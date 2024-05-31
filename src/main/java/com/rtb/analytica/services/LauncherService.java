package com.rtb.analytica.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rtb.analytica.client.IsroClient;
import com.rtb.analytica.client.response.IsroLauncherResponse;
import com.rtb.analytica.manager.LauncherManager;
import com.rtb.analytica.models.Launcher;
import com.rtb.analytica.requests.LauncherRequest;
import com.rtb.analytica.responses.LauncherResponse;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
        Set<String> newLauncherCodes = launchers.stream().map(IsroLauncherResponse.IsroLauncher::getId).collect(Collectors.toSet());
        Set<String> existingLauncherCodes = launcherManager.getLaunchers(newLauncherCodes).stream().map(Launcher::getLauncherCode).collect(Collectors.toSet());
        List<Launcher> launcherEntities = launchers.stream().filter(launcher -> !existingLauncherCodes.contains(launcher.getId())).map(launcher -> Launcher.builder()
                .launcherCode(launcher.getId()).build()
        ).toList();
        launcherManager.saveAll(launcherEntities);
        log.info("launchers are successfully fetched from isro and saved into database");
    }

    public LauncherResponse getLauncher(String launcherCode){
        Launcher launcher = launcherManager.getLauncher(launcherCode).orElseThrow(()->new RuntimeException("Launcher not found"));
        return objectMapper.convertValue(launcher, LauncherResponse.class);
    }

    public LauncherResponse create(LauncherRequest request){
        if(launcherManager.getLauncher(request.getLauncherCode()).isPresent()){
            throw new RuntimeException("Launcher already present");
        }
        Launcher launcher = new Launcher();
        launcher.setLauncherCode(request.getLauncherCode());
        launcher.setType(request.getType());
        try {
            launcher.setRegistrationDate(Objects.nonNull(request.getRegistrationDate()) ? new SimpleDateFormat("dd-MM-yyyy").parse(request.getRegistrationDate()) : null);
        }catch (Exception e){
            throw new RuntimeException("Wrong Date format it should be dd-MM-yyyy");
        }
        launcher = launcherManager.save(launcher);
        return objectMapper.convertValue(launcher, LauncherResponse.class);
    }

    public LauncherResponse update(String launcherCode, LauncherRequest request){
        Launcher launcher = launcherManager.getLauncher(launcherCode).orElseThrow(() -> new RuntimeException("Launcher not found"));
        launcher.setType(Objects.nonNull(request.getType()) ? request.getType() : launcher.getType());
        try{
            launcher.setRegistrationDate(Objects.nonNull(request.getRegistrationDate()) ? new SimpleDateFormat("dd-MM-yyyy").parse(request.getRegistrationDate()) : launcher.getRegistrationDate());
        }catch (Exception e){
            throw new RuntimeException("Wrong Date format it should be dd-MM-yyyy");
        }
        return objectMapper.convertValue(launcherManager.save(launcher), LauncherResponse.class);
    }

    public void delete(String launcherCode){
        Launcher launcher = launcherManager.getLauncher(launcherCode)
                .orElseThrow(()-> new RuntimeException("Could not find launcher"));
        launcherManager.delete(launcher);
    }
}
