package com.rony.oracleemployee.controller;

import com.rony.oracleemployee.model.AccessControl;
import com.rony.oracleemployee.services.AccessControlService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/accessControls")
@RestController
@AllArgsConstructor
public class AccessController {
    @Autowired
    private AccessControlService accessControlService;

    @GetMapping("/{id}")
    public ResponseEntity<AccessControl> getAccessControlByUserId(@PathVariable long id){
        return ResponseEntity.ok(accessControlService.getAccessControlListByUserId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccessControl> updateAccessControlByUserId(@PathVariable long id, @RequestBody AccessControl accessControl){
        return ResponseEntity.ok(accessControlService.update(id,accessControl));
    }
}

