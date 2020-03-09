package com.armi.sample.jpa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @Column(name = "customer_id", insertable = false, nullable = false)
    private String id;

    @Column(name = "customer_role")
    private String role;

    @Column(name = "customer_name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "facsimile")
    private String facsimile;

    @Column(name = "attention")
    private String attention;

    @Column(name = "trans_date")
    private String transDate;

}
