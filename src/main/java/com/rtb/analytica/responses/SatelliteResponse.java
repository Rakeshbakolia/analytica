package com.rtb.analytica.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SatelliteResponse {
    private String satelliteCode;
    private String country;
    private String launchDate;
    private Double mass;
    private LauncherResponse launcher;
}
