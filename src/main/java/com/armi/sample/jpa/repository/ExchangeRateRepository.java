package com.armi.sample.jpa.repository;

import com.armi.sample.jpa.entity.id.ExchangeRateId;
import com.armi.sample.jpa.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "exchangeRate", path = "exchangeRate")
public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, ExchangeRateId>, JpaSpecificationExecutor<ExchangeRate> {

    Iterable<ExchangeRate> findByExchangeRateIdDateId(String date_id);
}
