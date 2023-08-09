package io.github.tanguygab.bungeepapi.common;

import io.github.tanguygab.bungeepapi.api.Expansion;
import io.github.tanguygab.bungeepapi.api.events.ServerPlaceholderUpdateEvent;
import io.github.tanguygab.bungeepapi.api.placeholders.Placeholder;
import io.github.tanguygab.bungeepapi.api.placeholders.PlayerPlaceholder;
import io.github.tanguygab.bungeepapi.api.placeholders.RelationalPlaceholder;
import io.github.tanguygab.bungeepapi.api.placeholders.ServerPlaceholder;
import io.github.tanguygab.bungeepapi.common.entities.PAPIPlayer;
import io.github.tanguygab.bungeepapi.common.entities.PAPIServer;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Manager {

    @Getter public static Manager instance;
    private final BungeePAPI papi = BungeePAPI.getInstance();
    private final static Pattern PLACEHOLDER_PATTERN = Pattern.compile("%(?<identifier>[a-zA-Z0-9]+)_(?<name>[a-zA-Z0-9]+)(_(?<arguments>[^%]+))?%");
    private final Map<String,Integer> refreshIntervals = new HashMap<>();
    private final int defaultRefreshInterval = refreshIntervals.getOrDefault("default",1000);
    private final Map<String, Expansion> expansions = new HashMap<>();
    private final Map<String, Placeholder> placeholders = new HashMap<>();
    private final List<Placeholder> usedPlaceholders = new ArrayList<>();

    private final AtomicInteger loopTime = new AtomicInteger();

    public Manager() {
        instance = this;
        papi.getCpu().startRepeatingTask(50,this::refresh);
    }

    private void refresh() {
        int loopTime = this.loopTime.addAndGet(50);

        for (Placeholder placeholder : usedPlaceholders) {
            if (placeholder.getRefresh() == -1 || loopTime % placeholder.getRefresh() != 0) continue;
            List<ServerPlaceholderUpdateEvent> updates = placeholder.update();
            updates.forEach(event->papi.getEventBus().fire(event));
        }

    }

    public void register(Expansion expansion) {
        expansions.put(expansion.getIdentifier(),expansion);
        expansion.getPlaceholders().forEach((name,placeholder)->placeholders.put("%"+expansion.getIdentifier()+"_"+name+"%",placeholder));
    }

    public void unregister(String identifier) {
        if (!expansions.containsKey(identifier)) return;
        expansions.get(identifier).getPlaceholders().keySet().forEach(placeholder->placeholders.remove("%"+identifier+"_"+placeholder+"%"));
        expansions.remove(identifier);
    }

    public Map<String,String> detectPlaceholders(String text) {
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(text);
        Map<String,String> placeholders = new HashMap<>();
        while (matcher.find()) {
            String placeholder = "%"+matcher.group("identifier")+"_"+matcher.group("name")+"%";
            String args = matcher.group("arguments");
            placeholders.put(placeholder,args);
        }
        return placeholders;
    }

    public String parse(String text, PAPIServer server, boolean color) {
        return parse(text,server,null,null,color);
    }
    public String parse(String text, PAPIServer server, PAPIPlayer player, boolean color) {
        return parse(text,server,player,player,color);
    }
    public String parse(String text, PAPIServer server, PAPIPlayer viewer, PAPIPlayer target, boolean color) {
        Map<String,String> placeholders = detectPlaceholders(text);
        for (String placeholder : placeholders.keySet()) {
            Placeholder pl = getPlaceholder(placeholder);
            String args = placeholders.get(placeholder);
            pl.addArguments(args);
            String output = placeholder;
            if (pl instanceof ServerPlaceholder sp) output = sp.getLastValue(server,args);
            if (pl instanceof PlayerPlaceholder pp && target != null) output = pp.getLastValue(server,target,args);
            if (pl instanceof RelationalPlaceholder rp && target != null && viewer != null) output = rp.getLastValue(server,viewer,target,args);
            text = text.replace(placeholder,output);
        }
        return color ? color(text) : text;
    }

    public Placeholder getPlaceholder(String placeholder) {
        return placeholders.get(placeholder);
    }
    public Map<Placeholder,String> getPlaceholderWithArgs(String placeholderWithArgs) {
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(placeholderWithArgs);
        if (!matcher.find()) return null;
        String pl = "%"+matcher.group("identifier")+"_"+matcher.group("name")+"%";
        return new HashMap<>() {{put(getPlaceholder(pl),matcher.group("arguments"));}};
    }

    public String color(String text) {
        return ChatColor.translateAlternateColorCodes('&',text);
    }

    public boolean hasRefreshOverride(String placeholder) {
        return refreshIntervals.containsKey(placeholder);
    }
    public int getRefresh(String placeholder) {
        return refreshIntervals.getOrDefault(placeholder,defaultRefreshInterval);
    }
}
