package be.reactiveprogramming.demos.stockgameserver.client;

import be.reactiveprogramming.demos.stockgameserver.shared.server.ServerInfo;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.GameState;
import be.reactiveprogramming.demos.stockgameserver.shared.server.ServerInfo;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.GameState;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.Json;

public class WebsocketRouter {

    public WebsocketRouter(EventBus eventBus, HttpClient httpClient) {
        startWebsocketClient(eventBus, false, ServerInfo.WEBSOCKET, ServerInfo.WEBSOCKET2, httpClient);
    }

    public void startWebsocketClient(final EventBus eventBus, final boolean useA, final int portA, final int portB, final HttpClient client) {
        System.out.println("Starting websocket router connection");
        try {
            client.websocket((useA ? portA : portB), ServerInfo.SERVER, ServerInfo.WEBSOCKET_CHANNEL, webSocket -> {
                webSocket.handler(data -> {
                    GameState newState = Json.decodeValue(data.toString("ISO-8859-1"), GameState.class);
                    System.out.println("Got websocket info. Current price is " + newState.getStockValue());
                    eventBus.publish(Channels.UPDATES.name(), newState);
                }).closeHandler(data -> {
                    System.out.println("Websocket closed");
                    startWebsocketClient(eventBus, !useA, portA, portB, client);
                }).exceptionHandler(data -> {
                    System.out.println("Websocket exception!");
                    startWebsocketClient(eventBus, !useA, portA, portB, client);
                });
            }, throwable -> {
                System.out.println("Exception at startup");
                startWebsocketClient(eventBus, !useA, portA, portB, client);
            });
        } catch (Exception ex) {
            startWebsocketClient(eventBus, !useA, portA, portB, client);
        }
    }

}
