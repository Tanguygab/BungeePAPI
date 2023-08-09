package io.github.tanguygab.bungeepapi.common.defaultexpansions;

import io.github.tanguygab.bungeepapi.api.Expansion;
import io.github.tanguygab.bungeepapi.api.placeholders.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class ReplaceExpansion extends Expansion {

    private final Map<String,Map<String,Map<String,String>>> replacements = new HashMap<>();

    public ReplaceExpansion() {
        super("replace");
        loadReplacements();
        addPlaceholder(new ServerPlaceholder("server",1000,(server,args)-> getReplacement(args,(placeholder,plArgs)->
                placeholder instanceof ServerPlaceholder sp ? sp.getLastValue(server,plArgs) : "Invalid Placeholder Type!")));
        addPlaceholder(new PlayerPlaceholder("player",1000,(server,target,args)-> getReplacement(args,(placeholder,plArgs)->
                placeholder instanceof PlayerPlaceholder sp ? sp.getLastValue(server,target,plArgs) : "Invalid Placeholder Type!")));
        addPlaceholder(new RelationalPlaceholder("rel",1000,(server,viewer,target,args)-> getReplacement(args,(placeholder,plArgs)->
                placeholder instanceof RelationalPlaceholder sp ? sp.getLastValue(server,viewer,target,plArgs) : "Invalid Placeholder Type!")));
    }

    private void loadReplacements() {
        getConfig().forEach((placeholder,cfg)->replacements.put(placeholder,(Map<String, Map<String, String>>) cfg));
    }

    private String getReplacement(String args, BiFunction<Placeholder,String,String> fun) {
        if (!args.contains("_")) return "Invalid Placeholder!";
        String replacement = args.split("_")[0];
        String placeholder = args.substring(replacement.length()+1);
        Map<Placeholder,String> map = getPAPI().getPlacehodlerWithArgs(placeholder);
        Placeholder pl = map.keySet().stream().findFirst().orElse(null);
        if (pl == null) return "Invalid Placeholder!";
        String output = fun.apply(pl,map.get(pl));
        return replacements.containsKey(placeholder) && replacements.get(placeholder).containsKey(replacement)
                ? replacements.get(placeholder).get(replacement).getOrDefault(output,output) : output;
    }

}
