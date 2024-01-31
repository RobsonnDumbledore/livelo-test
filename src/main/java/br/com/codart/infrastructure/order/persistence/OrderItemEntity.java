package br.com.codart.infrastructure.order.persistence;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MapsId;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import br.com.codart.domain.order.OrderItem;
import br.com.codart.domain.order.OrderItemID;
import br.com.codart.domain.product.ProductID;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import br.com.codart.infrastructure.product.persistence.ProductEntity;

@Entity
@Table(name = "order_items")
public class OrderItemEntity {

    @Id
    @Column(name = "order_item_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "price_at_order", nullable = false)
    private Double priceAtOrder;

    public OrderItemEntity() {
    }

    public OrderItemEntity(
            String id,
            ProductEntity product,
            Integer quantity,
            Double priceAtOrder
    ) {

        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.priceAtOrder = priceAtOrder;
    }

    public OrderItemEntity(String id) {
        this.id = id;
    }

    public static OrderItemEntity fromDomain(OrderItem orderItem) {

        final var product = new ProductEntity(orderItem.getProductId().getValue());

        return new OrderItemEntity(
                orderItem.getId().getValue(),
                product,
                orderItem.getQuantity(),
                orderItem.getUnitPrice()
        );
    }

    public static OrderItem toDomain(OrderItemEntity orderItemEntity) {
        return OrderItem.with(
                OrderItemID.from(orderItemEntity.getId()),
                ProductID.from(orderItemEntity.getProduct().getId()),
                orderItemEntity.getQuantity(),
                orderItemEntity.getPriceAtOrder()
        );
    }

    public String getId() {
        return id;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPriceAtOrder() {
        return priceAtOrder;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof OrderItemEntity that)) return false;

        return new EqualsBuilder().append(id, that.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }
}


