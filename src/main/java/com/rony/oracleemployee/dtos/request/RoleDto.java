package com.rony.oracleemployee.dtos.request;

import lombok.Data;

@Data
public class RoleDto {
    private String id;
    private String role;
    private String createdAt;
    private String modifiedAt;
}
