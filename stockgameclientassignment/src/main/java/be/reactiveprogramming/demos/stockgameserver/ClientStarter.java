package be.reactiveprogramming.demos.stockgameserver;

import be.reactiveprogramming.demos.stockgameserver.client.RestRouter;
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
             * TODO 5 uncomment the following line of code, and run the application. (Make sure you don't accidentally send a registration or order request when doing so..)
             * It might happen the connection gets refused. In case this happens, simply try running it again. You should notice a couple of messages coming in. These
             * will contain information provided by the server on the current stock price, and stats of all the players.
             */
            //new WebsocketRouter(vertx.eventBus(), vertx.createHttpClient());

            /**
             * TODO 7C Uncomment the following line of code, and go into the BuyDecisionService
             */
            //new BuyDecisionService(vertx.eventBus(), restRouter, username);
        }

        void start() {
            /*
                TODO 1 The first part your first assignment will be a simple one. Fill in your name as the username on the line below
             */
            final String username = "Kristof";

            Vertx vertx = Vertx.vertx();

            /*
                TODO 7A For the next part of our application, we want to start automating the buying and selling of stocks, based on the information we received through
                 the websocket service. To do so, we will use our BuyDecisionService, which will make decisions on buying and selling of orders. Because we want to build
                 our application in loose components, we will be using Vertx's internal event bus to send a message from our WebsocketRouter to our BuyDecisionService,
                 so it can react appropriately.

                 Uncomment the following line of code. It is required to be able to send the gamestate as an object over your application's eventbus.
             */
            // vertx.eventBus().registerDefaultCodec(GameState.class, new GameStateCodec());

            RestRouter restRouter = new RestRouter(vertx.createHttpClient());

            /*
                TODO 2 The goal of our application will be to buy and sell stocks automatically, to do so you'll need to register yourself first (just one time).
                When you register yourself, you will be given a token that you can reuse later on as a password to validate your orders (this will be printed to your command line).
                To do so, simply uncomment, and run the code below. Make sure to save the password you receive in the Secrets.java class, so you can reuse it later!
             */
            /*
            if (Secrets.password.isEmpty()) {
                restRouter.doRegistration(new UserRegistration(username), password -> {
                    System.out.println("Password is " + password + " don't forget to save it in Secrets.java so you don't accidentally register twice!");
                });
            } else {
                // You can ignore the following line until step 6, but make sure not to remove it...
                startServices(vertx, restRouter, username);
            }
            */
        }
    }
}
