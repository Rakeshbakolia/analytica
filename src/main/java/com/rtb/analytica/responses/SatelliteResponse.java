package com.rtb.analytica.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rtb.analytica.models.Launcher;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SatelliteResponse {
    private String satelliteId;
    private String country;
    @Column(name = "launch_date")
    private String launchDate;
    private Double mass;
    private LauncherResponse launcher;
}
