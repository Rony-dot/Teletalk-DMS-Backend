package com.rony.oracleemployee.services;

import com.rony.oracleemployee.model.AccessControl;
import com.rony.oracleemployee.model.User;
import com.rony.oracleemployee.repository.AccessControlRepository;
import com.rony.oracleemployee.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class AccessControlService {
    @Autowired
    private AccessControlRepository accessControlRepository;
    @Autowired
    private UserRepository userRepository;

    public void generateDefaultValues(User savedUser) {
        var accessControl = new AccessControl();
        accessControl.setUser(savedUser);
        AccessControl result = accessControlRepository.save(accessControl);
        System.out.println(result.getId());
    }

    public AccessControl getAccessControlListByUserId(long userId){
        return accessControlRepository.getAccessControlsByUserId(userId);
    }

    public AccessControl update(long id, AccessControl accessControl) {
        AccessControl prevAccessControl = accessControlRepository.getAccessControlsByUserId(id);
        long accessControlId = prevAccessControl.getId();
        BeanUtils.copyProperties(accessControl,prevAccessControl);
        prevAccessControl.setId(accessControlId);
        prevAccessControl.setUser(userRepository.getById(id));
        return accessControlRepository.save(prevAccessControl);
    }
}
