package com.rtb.analytica.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SatelliteFilterRequest {
    private List<String> satelliteIds;
    private List<String> launcherIds;
    private List<String> countries;
    private List<Double> massList;
    private DateRange dateRange;
}
