package be.reactiveprogramming.demos.stockgameserver.server;

import be.reactiveprogramming.demos.stockgameserver.shared.server.ServerInfo;
import be.reactiveprogramming.demos.stockgameserver.stockmanagement.controller.StockBrokerController;
import be.reactiveprogramming.demos.stockgameserver.stockmanagement.controller.StockBrokerController;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.handler.ErrorHandler;

/**
 * Used to serve the websocket API
 *
 * Based on sample: http://vertx.io/blog/real-time-bidding-with-websockets-and-vert-x/
 */
public class WebsocketServer extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(WebsocketServer.class);

	private final StockBrokerController stockBrokerService;

	public WebsocketServer(StockBrokerController stockBrokerService) {
		assert stockBrokerService != null;
		this.stockBrokerService = stockBrokerService;
	}

	@Override
	public void start(Future<Void> fut) {
		alternateServers(true, ServerInfo.WEBSOCKET, ServerInfo.WEBSOCKET2);
	}

	private void alternateServers(boolean usePortA, int portA, int portB) {
		final HttpServer server = vertx.createHttpServer().websocketHandler(websocket -> {
			System.out.print("Websocket client connected!");
			stockBrokerService.addWebsocketClient(websocket);
		}).listen(usePortA ? portA : portB);

		vertx.setTimer(10000, new Handler<Long>() {
			@Override
			public void handle(Long aLong) {
				alternateServers(!usePortA, portA, portB);
				System.out.println("Alternating servers!");
				server.close();
			}
		});
	}

	private ErrorHandler errorHandler() {
		return ErrorHandler.create(true);
	}
}
