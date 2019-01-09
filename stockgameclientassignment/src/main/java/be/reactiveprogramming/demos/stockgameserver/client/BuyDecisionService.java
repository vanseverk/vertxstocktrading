package be.reactiveprogramming.demos.stockgameserver.client;

import be.reactiveprogramming.demos.stockgameserver.shared.stocks.GameState;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.OrderAction;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.UserInfo;
import io.vertx.core.eventbus.EventBus;

public class BuyDecisionService {

    public BuyDecisionService(EventBus eventBus, RestRouter restRouter, String username, String password) {
        /*
         TODO 7D At this point we will retrieve and process the Event we sent in TODO 7B

         create a localConsumer on the event bus (using the InternalChannels.UPDATES.name()), and pass it a handler which can use the makeDecision to create
         an orderAction, then put it into an Order, along with your Username and Password and use the restRouter to send it to the server.
         !BEWARE! in case you decide to test this, make sure you won't accidentally spend all your money already on expensive stocks, or sell your current stocks too cheap.
         The code will run every time we get a stock update from the websockets, so your money can be spent quickly.
          */
    }

    /*
        TODO 8 Think up an interesting strategy to decide on when to buy, and sell your stocks. The decision will be called every time an update
         happens, so plan accordingly. Will you become the winner?
     */
    private OrderAction makeDecision(GameState body, String username) {
        UserInfo userInfo = body.findUser(username);
        return null;
    }
}
