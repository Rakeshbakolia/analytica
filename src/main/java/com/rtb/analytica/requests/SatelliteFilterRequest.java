package com.rtb.analytica.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SatelliteFilterRequest {
    private List<String> satelliteCodes;
    private List<String> launcherCodes;
    private List<String> countries;
    private Double minMass;
    private Double maxMass;
    private String startDate;
    private String endDate;
}
