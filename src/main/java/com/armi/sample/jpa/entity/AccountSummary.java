package com.armi.sample.jpa.entity;


import org.springframework.data.rest.core.config.Projection;

@Projection(name = "accountSummary" , types = Account.class)
public interface AccountSummary {

    String getId();

    String getRole();

    Customer getCustomer();
}
