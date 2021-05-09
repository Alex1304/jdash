package jdash.client.request;

import java.util.Map;

public class GDParams {

    public static final String GAME_VERSION = "21";
    public static final String BINARY_VERSION = "35";
    public static final String SECRET = "Wmfd2893gb7";

    public static Map<String, String> commonParams() {
        return Map.ofEntries(
                Map.entry("gameVersion", GAME_VERSION),
                Map.entry("binaryVersion", BINARY_VERSION),
                Map.entry("gdw", "0"),
                Map.entry("secret", SECRET)
        );
    }
}
