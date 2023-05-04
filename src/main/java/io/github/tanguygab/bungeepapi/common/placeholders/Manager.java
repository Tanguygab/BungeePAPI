package io.github.tanguygab.bungeepapi.common.placeholders;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Manager {

    public static Manager instance;
    private final static Pattern PLACEHOLDER_PATTERN = Pattern.compile("%(?<identifier>[a-zA-Z0-9]+)_(?<name>[a-zA-Z0-9]+)_(?<arguments>[^%]+)?%");
    public Map<String,Expansion> expansions = new HashMap<>();

    public Manager() {
        instance = this;
    }

    public static Manager getInstance() {
        return instance;
    }

    public void register(Expansion expansion) {
        expansions.put(expansion.getIdentifier(),expansion);
    }

    public void unregister(String identifier) {
        expansions.remove(identifier);
    }

    public String parse(String text) {
        Matcher match = PLACEHOLDER_PATTERN.matcher(text);

        String identifier = match.group("identifier");
        String args = match.group("arguments");

        return text;
    }

    public String color(String text) {


        return text;
    }
}
