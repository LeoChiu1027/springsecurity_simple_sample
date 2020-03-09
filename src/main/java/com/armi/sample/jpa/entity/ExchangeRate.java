package com.armi.sample.jpa.entity;

import com.armi.sample.jpa.entity.id.ExchangeRateId;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "exchange_rate")
public class ExchangeRate {

    @EmbeddedId
    private ExchangeRateId exchangeRateId;

    @Column(name = "rate")
    private Double rate;
}
