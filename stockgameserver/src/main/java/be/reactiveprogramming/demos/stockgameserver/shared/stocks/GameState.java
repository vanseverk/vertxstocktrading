package be.reactiveprogramming.demos.stockgameserver.shared.stocks;

import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {

    private static final long serialVersionUID = -7815629107498631341L;

    private long stockValue;
    private long buys;
    private List<UserInfo> overview;

    private GameState() {

    }

    public GameState(long stockValue, long buys, List<UserInfo> overview) {
        this.stockValue = stockValue;
        this.buys = buys;
        this.overview = overview;
    }

    public long getStockValue() {
        return stockValue;
    }

    public long getBuys() {
        return buys;
    }

    public List<UserInfo> getOverview() {
        return overview;
    }
}
