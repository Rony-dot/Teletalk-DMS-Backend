package com.rony.oracleemployee.services;


import com.rony.oracleemployee.exception.ResourceNotFoundException;
import com.rony.oracleemployee.model.Role;
import com.rony.oracleemployee.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> getAll(){
        return roleRepository.findAll();
    }

    public void add(Role role){
        roleRepository.save(role);
    }

    public Role getById(long id){
        Optional<Role> optionalRole = roleRepository.findById(id);
        if(optionalRole.isPresent()){
            return optionalRole.get();
        }
        log.error("role not exist of id : {}", id);
        throw new ResourceNotFoundException("role not found by this id : "+ id);
    }


}
