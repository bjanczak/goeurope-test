package com.bjanczak.goeurope.position_suggestion;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteSource;
import com.googlecode.jcsv.writer.CSVEntryConverter;
import com.googlecode.jcsv.writer.CSVWriter;
import com.googlecode.jcsv.writer.internal.CSVWriterBuilder;

/**
 * PositionSuggestion suggestion facade.
 *
 * @author Bartłomiej Jańczak
 */
public class PositionSuggestionFacade {

    final static Logger log = LoggerFactory.getLogger(PositionSuggestionFacade.class);

    /**
     * Singleton instance.
     */
    private static final PositionSuggestionFacade INSTANCE = new PositionSuggestionFacade();



    private PositionSuggestionFacade() {
        // Private constructor prevents instance creation.
    }

    /**
     * Returns facede instnace.
     *
     * @return returns facede instnace
     */
    public static PositionSuggestionFacade getInstance() {
        return INSTANCE;
    }

    /**
     * Returns position suggestions.
     *
     * @param cityNamePhrase
     * @return returns position suggestions
     */
    public List<PositionSuggestion> getPositionSuggestions(String cityNamePhrase) throws IOException {
        String positionSuggestionUrl = getPositionSuggestionUrl(cityNamePhrase);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(positionSuggestionUrl);

        log.info("Sending 'GET' request to URL: {}",
                 positionSuggestionUrl);
        HttpResponse httpResponse = httpClient.execute(httpGet);

        if (HttpStatus.SC_OK != httpResponse.getStatusLine().getStatusCode()) {
            // Exception Handling.
            String errorMessage = String.format("Unexpected status code! request URL: %s | Response code: %d | reason: %s",
                                                positionSuggestionUrl,
                                                httpResponse.getStatusLine().getStatusCode(),
                                                httpResponse.getStatusLine().getReasonPhrase());
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        String responseContent = readResponse(httpResponse);
        log.info("URL: {} | responseContent: {}",
                 positionSuggestionUrl,
                 responseContent);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseContent, new TypeReference<List<PositionSuggestion>>(){});
    }

    /**
     * Saves given positions suggestions under given file path.
     *
     * @param positionSuggestions
     * @param filePath
     */
    public void savePositionSuggestionsToFile(List<PositionSuggestion> positionSuggestions, String filePath) throws IOException {
        log.info("Writing position suggestions to file. filePath: {} | positionSuggestions: {}",
                 filePath,
                 positionSuggestions);
        CSVWriter<PositionSuggestion> csvWriter =  new CSVWriterBuilder<PositionSuggestion>(new FileWriter(filePath))
                .entryConverter(new CSVEntryConverter<PositionSuggestion>() {
                    public String[] convertEntry(PositionSuggestion position) {
                        PositionSuggestion.GeoPosition geoPosition = position.getGeoPosition();
                        return new String[]{position.getId().toString(),
                                            position.getName(),
                                            position.getType(),
                                            geoPosition.getLatitude().toString(),
                                            geoPosition.getLongitude().toString()};
                    }
                })
                .build();
        csvWriter.writeAll(positionSuggestions);
        csvWriter.flush();
    }

    /**
     * Reads response http response.
     * @param httpResponse
     * @return returns response a String
     * @throws IOException
     */
    private String readResponse(final HttpResponse httpResponse) throws IOException {
        return  new ByteSource() {
                    @Override
                    public InputStream openStream() throws IOException {
                        return httpResponse.getEntity().getContent();
                    }
                }.asCharSource(Charset.defaultCharset()).read();
    }

    /**
     * Builds suggestion URL.
     *
     * @param cityNamePhrase city name
     * @return returns built URL
     */
    private String getPositionSuggestionUrl(String cityNamePhrase) {
        return new StringBuffer()
                       .append(PositionSuggestionSettings.getPositionSuggestionUrl())
                       .append(cityNamePhrase)
                       .toString();
    }
}
