package be.reactiveprogramming.demos.stockgameserver.stockmanagement.domain;

public class User {

    private String username;
    private long money;
    private int shares;

    public User(String username, long money) {
        this.username = username;
        this.money = money;
        this.shares = 0;
    }

    public void buy(final int amount, final long cost) {
        final long totalCost = amount * cost;
        final long affordableTotalCost = Math.min(totalCost, money);
        final long affordableSharesAmount = Math.floorDiv(affordableTotalCost, cost);
        final long finalCost = cost * affordableSharesAmount;

        money -= finalCost;
        shares += affordableSharesAmount;

        System.out.println(username + " bought " + amount + " shares for " + finalCost);
    }

    public void sell(final int amount, final long cost) {
        final long amountToSell = shares - Math.max(shares - amount, 0);
        final long totalGain = amountToSell * cost;

        shares -= amountToSell;
        money += totalGain;

        System.out.println(username + " sold " + amountToSell + " shares for " + totalGain);
    }

    public String getUsername() {
        return username;
    }

    public long getMoney() {
        return money;
    }

    public int getShares() {
        return shares;
    }

}
