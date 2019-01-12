package be.reactiveprogramming.demos.stockgameserver.client;

import be.reactiveprogramming.demos.stockgameserver.shared.server.ServerInfo;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.GameState;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.Json;

public class WebsocketRouter {

    private final EventBus eventBus;

    public WebsocketRouter(EventBus eventBus, HttpClient httpClient) {
        startWebsocketClient(ServerInfo.WEBSOCKET, httpClient);
        this.eventBus = eventBus;
    }

    /*
     TODO 6 Our websocket client starts a websocket connection to the server to receive price updates.

     But.. uh-oh! The Stock websocket service seems to be very unreliable, but we have been given the promise that if one of them goes down, we can rely on a backup service.
     However, when the regular websocket service comes up again, the backup one will be closed automatically.
     Modify the websocket client code, so that if the connection goes down, it automatically switches over to the other port and vice versa.
     You need to do this in a way that resembles recursion. Try using the connection close and exception handler to do this.
    */
    public void startWebsocketClient(final int port, final HttpClient client) {
        System.out.println("Starting websocket router connection");

        client.websocket(port, ServerInfo.SERVER, ServerInfo.WEBSOCKET_CHANNEL, webSocket -> {
            webSocket.handler(data -> {
                GameState newState = Json.decodeValue(data.toString("ISO-8859-1"), GameState.class);
                System.out.println("Got websocket info. Current price is " + newState.getStockValue());
            });
        });
    }

    /*
     TODO 7A For the next part of our application, we want to start automating the buying and selling of stocks, based on the information we received through
     the websocket service. To do so, we will use our BuyDecisionService, which will make decisions on buying and selling of orders. Because we want to build
     our application in loose components, we will be using Vertx's internal event bus to send a message from our WebsocketRouter to our BuyDecisionService,
     so it can react appropriately. Use the websocket handler above, so that it also publishes the newState object to the eventBus.
     You can find the eventBus in a field in this class. Use InternalChannels.UPDATES.name() (or another chosen name) as the name of the channel to put it on.
     By sending it on a channel on the eventbus everyone who listens to that channel will receive it, even on a clustered system.
     */
}
