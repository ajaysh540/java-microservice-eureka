package com.task.managerapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RefreshScope
@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    private RestTemplate restTemplate =new RestTemplate();



    @GetMapping("/employees")
    public Object getemployees(){
        try{
            Object result = restTemplate.exchange(getBaseUrl()+"/getemployees", HttpMethod.GET,null, new ParameterizedTypeReference<List<Object>>() {}).getBody();
            System.out.print(result.toString());
            return result;
        }
        catch (Exception e){
            return ("Error Occured "+ HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addemployee")
    public ResponseEntity<Object> addEmployee(@RequestBody Object employee){
        System.out.println(employee.toString());
        try{
            return new ResponseEntity<Object>(restTemplate.postForObject(getBaseUrl()+"/addEmployee",employee ,Object.class), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<Object>("Error Occured",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateEmployee(@RequestBody Object employee){
        try{
            return new ResponseEntity<Object>(restTemplate.postForObject(getBaseUrl()+"/updateEmployee",employee ,Object.class), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<Object>("Error Occured" +HttpStatus.INTERNAL_SERVER_ERROR,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEmployee(@RequestBody Object employee){
        try{
            return new ResponseEntity<String>(restTemplate.postForObject(getBaseUrl()+"/deleteEmployee",employee ,String.class), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<String>("Error Occured" +HttpStatus.INTERNAL_SERVER_ERROR,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private String getBaseUrl(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("EMPLOYEE-SERVICE");
        return serviceInstance.getUri().toString();
    }

}
