package be.reactiveprogramming.demos.stockgameserver.server;

import be.reactiveprogramming.demos.stockgameserver.shared.server.ServerInfo;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * Used to serve the static website parts
 */
public class StaticWebsiteServer extends AbstractVerticle {

	@Override
	public void start(Future<Void> fut) {
		Router router = Router.router(vertx);

		router.route().handler(StaticHandler.create());

		vertx.createHttpServer()
			.requestHandler(router::accept)
                .listen(ServerInfo.STATIC_HTTP, result -> {
                    if(result.succeeded()) {
					fut.complete();
				} else {
					fut.fail(result.cause());
				}
			});
	}
}
