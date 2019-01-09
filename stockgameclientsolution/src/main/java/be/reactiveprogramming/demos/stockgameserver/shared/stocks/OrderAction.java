package be.reactiveprogramming.demos.stockgameserver.shared.stocks;

import java.io.Serializable;

public class OrderAction implements Serializable {

    private static final long serialVersionUID = -5966323894577152798L;

    private OrderType type;
    private int amount;

    public OrderAction() {
    }

    public OrderAction(final OrderType type, final int amount) {
        this.type = type;
        this.amount = amount;
    }

    public OrderType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

}
