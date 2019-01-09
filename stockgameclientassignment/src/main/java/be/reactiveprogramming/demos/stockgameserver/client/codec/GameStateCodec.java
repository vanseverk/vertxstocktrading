package be.reactiveprogramming.demos.stockgameserver.client.codec;

import be.reactiveprogramming.demos.stockgameserver.shared.stocks.GameState;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.Json;

public class GameStateCodec implements MessageCodec<GameState, Object> {
    @Override
    public void encodeToWire(Buffer buffer, GameState t) {
        buffer.appendString(Json.encode(t));
    }

    @Override
    public Object decodeFromWire(int i, Buffer buffer) {
        return Json.decodeValue(buffer.toString(), GameState.class);
    }

    @Override
    public Object transform(GameState t) {
        return t;
    }

    @Override
    public String name() {
        return "gamestatecodec";
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
