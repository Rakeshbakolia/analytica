package com.rtb.analytica.controllers;

import com.rtb.analytica.requests.LauncherRequest;
import com.rtb.analytica.responses.LauncherResponse;
import com.rtb.analytica.services.LauncherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/launcher")
public class LauncherController {

    @Autowired
    private LauncherService launcherService;

    @GetMapping("/{launcherId}")
    public ResponseEntity<LauncherResponse> getLauncher(@PathVariable String launcherId){
        return new ResponseEntity<>(launcherService.getLauncher(launcherId), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<LauncherResponse> create(@RequestBody LauncherRequest request){
        return new ResponseEntity<>(launcherService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{launcherId}")
    public ResponseEntity<LauncherResponse> update(@PathVariable String launcherId, @RequestBody LauncherRequest request){
        return new ResponseEntity<>(launcherService.update(launcherId, request), HttpStatus.OK);
    }

    @DeleteMapping("/{launcherId}")
    public ResponseEntity<Boolean> delete(@PathVariable String launcherId){
        launcherService.delete(launcherId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
