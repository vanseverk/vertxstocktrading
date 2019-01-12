package be.reactiveprogramming.demos.stockgameserver.shared.server;

public class ServerInfo {

    // TODO 1 change the following line to the correct IP address instead of "localhost", it should be the IP address of the stock broker server
    // (We will give you the required IP address)
    public static final String SERVER = "localhost";

    // REST API
    public static final int REST = 81;
    public static final String REGISTRATION_URL = "/register";
    public static final String STOCK_URL = "/stock";

    // Websocket
    public static final int WEBSOCKET = 8090;
    public static final int WEBSOCKET2 = 8091;
    public static final String WEBSOCKET_CHANNEL = "gameupdates";

}
