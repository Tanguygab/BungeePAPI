package io.github.tanguygab.bungeepapi.common;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.github.tanguygab.bungeepapi.common.entities.PAPIPlayer;

import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public abstract class PluginMessageHandler {

    public final static String PLUGIN_CHANNEL = "bungeepapi:channel";
    protected final BungeePAPI plugin = BungeePAPI.getInstance();

    public abstract void unload();

    protected ByteArrayDataInput readMessage(byte[] data) {
        return ByteStreams.newDataInput(data);
    }
    protected byte[] createMessage(String... args) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        for (String arg : args) out.writeUTF(arg);
        return out.toByteArray();
    }
    protected PAPIPlayer getPlayer(String uuid) {
        return plugin.getPlayer(UUID.fromString(uuid));
    }
}