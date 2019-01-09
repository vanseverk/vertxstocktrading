package be.reactiveprogramming.demos.stockgameserver.stockmanagement.services;

import java.util.Random;

/**
 * Created by Kristof on 12/01/2017.
 */
public class StockBrokerServiceImpl {

    private static Random r = new Random();

    private long sharePrice = 20;

    private boolean increasing = true;

    private long upGoal = 100;

    private long nextGoal = upGoal;

    private boolean crashing = false;

    private long crashPrice = 200;

    public void tick() {

        if (sharePrice > crashPrice) {
            crashing = true;
        }

        if (!crashing) {
            if (increasing) {
                sharePrice += randomNumberBetween(adjustForInflation(10), -adjustForInflation(5));

                if (sharePrice > upGoal) {
                    increasing = !increasing;
                    nextGoal = (upGoal / 10) * (2 + r.nextInt(4));

                    upGoal *= 1.1;
                }
            } else {
                sharePrice += randomNumberBetween(adjustForInflation(5), -adjustForInflation(10));
                sharePrice = Math.max(5, sharePrice);

                if (sharePrice < nextGoal) {
                    increasing = !increasing;
                    nextGoal = upGoal;
                }
            }
        } else {
            sharePrice -= r.nextInt(10);

            if (sharePrice < 50) {
                crashing = false;
                crashPrice += 50;
            }
        }

        System.out.println("Share price: " + sharePrice);
    }

    private int adjustForInflation(double number) {
        int adjustedNumber = (int) Math.round(number + number / 10);
        return adjustedNumber;
    }

    public long getSharePrice() {
        return sharePrice;
    }

    public long getStarterMoney() {
        return sharePrice * 10;
    }

    private int randomNumberBetween(final int max, final int min) {
        return max - r.nextInt(max - min);
    }
}
