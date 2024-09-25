package dev.huls.elysium_crates.methods;

import dev.huls.elysium_crates.enums.BoxTypeEnum;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class BoxMethod implements ConfigurationSerializable {
    private final Location location;
    private final BoxTypeEnum type;

    public BoxMethod(Location location, BoxTypeEnum type) {
        this.location = location;
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public Vector getPosition() {
        return location.toVector();
    }

    public BoxTypeEnum getType() {
        return type;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("world", location.getWorld().getName());
        map.put("x", location.getX());
        map.put("y", location.getY());
        map.put("z", location.getZ());
        map.put("type", type.name());
        return map;
    }

    public static BoxMethod deserialize(Map<String, Object> map) {
        String worldName = (String) map.get("world");
        double x = (double) map.get("x");
        double y = (double) map.get("y");
        double z = (double) map.get("z");
        BoxTypeEnum type = BoxTypeEnum.valueOf((String) map.get("type"));
        Location location = new Location(Bukkit.getWorld(worldName), x, y, z);
        return new BoxMethod(location, type);
    }
}