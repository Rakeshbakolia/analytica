package com.rtb.analytica.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rtb.analytica.client.IsroClient;
import com.rtb.analytica.client.response.IsroSatelliteResponse;
import com.rtb.analytica.manager.LauncherManager;
import com.rtb.analytica.manager.SatelliteManager;
import com.rtb.analytica.models.Launcher;
import com.rtb.analytica.models.Satellite;
import com.rtb.analytica.responses.SatelliteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SatelliteService {

    @Autowired
    private SatelliteManager satelliteManager;

    @Autowired
    private LauncherManager launcherManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IsroClient isroClient;

    public void init(){
        List<IsroSatelliteResponse.IsroSatellite> satellites = isroClient.getSatellites();
        Set<String> launcherIds = satellites.stream()
                .map(IsroSatelliteResponse.IsroSatellite::getLauncher)
                .collect(Collectors.toSet());

        Map<String, Launcher> launcherMap = launcherManager.getLaunchers(launcherIds).stream()
                .collect(Collectors.toMap(Launcher::getLauncherId, l -> l));

        Set<String> existingSatelliteIds = satelliteManager.getSatellites(satellites.stream()
                .map(IsroSatelliteResponse.IsroSatellite::getId)
                .collect(Collectors.toSet())).stream()
                .map(Satellite::getSatelliteId)
                .collect(Collectors.toSet());

        List<Satellite> newSatellites = satellites.stream()
                .filter(satellite -> !existingSatelliteIds.contains(satellite.getId()))
                .filter(satellite -> launcherMap.containsKey(satellite.getLauncher()))
                .map(satellite -> {
                    try {
                        return Satellite.builder()
                        .satelliteId(satellite.getId())
                        .country(satellite.getCountry())
                        .mass(satellite.getMass())
                        .launcher(launcherMap.get(satellite.getLauncher()))
                        .launchDate(new SimpleDateFormat("dd-MM-yyyy").parse(satellite.getLaunchDate()))
                        .build();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
        ).toList();
        satelliteManager.saveAll(newSatellites);
    }

    public SatelliteResponse getSatellite(String satelliteId) {
        Satellite satellite =  satelliteManager.getSatellite(satelliteId).orElseThrow(()-> new RuntimeException("No satellite found"));
        return objectMapper.convertValue(satellite, SatelliteResponse.class);
    }
}
