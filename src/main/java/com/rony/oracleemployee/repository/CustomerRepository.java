package com.rony.oracleemployee.repository;

import com.rony.oracleemployee.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository< Customer, Long> {
}
