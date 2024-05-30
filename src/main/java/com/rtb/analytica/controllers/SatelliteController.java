package com.rtb.analytica.controllers;

import com.rtb.analytica.requests.LauncherRequest;
import com.rtb.analytica.requests.SatelliteFilterRequest;
import com.rtb.analytica.requests.SatelliteRequest;
import com.rtb.analytica.responses.LauncherResponse;
import com.rtb.analytica.responses.SatelliteResponse;
import com.rtb.analytica.services.SatelliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

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
    public ResponseEntity<SatelliteResponse> create(@RequestBody SatelliteRequest request){
        return new ResponseEntity<>(satelliteService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{satelliteId}")
    public ResponseEntity<SatelliteResponse> update(@PathVariable String satelliteId, @RequestBody SatelliteRequest request){
        return new ResponseEntity<>(satelliteService.update(satelliteId, request), HttpStatus.OK);
    }

    @DeleteMapping("/{satelliteId}")
    public ResponseEntity<Boolean> delete(@PathVariable String satelliteId){
        satelliteService.delete(satelliteId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<SatelliteResponse>> filter(@ModelAttribute SatelliteFilterRequest request){
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
    }

}
