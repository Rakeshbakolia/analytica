package com.rtb.analytica.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rtb.analytica.enums.TypeEnum;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LauncherResponse {
    private String launcherId;
    private TypeEnum type;
    private String registrationDate;
}