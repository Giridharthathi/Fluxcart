package com.bitesped.service;

import com.bitesped.dto.LinkPrecedence;
import com.bitesped.dto.ResponseContactClass;
import com.bitesped.entity.ContactEntity;
import com.bitesped.entity.RequestEntity;
import com.bitesped.repository.BIteSpeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IdentifyService {
    @Autowired
    private BIteSpeedRepository repository;
    @Autowired
    private ResponseContactClass responseContactClass;
    public ResponseContactClass getAllRelatedData(RequestEntity requestEntity){
        List<ContactEntity> allRelatedData = new ArrayList<>();

        responseContactClass.setMailId(new ArrayList<>());
        responseContactClass.setPhoneNumber(new ArrayList<>());
        responseContactClass.setSecondaryContactId(new ArrayList<>());

        if(requestEntity.getPhoneNumber()!=null){
            allRelatedData = repository.getAllRelatedDataByPhoneNumber(requestEntity.getPhoneNumber());
        }
        if(requestEntity.getMail()!=null){
            allRelatedData = repository.getAllRelatedDataByMail(requestEntity.getMail());
        }
        allRelatedData.forEach(contactDetail->{
          if(contactDetail.getLinkPrecedence() == LinkPrecedence.PRIMARY){
              responseContactClass.setPrimaryContactId(contactDetail.getId());
              responseContactClass.getMailId().add(contactDetail.getMailId());
              responseContactClass.getPhoneNumber().add( contactDetail.getPhoneNumber());
//              responseContactClass.getMailId().set(0, contactDetail.getMailId());
//              responseContactClass.getPhoneNumber().set(1, contactDetail.getPhoneNumber());
          }else {
              responseContactClass.getMailId().add(contactDetail.getMailId());
              responseContactClass.getPhoneNumber().add(contactDetail.getPhoneNumber());
              responseContactClass.getSecondaryContactId().add(contactDetail.getLinkedId());
          }
        });
        return responseContactClass;
    }
}
