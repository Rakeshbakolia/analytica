package com.rtb.analytica.manager;

import com.rtb.analytica.models.Satellite;
import com.rtb.analytica.repositories.SatelliteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class SatelliteManager {

    @Autowired
    private SatelliteRepository satelliteRepository;

    public List<Satellite> saveAll(Collection<Satellite> satellites){
        return satelliteRepository.saveAll(satellites);
    }

    public Set<Satellite> getSatellites(Collection<String> satelliteIds) {
        return satelliteRepository.findBySatelliteIdIn(satelliteIds);
    }

    public Optional<Satellite> getSatellite(String satelliteId){
     return satelliteRepository.findBySatelliteId(satelliteId);
    }
}
