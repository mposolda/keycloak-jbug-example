package org.keycloak.example;

/**
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public class Order {

    private String orderId;
    private String orderDescription;

    public Order() {}

    public Order(String orderId, String orderDescription) {
        this.orderId = orderId;
        this.orderDescription = orderDescription;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    @Override
    public String toString() {
        return String.format("Order [ orderId=" + orderId + ", orderDescription=" + orderDescription + "]");
    }
}
