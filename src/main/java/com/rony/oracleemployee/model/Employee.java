package com.rony.oracleemployee.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Employees")
public class Employee implements Serializable {
    @Id
    @Column(name = "EMPLOYEE_ID")
    private long employee_id;
    @Column(name = "FIRST_NAME")
    private String first_name;
    @Column(name = "LAST_NAME")
    private String last_name;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "HIRE_DATE")
    private LocalDate hire_date;
    @Column(name = "MANAGER_ID", nullable = true)
    private Integer manager_id;
    @Column(name = "JOB_TITLE")
    private String job_title;

    public int getManager_id() {
        if(manager_id == null){
            return 0;
        }
        return manager_id;
    }
}
