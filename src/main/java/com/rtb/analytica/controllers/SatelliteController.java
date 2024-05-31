package com.rtb.analytica.controllers;

import com.rtb.analytica.requests.SatelliteFilterRequest;
import com.rtb.analytica.requests.SatelliteRequest;
import com.rtb.analytica.responses.SatelliteResponse;
import com.rtb.analytica.services.SatelliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{satelliteCode}")
    public ResponseEntity<SatelliteResponse> getLauncher(@PathVariable String satelliteCode){
        return new ResponseEntity<>(satelliteService.getSatellite(satelliteCode), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<SatelliteResponse> create(@RequestBody SatelliteRequest request){
        return new ResponseEntity<>(satelliteService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{satelliteCode}")
    public ResponseEntity<SatelliteResponse> update(@PathVariable String satelliteCode, @RequestBody SatelliteRequest request){
        return new ResponseEntity<>(satelliteService.update(satelliteCode, request), HttpStatus.OK);
    }

    @DeleteMapping("/{satelliteCode}")
    public ResponseEntity<Boolean> delete(@PathVariable String satelliteCode){
        satelliteService.delete(satelliteCode);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<SatelliteResponse>> filter(@ModelAttribute SatelliteFilterRequest request){
        return new ResponseEntity<>(satelliteService.filter(request), HttpStatus.OK);
    }

}
