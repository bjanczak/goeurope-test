package com.bjanczak.goeurope.position_suggestion;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PositionSuggestion POJO.
 *
 * @author Bartłomiej Jańczak
 */
public class PositionSuggestion {
    /*
     * Properties.
     */
    @JsonProperty("_id")
    private Integer id;
    @JsonProperty("key")
    private String key;
    @JsonProperty("name")
    private String name;
    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("iata_airport_code")
    private String iataAirportCode;
    @JsonProperty("type")
    private String type;
    @JsonProperty("country")
    private String country;
    @JsonProperty("locationId")
    private String locationId;
    @JsonProperty("inEurope")
    private Boolean inEurope;
    @JsonProperty("countryCode")
    private String countryCode;
    @JsonProperty("coreCountry")
    private Boolean coreCountry;
    @JsonProperty("geo_position")
    private GeoPosition geoPosition;
    @JsonProperty("distance")
    private Double distance;

    /**
     * Geographical position POJO.
     */
    public static class GeoPosition {
        @JsonProperty("longitude")
        private Double longitude;
        @JsonProperty("latitude")
        private Double latitude;

        public Double getLongitude() {
            return longitude;
        }

        public Double getLatitude() {
            return latitude;
        }

        @Override
        public String toString() {
            return "GeoPosition{" +
                    "longitude=" + longitude +
                    ", latitude=" + latitude +
                    '}';
        }
    }

    public Integer getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getIataAirportCode() {
        return iataAirportCode;
    }

    public String getType() {
        return type;
    }

    public String getCountry() {
        return country;
    }

    public String getLocationId() {
        return locationId;
    }

    public Boolean getInEurope() {
        return inEurope;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Boolean getCoreCountry() {
        return coreCountry;
    }

    public GeoPosition getGeoPosition() {
        return geoPosition;
    }

    public Double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "PositionSuggestion{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", iataAirportCode='" + iataAirportCode + '\'' +
                ", type='" + type + '\'' +
                ", country='" + country + '\'' +
                ", locationId='" + locationId + '\'' +
                ", inEurope=" + inEurope +
                ", countryCode='" + countryCode + '\'' +
                ", coreCountry=" + coreCountry +
                ", geoPosition=" + geoPosition +
                ", distance=" + distance +
                '}';
    }
}
