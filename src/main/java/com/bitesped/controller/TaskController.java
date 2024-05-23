package com.bitesped.controller;

import com.bitesped.dto.ResponseContactClass;
import com.bitesped.entity.ContactEntity;
import com.bitesped.entity.RequestEntity;
import com.bitesped.entity.Response;
import com.bitesped.service.IdentifyService;
import com.bitesped.service.ServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {
    @Autowired
    private ServiceClass serviceClass;

    @Autowired
    private IdentifyService service;
    @PostMapping("/newRecord")
    private ResponseEntity<Response<ContactEntity>> insertData(@RequestBody RequestEntity requestEntity){
        Response<ContactEntity> response = serviceClass.insertNewRecord(requestEntity);
        if(response.getData()!=null) {
            return new ResponseEntity<>(response,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/identify")
    private ResponseEntity<ResponseContactClass> identify(@RequestBody RequestEntity requestEntity){
        ResponseContactClass allRelatedData = service.getAllRelatedData(requestEntity);
        return new ResponseEntity<>(allRelatedData,HttpStatus.OK);
    }
    @GetMapping("/test")
    private ResponseEntity<Response<String>> testDocker(@RequestBody RequestEntity requestEntity){
        Response<String> response = new Response<>();
        response.setData("Test Response Data");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
