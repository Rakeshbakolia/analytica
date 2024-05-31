package com.rtb.analytica.requests;

import lombok.Data;

@Data
public class SatelliteRequest {
    private String satelliteCode;
    private String country;
    private String launchDate;
    private Double mass;
    private String launcherCode;
}
