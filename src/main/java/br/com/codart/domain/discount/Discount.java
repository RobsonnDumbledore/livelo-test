package br.com.codart.domain.discount;

import br.com.codart.domain.product.Price;

public abstract class Discount {

    public abstract Price applyDiscount(Price originalPrice);
}
