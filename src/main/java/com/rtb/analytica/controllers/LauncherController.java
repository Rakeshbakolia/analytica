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

    @GetMapping("/{launcherCode}")
    public ResponseEntity<LauncherResponse> getLauncher(@PathVariable String launcherCode){
        return new ResponseEntity<>(launcherService.getLauncher(launcherCode), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<LauncherResponse> create(@RequestBody LauncherRequest request){
        return new ResponseEntity<>(launcherService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{launcherCode}")
    public ResponseEntity<LauncherResponse> update(@PathVariable String launcherCode, @RequestBody LauncherRequest request){
        return new ResponseEntity<>(launcherService.update(launcherCode, request), HttpStatus.OK);
    }

    @DeleteMapping("/{launcherCode}")
    public ResponseEntity<Boolean> delete(@PathVariable String launcherCode){
        launcherService.delete(launcherCode);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
