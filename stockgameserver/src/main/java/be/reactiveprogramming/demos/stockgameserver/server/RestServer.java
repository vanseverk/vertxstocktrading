package be.reactiveprogramming.demos.stockgameserver.server;

import be.reactiveprogramming.demos.stockgameserver.shared.registration.UserRegistration;
import be.reactiveprogramming.demos.stockgameserver.shared.server.ServerInfo;
import be.reactiveprogramming.demos.stockgameserver.shared.stocks.Order;
import be.reactiveprogramming.demos.stockgameserver.stockmanagement.controller.StockBrokerController;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Used to serve the REST API
 * http://vertx.io/blog/some-rest-with-vert-x/
 */
public class RestServer extends AbstractVerticle {

	private StockBrokerController stockBrokerController;

	public RestServer(final StockBrokerController stockBrokerController) {
		assert stockBrokerController != null;

		this.stockBrokerController = stockBrokerController;
	}

	@Override
	public void start(Future<Void> fut) {
		// Create a router object.
		Router router = Router.router(vertx);

		// Most important line of the whole REST service, it seems. Required to be able to use the "getBodyAsString" method, instead of requiring a callback...
		// http://stackoverflow.com/questions/30858917/unable-to-access-request-body-using-getbodyasjson-in-vert-x-3-0-0
		router.route().handler(BodyHandler.create());

		router.post("/register").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();

			try {
				final UserRegistration registration = Json.decodeValue(routingContext.getBodyAsString(), UserRegistration.class);
				System.out.println("Received registration " + registration.getUsername());
				String password = stockBrokerController.doRegistration(registration);
				response.putHeader("content-type", "application/json; charset=utf-8").end(password);
			} catch (Exception ex) {
				response.setStatusCode(400).putHeader("content-type", "application/json; charset=utf-8").end(ex.getMessage());
			}
		});

		router.post("/stock").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();

			try {
				final Order order = Json.decodeValue(routingContext.getBodyAsString(), Order.class);

				System.out.println("Received order " + order);

				stockBrokerController.doOrder(order);

				response.putHeader("content-type", "application/json; charset=utf-8").end("ok");
			} catch (Exception ex) {
				response.setStatusCode(400).putHeader("content-type", "application/json; charset=utf-8").end(ex.getMessage());
			}
		});

		router.get("/hello").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			response.putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200).end(Json.encodePrettily("Hello from the other side!"));
		});

		// Create the HTTP server and pass the "accept" method to the request handler.
		vertx.createHttpServer()
		.requestHandler(router::accept)
				.listen(ServerInfo.REST, result -> {
			if(result.succeeded()) {
				fut.complete();
			} else {
				fut.fail(result.cause());
			}
		}
		);
	}
}
