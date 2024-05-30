package com.rtb.analytica.manager;

import com.rtb.analytica.models.Launcher;
import com.rtb.analytica.models.Satellite;
import com.rtb.analytica.repositories.SatelliteRepository;
import com.rtb.analytica.requests.DateRange;
import com.rtb.analytica.requests.SatelliteFilterRequest;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class SatelliteManager {

    @Autowired
    private SatelliteRepository satelliteRepository;

    public List<Satellite> saveAll(Collection<Satellite> satellites){
        return satelliteRepository.saveAll(satellites);
    }

    public Satellite save(Satellite satellite){
        return satelliteRepository.save(satellite);
    }

    public Set<Satellite> getSatellites(Collection<String> satelliteIds) {
        return satelliteRepository.findBySatelliteIdIn(satelliteIds);
    }

    public Optional<Satellite> getSatellite(String satelliteId){
     return satelliteRepository.findBySatelliteId(satelliteId);
    }

    public void delete(Satellite satellite){
        satelliteRepository.delete(satellite);
    }

    public List<Satellite> filter(SatelliteFilterRequest request){
        return Collections.emptyList();
    }

    private Specification<Satellite> launcherIdIn(Collection<String> launcherIds){
        return (root, query, builder) -> {
            Join<Satellite, Launcher> join = root.join("launcher", JoinType.INNER);
            return builder.in(join.get("launcher_id").in(launcherIds));
        };
    }

    private Specification<Satellite> satelliteIdIn(Collection<String> satelliteIds){
        return (root, query, builder) -> builder.in(root.get("satellite_id").in(satelliteIds));
    }

    private Specification<Satellite> massList(Collection<Double> massList){
        return (root, query, builder) -> builder.in(root.get("mass").in(massList));
    }

    private Specification<Satellite> countries(Collection<String> countries){
        return (root, query, builder) -> builder.in(root.get("country").in(countries));
    }

    private Specification<Satellite> DateRange(DateRange range){
        Date start = null;
        Date end = null;
        try {
            start = new SimpleDateFormat("dd-MM-yyyy)").parse(range.getStartDate());
            end = new SimpleDateFormat("dd-MM-yyyy)").parse(range.getEndDate());
        }catch (Exception e){
            throw new RuntimeException("Wrong Date format it should be dd-MM-yyyy");
        }
        return (root, query, builder) -> builder.between(root.get("launch_date"), range.getStartDate(), range.getEndDate());
    }

}
