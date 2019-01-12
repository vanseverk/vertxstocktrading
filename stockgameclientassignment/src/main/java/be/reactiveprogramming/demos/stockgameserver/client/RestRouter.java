package be.reactiveprogramming.demos.stockgameserver.client;

import be.reactiveprogramming.demos.stockgameserver.shared.registration.UserRegistration;
import be.reactiveprogramming.demos.stockgameserver.shared.server.ServerInfo;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.Order;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.OrderAction;
import be.reactiveprogramming.demos.stockgameserver.shared.registration.UserRegistration;
import be.reactiveprogramming.demos.stockgameserver.shared.server.ServerInfo;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.OrderAction;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.json.Json;

public class RestRouter {

    private final HttpClient httpClient;

    public RestRouter(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /*
        TODO 4 Now let's try to send a BUY order to the server! (Hint: use the STOCK_URL)

        You can try it out by calling this method from the ClientStarter after the application starts up.
     */

    public void sendOrder(OrderAction orderAction, String username, String password) {
        final String orderInformation = Json.encodePrettily(new Order(orderAction, username, password));
    }

    /*
        TODO 3 This is the method that's being called to register ourselves in the stockBroker server. We get a UserRegistration object that we'll need
        to send to the server's registration url. With the response of the server we should call the handler callback argument so it gets printed
        to the command line. This will enable us to process the response as an event.

        HINT: Use the httpClient's post method, see the ServerInfo file for the correct REST port, server, and registration URL.
    */
    public void doRegistration(final UserRegistration registration, final Handler<String> handler) {
        final String registrationInformation = Json.encodePrettily(registration);


    }
}
