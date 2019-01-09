package be.reactiveprogramming.demos.stockgameserver.shared.stocks;

import java.io.Serializable;

public class Order implements Serializable {

    private static final long serialVersionUID = -981645742429298371L;

    private OrderAction action;
    private String username;
    private String password;

    private Order() {
    }

    public Order(OrderAction action, String username, String password) {
        this.action = action;
        this.username = username;
        this.password = password;
    }

    public OrderAction getAction() {
        return action;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
