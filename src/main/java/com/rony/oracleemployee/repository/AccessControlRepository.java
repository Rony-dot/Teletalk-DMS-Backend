package com.rony.oracleemployee.repository;

import com.rony.oracleemployee.model.AccessControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessControlRepository extends JpaRepository<AccessControl, Long> {
    public AccessControl getAccessControlsByUserId(long userId);
}
