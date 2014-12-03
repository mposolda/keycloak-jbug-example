package org.keycloak.example;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.cache.NoCache;

/**
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
@Path("orders")
public class OrdersResource {

    @GET
    @Produces("application/json")
    @NoCache
    public List<Order> getCustomers() {
        List<Order> orders = new ArrayList<Order>();
        orders.add(new Order("order123", "New desktop computer"));
        orders.add(new Order("order456", "New keyboard"));
        orders.add(new Order("order789", "New monitor"));
        return orders;
    }
}
