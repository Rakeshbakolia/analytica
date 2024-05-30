package com.rtb.analytica.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rtb.analytica.models.Launcher;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class SatelliteRequest {
    private String satelliteId;
    private String country;
    private String launchDate;
    private Double mass;
    private String launcherId;
}
