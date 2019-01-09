package be.reactiveprogramming.demos.stockgameserver.client;

import be.reactiveprogramming.demos.stockgameserver.shared.registration.UserRegistration;
import be.reactiveprogramming.demos.stockgameserver.shared.server.ServerInfo;
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
        TODO 4 Let's try to send a Buy or Sell request to the server
        Since one of the goals of our application is to send buy and sell requests to the broker server, let's try exactly that.
        Take a look at how we sent a registration request, but this time send an Order instead. To send the order,
        we need to send a POST request to the STOCK_URL instead of the REGISTRATION_URL. The order requires an
        OrderAction, and your username and password encoded as JSON.

        You can try it out by calling this method from the ClientStarter after the application starts up.
     */

    public void sendOrder(OrderAction orderAction, String username, String password) {

    }

    /*
        TODO 3 Let's take a closer look at what actually happened in step number 2.
        We sent a POST request to the stock server to get a token based for our username. We did this in a non-blocking way by using an event handler.
        The event handler will be called automatically after the server gives a response, but no blocking had to happen.
    */
    public void doRegistration(final UserRegistration registration, final Handler<String> handler) {
        final String registrationAction = Json.encodePrettily(registration);

        System.out.println("Sending a reqistration request to the server");

        HttpClientRequest request = httpClient.post(ServerInfo.REST, ServerInfo.SERVER, ServerInfo.REGISTRATION_URL, httpClientResponse -> {
            System.out.println("Response code for registration was " + httpClientResponse.statusCode());
            httpClientResponse.bodyHandler(totalBuffer -> {
                String password = totalBuffer.toString();
                handler.handle(password);
            });
        }).putHeader("content-type", "application/json").putHeader("content-length", Integer.toString(registrationAction.length()));

        System.out.println("Waiting for the server's response...");

        request.end(registrationAction);
    }
}
