package core;

import java.util.ArrayList;
import java.util.List;
import model.Order;
import model.Order.Side;

/**
 * Broker represents a market participant who can
 * create BUY and SELL orders and submit them to the Exchange.
 * Broker does not assign order ID 
 */

public class Broker {

    private final String name;	//Broker identity 
    private final List<Order> pendingOrders;	//Created orders by Broker that aren't ingested in Exchange yet

    public Broker(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Broker name cannot be null/blank.");
        }
        this.name = name.trim();
        this.pendingOrders = new ArrayList<>();	//Initialize empty queue of pending orders
    }

    public String getName() {
        return name;
    }

    //Create a buy order intent 
    // Broker queues "intent orders" with id=0 (unassigned). Exchange will assign real id.
    public void placeBuyOrder(String companyCode, int limitPrice, int quantity) {
        pendingOrders.add(new Order(0, name, companyCode, Side.BUY, limitPrice, quantity));
    }
    
    //Create a sell order 
    public void placeSellOrder(String companyCode, int limitPrice, int quantity) {
        pendingOrders.add(new Order(0, name, companyCode, Side.SELL, limitPrice, quantity));
    }

    //Returns a copy 
    public List<Order> getPendingOrders() {
        return new ArrayList<>(pendingOrders);
    }
    
    //Clears all pending orders after being ingested in Exchange
    public void clearPendingOrders() {
        pendingOrders.clear();
    }

    @Override
    public String toString() {
        return "Broker[" + name + "]";
    }
}