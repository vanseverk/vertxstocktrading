package be.reactiveprogramming.demos.stockgameserver.shared.stocks;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private static final long serialVersionUID = -2348153587666046353L;

    private String username;
    private long money;
    private long shares;

    private UserInfo() {
    }

    public UserInfo(String username, long money, int shares) {
        this.username = username;
        this.money = money;
        this.shares = shares;
    }

    public String getUsername() {
        return username;
    }

    public long getMoney() {
        return money;
    }

    public long getShares() {
        return shares;
    }
}
