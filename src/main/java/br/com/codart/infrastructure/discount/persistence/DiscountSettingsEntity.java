package br.com.codart.infrastructure.discount.persistence;

import java.math.BigDecimal;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "discount_settings")
public class DiscountSettingsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_type", nullable = false)
    private String paymentType;

    @Column(name = "discount_percentage", nullable = false)
    private BigDecimal discountPercentage;


    public DiscountSettingsEntity() {
    }

    public DiscountSettingsEntity(String paymentType, BigDecimal discountPercentage) {
        this.paymentType = paymentType;
        this.discountPercentage = discountPercentage;
    }

    public Long getId() {
        return id;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }
}
