package com.bitesped.repository;

import com.bitesped.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BIteSpeedRepository extends JpaRepository<ContactEntity, Integer> {
    ContactEntity findByMailId(String mailId);
    ContactEntity findByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT * FROM bite_speed_task WHERE mail_Id = ?", nativeQuery = true)
    List<ContactEntity> getAllRelatedDataByMail(String mail);
    @Query(value = "SELECT * FROM bite_speed_task WHERE phone_number=?", nativeQuery = true)
    List<ContactEntity> getAllRelatedDataByPhoneNumber(String phoneNumber);
}
