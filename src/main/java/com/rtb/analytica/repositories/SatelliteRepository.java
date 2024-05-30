package com.rtb.analytica.repositories;

import com.rtb.analytica.models.Satellite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SatelliteRepository extends JpaRepository<Satellite, Long>, JpaSpecificationExecutor<Satellite> {
    Set<Satellite> findBySatelliteIdIn(Collection<String> satelliteIds);
    Optional<Satellite> findBySatelliteId(String satelliteId);
}
