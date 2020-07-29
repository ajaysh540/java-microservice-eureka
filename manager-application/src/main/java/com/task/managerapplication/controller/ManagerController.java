package com.task.managerapplication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RefreshScope
@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    private RestTemplate restTemplate = new RestTemplate();
    private static String baseUrl;
    private static String token;
    private static HttpHeaders headers;
    Logger logger  = LoggerFactory.getLogger(ManagerController.class);

    @GetMapping("/employees")
    public Object getemployees() {
        logger.info("Calling Employee Service to Get employees");
        try {
            String url = getBaseUrl()+"/getemployees";
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return result;
        } catch (Exception e) {
            return ("Error Occurred " + HttpStatus.INTERNAL_SERVER_ERROR+" Employee Service says: "+e.getCause());
        }
    }

    @PostMapping("/addemployee")
    public Object addEmployee(@RequestBody Object employee) {
        logger.info("Calling Employee Service to Add employees");
        try {
            String url = getBaseUrl()+"/addEmployee";
            HttpEntity<Object> entity = new HttpEntity<>(employee,headers);
            ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            return result;
        } catch (Exception e) {
            return ("Error Occurred " + HttpStatus.INTERNAL_SERVER_ERROR+" Employee Service says: "+e.getCause());
        }
    }

    @PutMapping("/update")
    public Object updateEmployee(@RequestBody Object employee) {
        logger.info("Calling Employee Service to Update employees");
        try {
            String url = getBaseUrl()+"/updateEmployee";
            HttpEntity<Object> entity = new HttpEntity<>(employee,headers);
            ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
            return result;

        } catch (Exception e) {
            return ("Error Occurred " + HttpStatus.INTERNAL_SERVER_ERROR+" Employee Service says: "+e.getCause());
        }
    }

    @DeleteMapping("/delete")
    public Object deleteEmployee(@RequestBody Object employee) {
        logger.info("Calling Employee Service to Delete employees");
        try {
            String url = getBaseUrl()+"/deleteEmployee";
            HttpEntity<Object> entity = new HttpEntity<>(employee,headers);
            ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
            return result;
        } catch (Exception e) {
            return ("Error Occurred " + HttpStatus.INTERNAL_SERVER_ERROR+" Employee Service says: "+e.getCause());
        }
    }

    private String getBaseUrl() throws Exception {
        ServiceInstance serviceInstance = loadBalancerClient.choose("EMPLOYEE-SERVICE");
        if(baseUrl==null) {
            baseUrl = serviceInstance.getUri().toString();
            try{
                logger.info("Attempting Login to Employee Service");
                Map<String,String> loginDetails = new HashMap<>();
                loginDetails.put("username","employee");
                loginDetails.put("password","employee");
                ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/authenticate", loginDetails, String.class);
                token = response.getBody();
                logger.info(response.getBody());
                logger.info(token);
                if(token!=null){
                    logger.info("Token Found");
                    headers = new HttpHeaders();
                    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    headers.set("Authorization","token "+token);
                    logger.info("Header Added");
                }
                else{
                    throw new Exception("Login to Employee Failed");
                }
            }
            catch(Exception e){
                logger.error("Login Failed" + e.getMessage());
                throw e;
            }
        }
        return baseUrl +"/employee";
    }

}
