package net.ldoin.jmines.util.vec;

import lombok.EqualsAndHashCode;
import org.bukkit.Location;
import org.bukkit.block.Block;

@EqualsAndHashCode
@SuppressWarnings({"unused"})
public class Vec3f {

    public static final Vec3f ZERO = new Vec3f();

    public final float x;
    public final float y;
    public final float z;

    public Vec3f() {

        this(0, 0, 0);

    }

    public Vec3f(Vec3f vec) {

        x = vec.x;
        y = vec.y;
        z = vec.z;

    }

    public Vec3f(Vec3i vec) {

        x = vec.x;
        y = vec.y;
        z = vec.z;

    }

    public Vec3f(Location loc) {

        x = loc.getBlockX();
        y = loc.getBlockY();
        z = loc.getBlockZ();

    }

    public Vec3f(Block bloc) {

        x = bloc.getX();
        y = bloc.getY();
        z = bloc.getZ();

    }

    public Vec3f(float x, float y, float z) {

        this.x = x;
        this.y = y;
        this.z = z;

    }

    public Vec3f setX(float x) {

        return new Vec3f(x, y, z);

    }

    public Vec3f setY(float y) {

        return new Vec3f(x, y, z);

    }

    public Vec3f setZ(float z) {

        return new Vec3f(x, y, z);

    }

    public Vec3f add(float val) {

        return new Vec3f(x + val, y + val, z + val);

    }

    public Vec3f add(Vec3f vec) {

        return new Vec3f(x + vec.x, y + vec.y, z + vec.z);

    }

    public Vec3f add(float x, float y, float z) {

        return new Vec3f(x + x, y + y, z + z);

    }

    public Vec3f subtract(float val) {

        return new Vec3f(x - val, y - val, z - val);

    }

    public Vec3f subtract(Vec3f vec) {

        return new Vec3f(x - vec.x, y - vec.y, z - vec.z);

    }

    public Vec3f subtract(float x, float y, float z) {

        return new Vec3f(this.x - x, this.y - y, this.z - z);

    }

    public Vec3f multiply(float val) {

        return new Vec3f(x * val, y * val, z * val);

    }

    public Vec3f multiply(Vec3f vec) {

        return new Vec3f(x * vec.x, y * vec.y, z * vec.z);

    }

    public Vec3f multiply(float x, float y, float z) {

        return new Vec3f(x * x, y * y, z * z);

    }

    public Vec3f divide(float val) {

        return new Vec3f(x / val, y / val, z / val);

    }

    public Vec3f divide(Vec3f vec) {

        return new Vec3f(x / vec.x, y / vec.y, z / vec.z);

    }

    public Vec3f divide(float x, float y, float z) {

        return new Vec3f(this.x / x, this.y / y, this.z / z);

    }

    public Vec3f invert() {

        return new Vec3f(-x, -y, -z);

    }

    public Vec3f normalize() {

        return divide(length());

    }

    public Vec3f abs() {

        return new Vec3f(Math.abs(x), Math.abs(y), Math.abs(z));

    }

    public Vec3f clone() {

        return new Vec3f(x, y, z);

    }

    public float length() {

        return (float) Math.sqrt(x * x + y * y + z * z);

    }

    public String toString() {

        return x + " " + y + " " + z;

    }
}