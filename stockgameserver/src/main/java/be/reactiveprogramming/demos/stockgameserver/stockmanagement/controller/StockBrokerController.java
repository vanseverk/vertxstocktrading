package be.reactiveprogramming.demos.stockgameserver.stockmanagement.controller;

import be.reactiveprogramming.demos.stockgameserver.shared.registration.UserRegistration;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.GameState;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.Order;
import be.reactiveprogramming.demos.stockgameserver.stockmanagement.services.StatisticsServiceImpl;
import be.reactiveprogramming.demos.stockgameserver.stockmanagement.services.StockBrokerServiceImpl;
import be.reactiveprogramming.demos.stockgameserver.stockmanagement.services.UserServiceImpl;
import be.reactiveprogramming.demos.stockgameserver.stockmanagement.services.StatisticsServiceImpl;
import be.reactiveprogramming.demos.stockgameserver.stockmanagement.services.StockBrokerServiceImpl;
import be.reactiveprogramming.demos.stockgameserver.stockmanagement.services.UserServiceImpl;
import io.vertx.core.Vertx;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.Json;

import java.util.ArrayList;
import java.util.List;

public class StockBrokerController {

    private StockBrokerServiceImpl stockBrokerService = new StockBrokerServiceImpl();

    private UserServiceImpl userService = new UserServiceImpl();

    private StatisticsServiceImpl statisticsService = new StatisticsServiceImpl();

    private List<ServerWebSocket> clients = new ArrayList<>();

    public void doOrder(Order order) {
        statisticsService.orderDone();
        userService.doOrder(order, stockBrokerService.getSharePrice());
    }

    public void start(String channel, Vertx vertx) {
        vertx.setPeriodic(1000, id -> {

            stockBrokerService.tick();

            final String gameState = Json.encodePrettily(new GameState(stockBrokerService.getSharePrice(), statisticsService.getOrdersDone(), userService.overview()));

            vertx.eventBus().send(channel, gameState);

            List<ServerWebSocket> toRemoveList = new ArrayList<>();
            for (ServerWebSocket client : clients) {
                try {
                    client.writeFinalTextFrame(gameState);
                } catch (IllegalStateException ex) { //WS disconnected...
                    toRemoveList.add(client);
                }
            }
            for (ServerWebSocket toRemove : toRemoveList) {
                clients.remove(toRemove);
            }
        });
    }

    public void addWebsocketClient(ServerWebSocket websocket) {
        clients.add(websocket);
    }

    public String doRegistration(UserRegistration registration) {
        return userService.registerUser(registration, stockBrokerService.getStarterMoney());
    }
}
