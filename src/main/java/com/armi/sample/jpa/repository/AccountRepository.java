package com.armi.sample.jpa.repository;

import com.armi.sample.jpa.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "account", path = "account")
public interface AccountRepository extends CrudRepository<Account, String> {

    @RestResource(exported = false)
    @Override
    Iterable<Account> findAll();
}
