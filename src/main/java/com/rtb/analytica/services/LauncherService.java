package com.rtb.analytica.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rtb.analytica.client.IsroClient;
import com.rtb.analytica.client.response.IsroLauncherResponse;
import com.rtb.analytica.manager.LauncherManager;
import com.rtb.analytica.models.Launcher;
import com.rtb.analytica.responses.LauncherResponse;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LauncherService {

    @Autowired
    private LauncherManager launcherManager;

    @Autowired
    private IsroClient isroClient;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init(){
        log.info("Fetching all the launchers from isro...");
        List<IsroLauncherResponse.IsroLauncher> launchers = isroClient.getLaunchers();
        Set<String> newLauncherIds = launchers.stream().map(IsroLauncherResponse.IsroLauncher::getId).collect(Collectors.toSet());
        Set<String> existingLauncherIds = launcherManager.getLaunchers(newLauncherIds).stream().map(Launcher::getLauncherId).collect(Collectors.toSet());
        List<Launcher> launcherEntities = launchers.stream().filter(launcher -> !existingLauncherIds.contains(launcher.getId())).map(launcher -> Launcher.builder()
                .launcherId(launcher.getId()).build()
        ).toList();
        launcherManager.saveAll(launcherEntities);
        log.info("launchers are successfully fetched from isro and saved into database");
    }

    public LauncherResponse getLauncher(String launcherId){
        Launcher launcher = launcherManager.getLauncher(launcherId).orElseThrow(()->new RuntimeException("Launcher not found"));
        return objectMapper.convertValue(launcher, LauncherResponse.class);
    }
}
