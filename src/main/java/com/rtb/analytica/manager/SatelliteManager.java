package com.rtb.analytica.manager;

import com.rtb.analytica.models.Satellite;
import com.rtb.analytica.repositories.SatelliteRepository;
import com.rtb.analytica.requests.SatelliteFilterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.util.*;

import static com.rtb.analytica.manager.specifications.SatelliteSpecification.*;

@Component
public class SatelliteManager {

    @Autowired
    private SatelliteRepository repository;

    public List<Satellite> saveAll(Collection<Satellite> satellites){
        return repository.saveAll(satellites);
    }

    public Satellite save(Satellite satellite){
        return repository.save(satellite);
    }

    public Set<Satellite> getSatellites(Collection<String> satelliteCodes) {
        return repository.findBySatelliteCodeIn(satelliteCodes);
    }

    public Optional<Satellite> getSatellite(String satelliteCode){
     return repository.findBySatelliteCode(satelliteCode);
    }

    public void delete(Satellite satellite){
        repository.delete(satellite);
    }

    public List<Satellite> filter(SatelliteFilterRequest request){
        Specification<Satellite> specification = Specification.where(Objects.nonNull(request.getSatelliteCodes()) ? satelliteCodeIn(request.getSatelliteCodes()) : null)
                .and(Objects.nonNull(request.getCountries()) ? countries(request.getCountries()) : null)
                .and(Objects.nonNull(request.getMinMass()) || Objects.nonNull(request.getMaxMass()) ? massRange(request.getMinMass(), request.getMaxMass()) : null)
                .and(Objects.nonNull(request.getStartDate()) || Objects.nonNull(request.getEndDate()) ? dateRange(request.getStartDate(), request.getEndDate()) : null)
                .and(Objects.nonNull(request.getLauncherCodes()) ? launcherCodeIn(request.getLauncherCodes()) : null);
        return repository.findAll(specification);
    }
}
