package com.rtb.analytica.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rtb.analytica.client.IsroClient;
import com.rtb.analytica.client.response.IsroSatelliteResponse;
import com.rtb.analytica.manager.LauncherManager;
import com.rtb.analytica.manager.SatelliteManager;
import com.rtb.analytica.models.Launcher;
import com.rtb.analytica.models.Satellite;
import com.rtb.analytica.requests.LauncherRequest;
import com.rtb.analytica.requests.SatelliteFilterRequest;
import com.rtb.analytica.requests.SatelliteRequest;
import com.rtb.analytica.responses.LauncherResponse;
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

    public SatelliteResponse create(SatelliteRequest request){
        if(Objects.isNull(request.getLauncherId())){
            throw new RuntimeException("launcherId not provided");
        }
        Launcher launcher = launcherManager.getLauncher(request.getLauncherId()).orElseThrow(() -> new RuntimeException("Launcher not found"));
        if(satelliteManager.getSatellite(request.getSatelliteId()).isPresent()){
            throw new RuntimeException("Satellite already present");
        }
        Satellite satellite = new Satellite();
        satellite.setSatelliteId(request.getSatelliteId()).setCountry(request.getCountry()).setMass(request.getMass()).setLauncher(launcher);
        try {
            satellite.setLaunchDate(Objects.nonNull(request.getLaunchDate()) ? new SimpleDateFormat("dd-MM-yyyy").parse(request.getLaunchDate()) : null);
        }catch (Exception e){
            throw new RuntimeException("Wrong Date format it should be dd-MM-yyyy");
        }
        satellite = satelliteManager.save(satellite);
        return objectMapper.convertValue(satellite, SatelliteResponse.class);
    }

    public SatelliteResponse update(String satelliteId, SatelliteRequest request){
        Launcher launcher = null;
        if(Objects.nonNull(request.getLauncherId())){
            launcher = launcherManager.getLauncher(request.getLauncherId()).orElseThrow(()-> new RuntimeException("Could not find launcher"));
        }
        Satellite satellite = satelliteManager.getSatellite(satelliteId).orElseThrow(() -> new RuntimeException("Satellite not found"));
        satellite.setCountry(Objects.nonNull(request.getCountry()) ? request.getCountry() : satellite.getCountry())
                .setMass(Objects.nonNull(request.getMass()) ? request.getMass() : satellite.getMass())
                .setLauncher(Objects.nonNull(launcher) ? launcher : satellite.getLauncher());
        try{
            satellite.setLaunchDate(Objects.nonNull(request.getLaunchDate()) ? new SimpleDateFormat("dd-MM-yyyy").parse(request.getLaunchDate()) : satellite.getLaunchDate());
        }catch (Exception e){
            throw new RuntimeException("Wrong Date format it should be dd-MM-yyyy");
        }
        return objectMapper.convertValue(satelliteManager.save(satellite), SatelliteResponse.class);
    }

    public void delete(String satelliteId){
        Satellite satellite = satelliteManager.getSatellite(satelliteId)
                .orElseThrow(()-> new RuntimeException("Could not find satellite"));
        satelliteManager.delete(satellite);
    }

    public List<Satellite> filter(SatelliteFilterRequest request){
        return Collections.emptyList();
    }
}
