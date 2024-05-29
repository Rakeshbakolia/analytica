package com.rtb.analytica.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class IsroSatelliteResponse {
    @JsonProperty("customer_satellites")
    private List<IsroSatellite> customerSatellites;

    @Data
    public static class IsroSatellite {
        private String id;
        private String country;
        @JsonProperty("launch_date")
        private String launchDate; // Start with String to avoid Date parsing issues
        private Double mass;
        private String launcher;
    }
}

