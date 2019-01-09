package be.reactiveprogramming.demos.stockgameserver.stockmanagement.services;

/**
 * Created by Kristof on 12/01/2017.
 */
public class StatisticsServiceImpl {

    private int ordersDone = 0;

    public void orderDone() {
        ordersDone++;
    }

    public int getOrdersDone() {
        return ordersDone;
    }

}
