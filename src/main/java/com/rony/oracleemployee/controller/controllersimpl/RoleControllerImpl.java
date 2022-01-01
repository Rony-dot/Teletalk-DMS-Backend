package com.rony.oracleemployee.controller.controllersimpl;

import com.rony.oracleemployee.controller.RoleController;
import com.rony.oracleemployee.model.Role;
import com.rony.oracleemployee.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class RoleControllerImpl implements RoleController {

    private final RoleService roleService;

    @Override
    public ResponseEntity<Void> add(Role role) {
        roleService.add(role);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Role> getById(long id) {
        return ResponseEntity.ok().body(roleService.getById(id));
    }

    @Override
    public ResponseEntity<List<Role>> getAll() {
        return ResponseEntity.ok().body(roleService.getAll());
    }

    @Override
    public ResponseEntity<Void> deleteById(long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> update( Role role, long id) {
        return null;
    }
}
