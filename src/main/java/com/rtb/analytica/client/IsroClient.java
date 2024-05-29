package com.rtb.analytica.client;

import com.rtb.analytica.client.response.IsroLauncherResponse;
import com.rtb.analytica.client.response.IsroSatelliteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class IsroClient {

    @Autowired
    private RestTemplate restTemplate;

    public List<IsroLauncherResponse.IsroLauncher> getLaunchers() {
        try {
            ResponseEntity<IsroLauncherResponse> responseEntity = restTemplate.getForEntity("https://isro.vercel.app/api/launchers", IsroLauncherResponse.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return Optional.ofNullable(responseEntity.getBody())
                        .map(IsroLauncherResponse::getLaunchers)
                        .orElse(Collections.emptyList());
            }
            return Collections.emptyList();
        }catch(Exception e){
            throw new RuntimeException("Failed to retrieve launchers: " + e.getMessage(), e);
        }
    }

    public List<IsroSatelliteResponse.IsroSatellite> getSatellites() {
        try {
            ResponseEntity<IsroSatelliteResponse> responseEntity = restTemplate.getForEntity(
                    "https://isro.vercel.app/api/customer_satellites", IsroSatelliteResponse.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return Optional.ofNullable(responseEntity.getBody())
                        .map(IsroSatelliteResponse::getCustomerSatellites)
                        .orElse(Collections.emptyList());
            }
            return Collections.emptyList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve satellites: " + e.getMessage(), e);
        }
    }
}
