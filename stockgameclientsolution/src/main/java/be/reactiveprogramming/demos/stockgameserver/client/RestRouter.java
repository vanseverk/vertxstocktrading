package be.reactiveprogramming.demos.stockgameserver.client;

import be.reactiveprogramming.demos.stockgameserver.shared.registration.UserRegistration;
import be.reactiveprogramming.demos.stockgameserver.shared.server.ServerInfo;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.Order;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.OrderAction;
import be.reactiveprogramming.demos.stockgameserver.shared.registration.UserRegistration;
import be.reactiveprogramming.demos.stockgameserver.shared.server.ServerInfo;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.Order;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.OrderAction;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.json.Json;

public class RestRouter {

    private final HttpClient httpClient;

    private String username;

    private String password;

    public RestRouter(HttpClient httpClient, String username) {
        this.httpClient = httpClient;
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void sendOrder(OrderAction order) {
        String encodedAction = Json.encodePrettily(new Order(order, this.username, this.password));

        HttpClientRequest request = httpClient.post(ServerInfo.REST, ServerInfo.SERVER, ServerInfo.STOCK_URL, httpClientResponse -> {
            if (httpClientResponse.statusCode() != 200) {
                System.err.println("Problem with the request! Received status code " + httpClientResponse.statusCode() + " " + httpClientResponse.statusMessage());
            }
        }).putHeader("content-type", "application/json").putHeader("content-length", Integer.toString(encodedAction.length()));

        request.write(encodedAction).end();
    }

    public void doRegistration(final UserRegistration registration, final Handler<String> handler) {
        final String registrationAction = Json.encodePrettily(registration);

        HttpClientRequest request = httpClient.post(ServerInfo.REST, ServerInfo.SERVER, ServerInfo.REGISTRATION_URL, httpClientResponse -> {
            httpClientResponse.bodyHandler(totalBuffer -> {
                String password = totalBuffer.toString();
                this.password = password;
                handler.handle(password);
            });
        }).putHeader("content-type", "application/json").putHeader("content-length", Integer.toString(registrationAction.length()));

        request.end(registrationAction);
    }
}
