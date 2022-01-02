package com.rony.oracleemployee.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Customers")
public class Customer implements Serializable {
    @Id
    @Column(name = "CUSTOMER_ID")
    private long customer_id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "WEBSITE")
    private String website;

    @Column(name = "CREDIT_LIMIT")
    private double credit_limit;
}
