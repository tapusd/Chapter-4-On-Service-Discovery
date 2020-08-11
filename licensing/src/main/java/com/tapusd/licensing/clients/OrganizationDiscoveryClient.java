package com.tapusd.licensing.clients;

import java.util.List;

import com.tapusd.licensing.model.Organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrganizationDiscoveryClient {
    @Autowired
    DiscoveryClient discoveryClient;

    public Organization getOrganization(Long organizationId){
        RestTemplate restTemplate = new RestTemplate();

        List<ServiceInstance> instances = discoveryClient.getInstances("organizationservice");

        if(instances.size() == 0) return null;

        String serviceUri = String.format("%s/v1/organizations/%s", instances.get(0).getUri().toString(),organizationId);

        ResponseEntity<Organization> restExchange = 
            restTemplate.exchange(
                serviceUri,
                 HttpMethod.GET,
                  null, Organization.class, organizationId);
        return restExchange.getBody();
    }
}