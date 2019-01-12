package be.reactiveprogramming.demos.stockgameserver;

import be.reactiveprogramming.demos.stockgameserver.client.BuyDecisionService;
import be.reactiveprogramming.demos.stockgameserver.client.RestRouter;
import be.reactiveprogramming.demos.stockgameserver.client.Secrets;
import be.reactiveprogramming.demos.stockgameserver.client.codec.GameStateCodec;
import be.reactiveprogramming.demos.stockgameserver.shared.registration.UserRegistration;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.GameState;
import io.vertx.core.Vertx;

import java.util.Scanner;

public class ClientStarter {

    public static void main(String[] args) {
        new Client().start();
        new Scanner(System.in).nextLine();
    }

    static class Client {

        private static void startServices(Vertx vertx, RestRouter restRouter, String username) {
            /**
             * TODO 5 uncomment the following line of code, and run the application.
             * It might happen the connection gets refused. In case this happens, simply try running it again. You should notice a couple of messages coming in. These
             * will contain information provided by the server on the current stock price, and stats of all the players.
             */
            //new WebsocketRouter(vertx.eventBus(), vertx.createHttpClient());


            new BuyDecisionService(vertx.eventBus(), restRouter, username);
        }

        void start() {

            Vertx vertx = Vertx.vertx();

            vertx.eventBus().registerDefaultCodec(GameState.class, new GameStateCodec());

            RestRouter restRouter = new RestRouter(vertx.createHttpClient());

            final String username = "Kristof";

            /*
               TODO 2 The goal of our application is to buy and sell stocks automatically and make the most profit! First step is to register ourself
               with the stock broker service using a REST call to the /register endpoint. This will result in a token that you will have to use for validating
               your orders later (HINT: the token will be printed in command line). Don't forget to change the username above to a username of your choosing.
               Make sure to save the password you receive in the Secrets.java class, so you can reuse it later! Before anything is sent, you'll have to implement
               the doRegistration method in the restRouter first though.
             */

            if (Secrets.password.isEmpty()) {
                restRouter.doRegistration(new UserRegistration(username), password -> {
                    System.out.println("Password is " + password + " don't forget to save it in Secrets.java so you don't accidentally register twice!");
                });
            } else {
                // You can ignore the following line until step 6, but make sure not to remove it...
                startServices(vertx, restRouter, username);
            }
        }
    }
}
