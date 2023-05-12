package io.github.tanguygab.bungeepapi.common.placeholders;

import io.github.tanguygab.bungeepapi.common.PAPIPlayer;
import io.github.tanguygab.bungeepapi.common.PAPIServer;
import io.github.tanguygab.bungeepapi.common.placeholders.expansions.Expansion;
import io.github.tanguygab.bungeepapi.common.placeholders.types.Placeholder;
import io.github.tanguygab.bungeepapi.common.placeholders.types.PlayerPlaceholder;
import io.github.tanguygab.bungeepapi.common.placeholders.types.RelationalPlaceholder;
import io.github.tanguygab.bungeepapi.common.placeholders.types.ServerPlaceholder;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Manager {

    @Getter public static Manager instance;
    private final static Pattern PLACEHOLDER_PATTERN = Pattern.compile("%(?<identifier>[a-zA-Z0-9]+)_(?<name>[a-zA-Z0-9]+)_(?<arguments>[^%]+)?%");
    private final Map<String,Integer> refreshIntervals = new HashMap<>();
    private final int defaultRefreshInterval = refreshIntervals.getOrDefault("default",1000);
    private final Map<String, Expansion> expansions = new HashMap<>();
    private final Map<String, Placeholder> placeholders = new HashMap<>();

    public Manager() {
        instance = this;
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
            String identifier = matcher.group("identifier");
            String name = matcher.group("name");
            String placeholder = "%"+identifier+"_"+name+"%";
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
            String output = placeholder;
            if (pl instanceof ServerPlaceholder sp) output = sp.getValue(server,args);
            if (pl instanceof PlayerPlaceholder pp && target != null) output = pp.getValue(server,target,args);
            if (pl instanceof RelationalPlaceholder rp && target != null && viewer != null) output = rp.getValue(server,viewer,target,args);
            text = text.replace(placeholder,output);
        }
        return color ? color(text) : text;
    }

    public Placeholder getPlaceholder(String placeholder) {
        return placeholders.get(placeholder);
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
