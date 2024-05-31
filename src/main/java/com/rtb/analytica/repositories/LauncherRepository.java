package com.rtb.analytica.repositories;

import com.rtb.analytica.models.Launcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Repository
public interface LauncherRepository extends JpaRepository<Launcher, Long> {
    Set<Launcher> findByLauncherCodeIn(Collection<String> launcherCodes);
    Optional<Launcher> findByLauncherCode(String launcherCode);
}
