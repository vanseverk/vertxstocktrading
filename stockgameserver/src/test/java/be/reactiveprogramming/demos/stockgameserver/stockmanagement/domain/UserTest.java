package be.reactiveprogramming.demos.stockgameserver.stockmanagement.domain;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class UserTest {

    private User user;

    @Before
    public void init() {
        user = new User("a", 100);
    }

    @Test
    public void testBuy() {
        user.buy(5, 5);
        assertEquals(75, user.getMoney());
        assertEquals(5, user.getShares());
    }

    @Test
    public void testBuyTooMuchGoesToMax() {
        user.buy(Integer.MAX_VALUE, 47);
        assertEquals(6, user.getMoney());
        assertEquals(2, user.getShares());
    }

    @Test
    public void testSell() {
        user.buy(5, 5);
        assertEquals(75, user.getMoney());
        assertEquals(5, user.getShares());

        user.sell(2, 2);
        assertEquals(79, user.getMoney());
        assertEquals(3, user.getShares());
    }

    @Test
    public void testSellTooMuchGoesToMin() {
        user.buy(5, 5);
        assertEquals(75, user.getMoney());
        assertEquals(5, user.getShares());

        user.sell(Integer.MAX_VALUE, 2);
        assertEquals(85, user.getMoney());
        assertEquals(0, user.getShares());
    }
}
