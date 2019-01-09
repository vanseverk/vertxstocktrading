package be.reactiveprogramming.demos.stockgameserver;

import be.reactiveprogramming.demos.stockgameserver.client.BuyDecisionService;
import be.reactiveprogramming.demos.stockgameserver.client.RestRouter;
import be.reactiveprogramming.demos.stockgameserver.client.Secrets;
import be.reactiveprogramming.demos.stockgameserver.client.WebsocketRouter;
import be.reactiveprogramming.demos.stockgameserver.client.codec.GameStateCodec;
import be.reactiveprogramming.demos.stockgameserver.shared.registration.UserRegistration;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.GameState;
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
            new WebsocketRouter(vertx.eventBus(), vertx.createHttpClient());
            new BuyDecisionService(vertx.eventBus(), restRouter, username);
        }

        void start() {

            final String username = "Kristof";

            Vertx vertx = Vertx.vertx();

            vertx.eventBus().registerDefaultCodec(GameState.class, new GameStateCodec());

            RestRouter restRouter = new RestRouter(vertx.createHttpClient(), username);

            if (Secrets.password.isEmpty()) {
                restRouter.doRegistration(new UserRegistration(username), password -> {
                    System.out.println("Password is " + password + " don't forget to save it!");
                    startServices(vertx, restRouter, username);
                });
            } else {
                restRouter.setPassword(Secrets.password);
                startServices(vertx, restRouter, username);
            }
        }
    }
}
