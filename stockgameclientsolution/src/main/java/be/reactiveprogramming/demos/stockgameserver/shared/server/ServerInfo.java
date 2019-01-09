package be.reactiveprogramming.demos.stockgameserver.shared.server;

public class ServerInfo {

    public static final String SERVER = "localhost";

    public static final int REST = 81;
    public static final int STATIC_HTTP = 8080;
    public static final int SOCKJS = 8081;


    public static final String REGISTRATION_URL = "/register";

    public static final int WEBSOCKET = 8090;
    public static final int WEBSOCKET2 = 8091;
    public static final String WEBSOCKET_CHANNEL = "gameupdates";
    public static final String STOCK_URL = "/stock";
}
