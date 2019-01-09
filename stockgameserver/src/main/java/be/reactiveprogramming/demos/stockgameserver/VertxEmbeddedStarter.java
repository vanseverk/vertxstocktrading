package be.reactiveprogramming.demos.stockgameserver;

import be.reactiveprogramming.demos.stockgameserver.server.RestServer;
import be.reactiveprogramming.demos.stockgameserver.server.SockJSServer;
import be.reactiveprogramming.demos.stockgameserver.server.StaticWebsiteServer;
import be.reactiveprogramming.demos.stockgameserver.server.WebsocketServer;
import be.reactiveprogramming.demos.stockgameserver.stockmanagement.controller.StockBrokerController;
import io.vertx.core.Vertx;

public class VertxEmbeddedStarter {

	private static final StockBrokerController stockBrokerController = new StockBrokerController();

	public static void main(String[] args) {
		    Vertx.vertx().deployVerticle(new StaticWebsiteServer());
		    Vertx.vertx().deployVerticle(new SockJSServer(stockBrokerController));
        Vertx.vertx().deployVerticle(new RestServer(stockBrokerController));
        Vertx.vertx().deployVerticle(new WebsocketServer(stockBrokerController));
	}

}
