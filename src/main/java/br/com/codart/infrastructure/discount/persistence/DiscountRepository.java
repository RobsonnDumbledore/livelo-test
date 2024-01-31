package br.com.codart.infrastructure.discount.persistence;

import java.util.Optional;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<DiscountSettingsEntity, Long> {

    @Query("SELECT ds.discountPercentage FROM DiscountSettingsEntity ds WHERE ds.paymentType = :paymentType")
    Optional<BigDecimal> findDiscountByPaymentName(String paymentType);

}
