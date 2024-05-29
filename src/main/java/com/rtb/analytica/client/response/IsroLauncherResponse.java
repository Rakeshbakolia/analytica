package com.rtb.analytica.client.response;

import lombok.Data;

import java.util.List;

@Data
public class IsroLauncherResponse {

    private List<IsroLauncher> launchers;

    @Data
    public static class IsroLauncher {
        private String id;
    }
}
