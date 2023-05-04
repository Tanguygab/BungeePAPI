package io.github.tanguygab.bungeepapi.common;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

@SuppressWarnings("UnstableApiUsage")
public abstract class PluginMessageHandler {

    public final static String PLUGIN_CHANNEL = "bungeepapi:channel";
    protected final BungeePAPI plugin = BungeePAPI.getInstance();

    public abstract void unload();
    public abstract void onMessageReceive(byte[] data);
    public abstract void sendMessage();

    protected ByteArrayDataInput readMsg(byte[] data) {
        return ByteStreams.newDataInput(data);
    }
    protected ByteArrayDataOutput createMsg() {
        return ByteStreams.newDataOutput();
    }
}
