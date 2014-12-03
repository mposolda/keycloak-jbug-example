package org.keycloak.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public class ExampleServlet extends HttpServlet {

    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.println("<h1>Servlet application retrieving list of orders from backend rest-app</h1>");

        try {
            List<Order> orders = readOrdersFromRestApp();
            writer.println("");
            for (Order order : orders) {
                writer.println(order + "<br>");
            }
        } catch (OrderRetrieveException e) {
            writer.println("<b>Error occured: " + e.getMessage() + "</b>");
        }

        writer.flush();
        writer.close();
    }

    private List<Order> readOrdersFromRestApp() throws OrderRetrieveException {
        DefaultHttpClient client = new DefaultHttpClient();
        try {
            HttpGet get = new HttpGet("http://localhost:8080/rest-app/orders");
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new OrderRetrieveException("Can't load data from rest-app. Status : " + response.getStatusLine().getStatusCode());
            }

            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            try {
                OrdersList data = objectMapper.readValue(is, OrdersList.class);
                return data;
            } finally {
                is.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new OrderRetrieveException("Unknown error. See server log for details");
        } finally {
            client.getConnectionManager().shutdown();
        }
    }

    private class OrderRetrieveException extends Exception {

       public OrderRetrieveException(String message) {
           super(message);
       }

    }

    public static class OrdersList extends ArrayList<Order> {};
}
