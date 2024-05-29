package com.rtb.analytica.controllers;

import com.rtb.analytica.requests.LauncherRequest;
import com.rtb.analytica.responses.LauncherResponse;
import com.rtb.analytica.responses.SatelliteResponse;
import com.rtb.analytica.services.SatelliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/satellite")
public class SatelliteController {

    @Autowired
    private SatelliteService satelliteService;

    @GetMapping("/init")
    public String initSatellite(){
        satelliteService.init();
        return "Success";
    }

    @GetMapping("/{satelliteId}")
    public ResponseEntity<SatelliteResponse> getLauncher(@PathVariable String satelliteId){
        return new ResponseEntity<>(satelliteService.getSatellite(satelliteId), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<LauncherResponse> create(@RequestBody LauncherRequest request){
        return new ResponseEntity<>(new LauncherResponse(), HttpStatus.CREATED);
    }

    @PostMapping("/{satelliteId}")
    public ResponseEntity<LauncherResponse> update(@PathVariable String satelliteId, @RequestBody LauncherRequest request){
        return new ResponseEntity<>(new LauncherResponse(), HttpStatus.OK);
    }

    @DeleteMapping("/{satelliteId}")
    public ResponseEntity<Boolean> delete(@PathVariable String satelliteId){
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
