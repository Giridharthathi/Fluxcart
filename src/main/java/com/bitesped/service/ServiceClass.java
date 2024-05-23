package com.bitesped.service;

import com.bitesped.dto.LinkPrecedence;
import com.bitesped.entity.ContactEntity;
import com.bitesped.entity.RequestEntity;
import com.bitesped.entity.Response;
import com.bitesped.repository.BIteSpeedRepository;
import jakarta.persistence.NonUniqueResultException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;

@Service
public class ServiceClass {
    @Autowired
    private BIteSpeedRepository repository;

    public Response<ContactEntity> insertNewRecord( RequestEntity requestEntity) {

            if (requestEntity.getMail() != null) {
                return emailAlreadyExistence(requestEntity);
            }
            else if (requestEntity.getPhoneNumber() != null) {
                return phoneNumberAlreadyExistence(requestEntity);
            }

        return null;
    }

    private Response<ContactEntity> emailAlreadyExistence(RequestEntity requestEntity) {
        Response<ContactEntity> response = new Response<>();
        try {
            ContactEntity byMailId = repository.findByMailId(requestEntity.getMail());
            if (byMailId != null && !byMailId.getPhoneNumber().equals(requestEntity.getPhoneNumber())) {

                ContactEntity newSecondaryAccount = new ContactEntity();
                newSecondaryAccount.setMailId(byMailId.getMailId());
                newSecondaryAccount.setPhoneNumber(requestEntity.getPhoneNumber());
                newSecondaryAccount.setLinkedId(byMailId.getId());
                newSecondaryAccount.setLinkPrecedence(LinkPrecedence.SECONDARY);
                newSecondaryAccount.setCreatedAt(LocalDateTime.now());
                newSecondaryAccount.setUpdatedAt(LocalDateTime.now());
                ContactEntity secondaryRecord = createSecondaryRecord(newSecondaryAccount);
                response.setData(secondaryRecord);
                response.setResponse("Insert a new secondary record");

                return response;
            } else if (byMailId == null) {
                return phoneNumberAlreadyExistence(requestEntity);
            } else {
                response.setResponse("Already a record present");
            }
        } catch (NonUniqueResultException e) {
            response.setResponse("More than one record found with the same mail");
        }
        return response;
    }

    private Response<ContactEntity> phoneNumberAlreadyExistence(RequestEntity requestEntity) {
        Response<ContactEntity> response = new Response<>();
        try {
            ContactEntity byPhoneNumber = repository.findByPhoneNumber(requestEntity.getPhoneNumber());
            if (byPhoneNumber != null && !byPhoneNumber.getMailId().equals(requestEntity.getMail())) {
                ContactEntity newSecondaryAccount = new ContactEntity();
                newSecondaryAccount.setMailId(requestEntity.getMail());
                newSecondaryAccount.setPhoneNumber(byPhoneNumber.getPhoneNumber());
                newSecondaryAccount.setLinkedId(byPhoneNumber.getId());
                newSecondaryAccount.setLinkPrecedence(LinkPrecedence.SECONDARY);
                newSecondaryAccount.setCreatedAt(LocalDateTime.now());
                newSecondaryAccount.setUpdatedAt(LocalDateTime.now());
                ContactEntity secondaryRecord = createSecondaryRecord(newSecondaryAccount);
                response.setData(secondaryRecord);
                response.setResponse("Inserte a new secondary record");
            } else if (byPhoneNumber == null) {
                ContactEntity newRecord = new ContactEntity();
                newRecord.setMailId(requestEntity.getMail());
                newRecord.setPhoneNumber(requestEntity.getPhoneNumber());
                newRecord.setLinkedId(0);
                newRecord.setLinkPrecedence(LinkPrecedence.PRIMARY);
                newRecord.setCreatedAt(LocalDateTime.now());
                newRecord.setUpdatedAt(LocalDateTime.now());

                ContactEntity secondaryRecord = createSecondaryRecord(newRecord);
                response.setData(secondaryRecord);
                response.setResponse("No record found previous, new Primary record inserted");
            } else {
                response.setResponse("with all this detail already a record is present");
            }
            return response;
        } catch (NonUniqueResultException e) {
            response.setResponse("More than one record found with the same phone number");
        }
        return response;
    }

    private ContactEntity createSecondaryRecord(ContactEntity contactEntity) {
        return repository.save(contactEntity);
    }
}
