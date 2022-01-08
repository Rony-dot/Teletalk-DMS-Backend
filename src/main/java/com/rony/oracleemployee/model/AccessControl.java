package com.rony.oracleemployee.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tbl_access_control")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessControl {
    @Id
    private long id;
    private boolean emp_phone = false;
    private boolean emp_email = false;
    private boolean emp_manager_id = false;
    private boolean emp_hire_date = false;
    private boolean emp_job_title = false;
    private boolean cus_address = false;
    private boolean cus_credit_limit = false;
    private boolean cus_website = false;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
