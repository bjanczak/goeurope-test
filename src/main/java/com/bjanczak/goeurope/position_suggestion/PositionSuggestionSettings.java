package com.bjanczak.goeurope.position_suggestion;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * PositionSuggestion suggestion settings.
 *
 * @author Bartłomiej Jańczak
 */
class PositionSuggestionSettings {
    /*
     * Settings parameters.
     */
    private static final String POSITION_SUGGESTION_URL = "position_suggestion_api_url";

    /*
     * State.
     */
    private static final Map<String, String> cache = Maps.newHashMap();

    // Loading settings cache.
    static {
        // Settings may be loaded from file.
        cache.put(POSITION_SUGGESTION_URL, "http://api.goeuro.com/api/v2/position/suggest/en/");
    }

    private PositionSuggestionSettings() {
        // Private constructor prevents from creating class instance.
    }

    /**
     * Returns position suggestion URL.
     *
     * @return returns position suggestion URL
     */
    public static String getPositionSuggestionUrl() {
        return cache.get(POSITION_SUGGESTION_URL);
    }
}
