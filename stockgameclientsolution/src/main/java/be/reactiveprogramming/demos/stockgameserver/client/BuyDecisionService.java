package be.reactiveprogramming.demos.stockgameserver.client;

import be.reactiveprogramming.demos.stockgameserver.shared.stocks.GameState;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.OrderAction;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.OrderType;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.UserInfo;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.GameState;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.OrderAction;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.OrderType;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.UserInfo;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;

public class BuyDecisionService {

    long buyPrice = 0;
    long maxFound = 90;

    public BuyDecisionService(EventBus eventBus, RestRouter restRouter, String username) {
        eventBus.localConsumer(Channels.UPDATES.name(), (Handler<Message<GameState>>) message -> {
            final OrderAction action = makeDecision(message.body(), username);

            if (action != null) {
                restRouter.sendOrder(action);
            }
        });
    }

    private OrderAction makeDecision(GameState body, String username) {
        UserInfo userInfo = body.findUser(username);

        if (userInfo.getShares() == 0) {
            if (body.getStockValue() <= maxFound / 2) {
                buyPrice = body.getStockValue();
                return new OrderAction(OrderType.BUY, Integer.MAX_VALUE);
            }
        } else if (body.getStockValue() > maxFound) {
            return new OrderAction(OrderType.SELL, Integer.MAX_VALUE);
        }

        maxFound = Math.max(maxFound, body.getStockValue());

        return null;
    }
}
