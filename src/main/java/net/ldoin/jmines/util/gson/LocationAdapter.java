package net.ldoin.jmines.util.gson;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.lang.reflect.Type;

public class LocationAdapter implements JsonSerializer<Location>, JsonDeserializer<Location> {

    public JsonElement serialize(Location location, Type type, JsonSerializationContext jsonSerializationContext) {

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("world", location.getWorld().getName());

        jsonObject.addProperty("x", location.getX());
        jsonObject.addProperty("y", location.getY());
        jsonObject.addProperty("z", location.getZ());

        jsonObject.addProperty("yaw", location.getYaw());
        jsonObject.addProperty("pitch", location.getPitch());

        return jsonObject;

    }

    public Location deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String worldName = jsonObject.get("world").getAsString();

        double x = jsonObject.get("x").getAsDouble();
        double y = jsonObject.get("y").getAsDouble();
        double z = jsonObject.get("z").getAsDouble();

        float yaw = jsonObject.get("yaw").getAsFloat();
        float pitch = jsonObject.get("pitch").getAsFloat();

        return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);

    }
}