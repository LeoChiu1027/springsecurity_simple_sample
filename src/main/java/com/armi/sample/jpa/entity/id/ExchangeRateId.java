package com.armi.sample.jpa.entity.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExchangeRateId implements Serializable {

    @Column(name = "date_id")
    private String dateId;

    @Column(name = "currency")
    private String currency;
}
