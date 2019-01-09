package be.reactiveprogramming.demos.stockgameserver.stockmanagement.services;

import be.reactiveprogramming.demos.stockgameserver.shared.registration.UserRegistration;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.Order;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.OrderType;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.UserInfo;
import be.reactiveprogramming.demos.stockgameserver.stockmanagement.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl {

    private SecurityServiceImpl securityService = new SecurityServiceImpl();

    private Map<String, User> overview = new HashMap<>();

    public void doOrder(Order order, long currentStockPrice) {
        if(securityService.isValidPair(order.getUsername(), order.getPassword())) {
            if (OrderType.BUY.equals(order.getAction().getType())) {
                overview.get(order.getUsername()).buy(order.getAction().getAmount(), currentStockPrice);
            } else if (OrderType.SELL.equals(order.getAction().getType())) {
                overview.get(order.getUsername()).sell(order.getAction().getAmount(), currentStockPrice);
            } else {
                throw new IllegalArgumentException("Did not recognize order action " + order.getAction());
            }
        } else {
            throw new IllegalArgumentException("Invalid security pair for user " + order.getUsername());
        }
    }

    public List<UserInfo> overview() {
        List<UserInfo> result = new ArrayList<>();
        for (User user : overview.values()) {
            result.add(new UserInfo(user.getUsername(), user.getMoney(), user.getShares()));
        }
        return result;
    }

    public String registerUser(UserRegistration registration, long money) {
        String username = registration.getUsername();

        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("We require a username");
        }

        if (overview.containsKey(username)) {
            throw new IllegalArgumentException("Username is already registered");
        }

        String generatedPassword = securityService.generatePassword(username);

        overview.put(username, new User(username, money));

        return generatedPassword;
    }
}
