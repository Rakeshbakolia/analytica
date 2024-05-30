package com.rtb.analytica.manager;

import com.rtb.analytica.models.Launcher;
import com.rtb.analytica.repositories.LauncherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class LauncherManager {

    @Autowired
    private LauncherRepository launcherRepository;

    public List<Launcher> saveAll(Collection<Launcher> launchers){
        return launcherRepository.saveAll(launchers);
    }

    public Launcher save(Launcher launcher){
        return launcherRepository.save(launcher);
    }

    public void delete(Launcher launcher){
        launcherRepository.delete(launcher);
    }

    public Optional<Launcher> getLauncher(String launcherId){
        return launcherRepository.findByLauncherId(launcherId);
    }

    public Set<Launcher> getLaunchers(Collection<String> ids){
        return launcherRepository.findByLauncherIdIn(ids);
    }
}
