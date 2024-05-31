package com.rtb.analytica.requests;

import com.rtb.analytica.enums.TypeEnum;
import lombok.Data;

@Data
public class LauncherRequest {
    private String launcherCode;
    private TypeEnum type;
    private String registrationDate;
}
