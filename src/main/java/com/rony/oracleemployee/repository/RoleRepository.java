package com.rony.oracleemployee.repository;

import com.rony.oracleemployee.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;


@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findById(String id);

    Optional<Role> findByName(String name);
}
