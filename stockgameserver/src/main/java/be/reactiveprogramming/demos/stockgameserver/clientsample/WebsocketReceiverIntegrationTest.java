package be.reactiveprogramming.demos.stockgameserver.clientsample;

import be.reactiveprogramming.demos.stockgameserver.shared.registration.UserRegistration;
import be.reactiveprogramming.demos.stockgameserver.shared.server.ServerInfo;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.Order;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.OrderAction;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.OrderType;
import be.reactiveprogramming.demos.stockgameserver.shared.registration.UserRegistration;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.Order;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.OrderAction;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.OrderType;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.json.Json;

import java.util.Random;
import java.util.Scanner;

public class WebsocketReceiverIntegrationTest {

    private static final Random r = new Random();

    private static final int maxClients = 20;

    private static final int restPort = ServerInfo.REST;

    public static void main(String[] args) {
        System.out.println("Started demo WS client");

        System.out.println("Will now connect to WS server");

        HttpClient client = Vertx.vertx().createHttpClient();


        client.websocket(ServerInfo.WEBSOCKET, "localhost", "gameupdates", webSocket -> {
           webSocket.handler( data -> {
                System.out.print("Received data " + data.toString("ISO-8859-1"));
           });
        });

        System.out.println("Ready to receive data");

        System.out.print("Asking for a hello");
        client.get(restPort, "localhost", "/hello",  httpClientResponse -> {
            System.out.println("Received hello response with status code " + httpClientResponse.statusCode());
        }).end();

        for (int i = 0; i < maxClients; i++) {
            registerStartBuyingForPlayer("Player " + i, client);
        }

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext() && ! scanner.next().equals("q")) {}

        System.out.println("Stopped");
        client.close();
    }

    public static void registerStartBuyingForPlayer(String playerName, HttpClient client) {
        String registrationAction = Json.encodePrettily(new UserRegistration(playerName));

        HttpClientRequest request = client.post(restPort,"localhost", "/register", httpClientResponse -> {
            httpClientResponse.bodyHandler(totalBuffer -> {
                String password = totalBuffer.toString();
                System.out.println("Password: " + password);
                startBuySell(playerName, password, client);
            });
        }).putHeader("content-type", "application/json").putHeader("content-length", Integer.toString(registrationAction.length()));

        request.write(registrationAction).end();
    }

    private static void startBuySell(String playerName, String password, HttpClient client) {
        Vertx.vertx().setPeriodic(500, id -> {
            randomBuySell(playerName, password, client);
        });
    }

    private static void randomBuySell(String username, String password, HttpClient client) {
        OrderAction action = new OrderAction(r.nextBoolean() ? OrderType.BUY : OrderType.SELL, 5);
        String encodedAction = Json.encodePrettily(new Order(action, username, password));

        HttpClientRequest request = client.post(restPort, "localhost", "/stock",  httpClientResponse -> {
            System.out.println("Received response with status code " + httpClientResponse.statusCode());
        }).putHeader("content-type", "application/json").putHeader("content-length", Integer.toString(encodedAction.length()));

        request.write(encodedAction).end();

        System.out.println("Sending for " + encodedAction);
    }
}
