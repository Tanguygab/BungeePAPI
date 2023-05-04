package io.github.tanguygab.bungeepapi.common;


import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.NonNull;

/**
 * A class which measures CPU usage of all tasks inserted into it and shows usage
 * Clearly I didn't just copy-paste it from TAB's repo
 */
public class CPUManager {

    private final int UPDATE_RATE_SECONDS = 10;

    private final long TIME_PERCENT = TimeUnit.SECONDS.toNanos(1) / UPDATE_RATE_SECONDS;

    /** Active time in current time period saved as nanoseconds from placeholders */
    private volatile Map<String, Long> placeholderUsageCurrent = new ConcurrentHashMap<>();

    /** Active time in previous time period saved as nanoseconds from placeholders */
    private volatile Map<String, Long> placeholderUsagePrevious = new HashMap<>();

    // Scheduler for scheduling delayed and repeating tasks
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(
            new ThreadFactoryBuilder().setNameFormat("BungeePAPI Processing Thread").build());

    /** Tasks submitted to main thread before plugin was fully enabled */
    private final Queue<Runnable> taskQueue = new ConcurrentLinkedQueue<>();

    /** Enabled flag used to queue incoming tasks if plugin is not enabled yet */
    private volatile boolean enabled = false;

    /**
     * Constructs new instance and starts repeating task that resets values in configured interval
     */
    public CPUManager() {
        startRepeatingTask((int) TimeUnit.SECONDS.toMillis(UPDATE_RATE_SECONDS), () -> {
            placeholderUsagePrevious = Collections.unmodifiableMap(placeholderUsageCurrent);
            placeholderUsageCurrent = new ConcurrentHashMap<>();
        });
    }

    /**
     * Cancels all tasks and shuts down thread pools
     */
    public void cancelAllTasks() {
        scheduler.shutdownNow();
    }

    /**
     * Marks cpu manager as loaded and submits all queued tasks
     */
    public void enable() {
        enabled = true;

        Runnable r;
        while ((r = taskQueue.poll()) != null) {
            submit(r);
        }
    }

    /**
     * Submits task to BungeePAPI's main thread. If plugin is not enabled yet,
     * queues the task instead and executes once it's loaded.
     *
     * @param task task to execute
     */
    private void submit(@NonNull Runnable task) {
        if (scheduler.isShutdown()) return;
        if (!enabled) {
            taskQueue.add(task);
            return;
        }
        scheduler.submit(() -> run(task));
    }

    /**
     * Returns cpu usage map of placeholders from previous time period
     *
     * @return cpu usage map of placeholders
     */
    public Map<String, Float> getPlaceholderUsage() {
        return getUsage(placeholderUsagePrevious);
    }

    /**
     * Converts nano map to percent and sorts it from highest to lowest usage.
     *
     * @param map map to convert
     * @return converted and sorted map
     */
    private @NonNull Map<String, Float> getUsage(@NonNull Map<String, Long> map) {
        return map
                .entrySet()
                .stream()
                .sorted(Entry.comparingByValue((o1, o2) -> Long.compare(o2, o1)))
                .collect(LinkedHashMap::new,
                        (m, e) -> m.put(e.getKey(), nanosToPercent(e.getValue())),
                        Map::putAll
                );
    }

    /**
     * Converts nanoseconds to percent usage.
     *
     * @param nanos nanoseconds of cpu time
     * @return usage in % (0-100)
     */
    private float nanosToPercent(long nanos) {
        return (float) nanos / TIME_PERCENT;
    }

    /**
     * Adds used time to specified key into specified map
     *
     * @param map  map to add usage to
     * @param key  usage key
     * @param time nanoseconds to add
     */
    private void addTime(@NonNull Map<String, Long> map, @NonNull String key, long time) {
        map.merge(key, time, Long::sum);
    }

    /**
     * Adds placeholder time to specified placeholder
     *
     * @param placeholder placeholder to add time to
     * @param nanoseconds time to add
     */
    public void addPlaceholderTime(@NonNull String placeholder, long nanoseconds) {
        addTime(placeholderUsageCurrent, placeholder, nanoseconds);
    }

    public void runTask(@NonNull Runnable task) {
        submit(task);
    }

    public void startRepeatingTask(int intervalMilliseconds, @NonNull Runnable task) {
        if (scheduler.isShutdown()) return;
        scheduler.scheduleAtFixedRate(() -> run(task), intervalMilliseconds, intervalMilliseconds, TimeUnit.MILLISECONDS);
    }

    private void run(@NonNull Runnable task) {
        try {task.run();}
        catch (Exception | LinkageError | StackOverflowError e) {e.printStackTrace();}
    }
}