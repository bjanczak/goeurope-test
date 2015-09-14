package com.bjanczak.goeurope;

import java.io.IOException;
import java.util.List;

import com.bjanczak.goeurope.position_suggestion.PositionSuggestion;
import com.bjanczak.goeurope.position_suggestion.PositionSuggestionFacade;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;

/**
 * Application entry point.
 *
 * @author Bartłomiej Jańczak
 */
public class App {

    public static void main(String[] args) {
        Preconditions.checkState(args.length > 0, "Please provide application with arguments!");

        String cityName = args[0];
        String csvFilePath = "position_suggestion.csv";

        if (args.length > 1) {
            csvFilePath = args[1];
        }

        try {
            PositionSuggestionFacade positionSuggestionFacade = PositionSuggestionFacade.getInstance();

            List<PositionSuggestion> positionSuggestions = positionSuggestionFacade.getPositionSuggestions(cityName);
            positionSuggestionFacade.savePositionSuggestionsToFile(positionSuggestions,
                                                                   csvFilePath);
        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }
}
