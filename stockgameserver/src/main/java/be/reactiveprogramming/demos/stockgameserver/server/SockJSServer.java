package be.reactiveprogramming.demos.stockgameserver.server;

import be.reactiveprogramming.demos.stockgameserver.shared.server.ServerInfo;
import be.reactiveprogramming.demos.stockgameserver.stockmanagement.controller.StockBrokerController;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.ErrorHandler;
import io.vertx.ext.web.handler.sockjs.BridgeEventType;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

public class SockJSServer extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(WebsocketServer.class);

    private final StockBrokerController stockBrokerService;

    public SockJSServer(StockBrokerController stockBrokerService) {
        assert stockBrokerService != null;
        this.stockBrokerService = stockBrokerService;
    }

    private SockJSHandler eventBusHandler() {
        BridgeOptions options = new BridgeOptions().addOutboundPermitted(new PermittedOptions().setAddress(ServerInfo.WEBSOCKET_CHANNEL));
        return SockJSHandler.create(vertx).bridge(options, event -> {
            if (event.type() == BridgeEventType.SOCKET_CREATED) {
                logger.info("A socket was created");
            }
            event.complete(true);
        });
    }

    @Override
    public void start(Future<Void> fut) {
        Router sockJSRouter = Router.router(vertx);
        sockJSRouter.route("/eventbus/*").handler(eventBusHandler());
        sockJSRouter.route().failureHandler(errorHandler());
        vertx.createHttpServer().requestHandler(sockJSRouter::accept).listen(ServerInfo.SOCKJS);

        stockBrokerService.start(ServerInfo.WEBSOCKET_CHANNEL, vertx);
    }

    private ErrorHandler errorHandler() {
        return ErrorHandler.create(true);
    }
}
