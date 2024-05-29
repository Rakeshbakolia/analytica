package com.rtb.analytica.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rtb.analytica.enums.TypeEnum;
import lombok.Data;

import java.util.Date;

@Data
public class LauncherRequest {
    @JsonProperty("launcher_id")
    private String launcherId;
    private TypeEnum type;
    private String registrationDate;
}
