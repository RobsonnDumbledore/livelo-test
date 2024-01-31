package br.com.codart.infrastructure.order.persistence;

import java.util.Set;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.util.stream.Collectors;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import br.com.codart.domain.order.Order;
import br.com.codart.domain.order.OrderID;
import br.com.codart.domain.order.OrderItem;
import br.com.codart.domain.payment.PaymentType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "payment_type", nullable = false)
    private String paymentType;

    @Column(nullable = false)
    private Double total;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<OrderItemEntity> orderItems;

    public OrderEntity() {
    }

    public OrderEntity(
            String orderId,
                       String userId,
                       String paymentType,
                       Double total,
                       LocalDateTime createdAt,
                       Set<OrderItemEntity> orderItems
    ) {
        this.orderId = orderId;
        this.userId = userId;
        this.paymentType = paymentType;
        this.total = total;
        this.createdAt = createdAt;
        this.orderItems = orderItems;
    }

    public static OrderEntity fromDomain(Order order) {
        OrderEntity orderEntity = new OrderEntity(
                order.getId().getValue(),
                UUID.randomUUID().toString(),
                order.getPaymentType().toString(),
                order.getTotal(),
                LocalDateTime.now(),
                null
        );

        Set<OrderItemEntity> orderItemEntities = order.getItems().stream()
                .map(orderItem -> {
                    OrderItemEntity orderItemEntity = OrderItemEntity.fromDomain(orderItem);
                    orderItemEntity.setOrder(orderEntity);
                    return orderItemEntity;
                })
                .collect(Collectors.toSet());

        orderEntity.setOrderItems(orderItemEntities);
        return orderEntity;
    }

    public OrderEntity(String orderId) {
        this.orderId = orderId;
    }

    public static Order toDomain(OrderEntity orderEntity) {

        List<OrderItem> orderItems = orderEntity.getOrderItems().stream()
                .map(OrderItemEntity::toDomain)
                .collect(Collectors.toList());

        return Order.with(
                OrderID.from(orderEntity.getOrderId()),
                orderItems,
                PaymentType.valueOf(orderEntity.getPaymentType()),
                null
        );
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public Double getTotal() {
        return total;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Set<OrderItemEntity> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItemEntity> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof OrderEntity that)) return false;

        return new EqualsBuilder().append(orderId, that.orderId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(orderId).toHashCode();
    }
}

