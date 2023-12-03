package net.ldoin.jmines.util.vec;

import lombok.EqualsAndHashCode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.NumberConversions;

@EqualsAndHashCode
public class Vec3i {

    public static final Vec3i ZERO = new Vec3i();

    public final int x;
    public final int y;
    public final int z;

    public Vec3i() {

        this(0, 0, 0);

    }

    public Vec3i(Vec3f vec) {

        x = (int) vec.x;
        y = (int) vec.y;
        z = (int) vec.z;

    }

    public Vec3i(Location loc) {

        x = loc.getBlockX();
        y = loc.getBlockY();
        z = loc.getBlockZ();

    }

    public Vec3i(Block bloc) {

        x = bloc.getX();
        y = bloc.getY();
        z = bloc.getZ();

    }

    public Vec3i(int x, int y, int z) {

        this.x = x;
        this.y = y;
        this.z = z;

    }

    public Vec3i setX(int x) {

        return new Vec3i(x, y, z);

    }

    public Vec3i setY(int y) {

        return new Vec3i(x, y, z);

    }

    public Vec3i setZ(int z) {

        return new Vec3i(x, y, z);

    }

    public Vec3i add(int val) {

        return new Vec3i(x + val, y + val, z + val);

    }

    public Vec3i add(Vec3i vec) {

        return new Vec3i(x + vec.x, y + vec.y, z + vec.z);

    }

    public Vec3i add(int x, int y, int z) {

        return new Vec3i(x + x, y + y, z + z);

    }

    public Vec3i subtract(int val) {

        return new Vec3i(x - val, y - val, z - val);

    }

    public Vec3i subtract(Vec3i vec) {

        return new Vec3i(x - vec.x, y - vec.y, z - vec.z);

    }

    public Vec3i subtract(int x, int y, int z) {

        return new Vec3i(this.x - x, this.y - y, this.z - z);

    }

    public Vec3i multiply(int val) {

        return new Vec3i(x * val, y * val, z * val);

    }

    public Vec3i multiply(Vec3i vec) {

        return new Vec3i(x * vec.x, y * vec.y, z * vec.z);

    }

    public Vec3i multiply(int x, int y, int z) {

        return new Vec3i(x * x, y * y, z * z);

    }

    public Vec3f divide(int val) {

        return new Vec3f((float) x / (float) val, (float) y / (float) val, (float) z / (float) val);

    }

    public Vec3f divide(float val) {

        return new Vec3f((float) x / val, (float) y / val, (float) z / val);

    }

    public Vec3f divide(Vec3i vec) {

        return new Vec3f((float) x / (float) vec.x, (float) y / (float) vec.y, (float) z / (float) vec.z);

    }

    public Vec3f divide(Vec3f vec) {

        return new Vec3f((float) x / vec.x, (float) y / vec.y, (float) z / vec.z);

    }

    public Vec3f divide(int x, int y, int z) {

        return new Vec3f((float) this.x / (float) x, (float) this.y / (float) y, (float) this.z / (float) z);

    }

    public Vec3i invert() {

        return new Vec3i(-x, -y, -z);

    }

    public Vec3f normalize() {

        return divide(length());

    }

    public Vec3i abs() {

        return new Vec3i(Math.abs(x), Math.abs(y), Math.abs(z));

    }

    public Vec3i clone() {

        return new Vec3i(x, y, z);

    }

    public float length() {

        return (float) Math.sqrt(x * x + y * y + z * z);

    }

    public float lengthSq() {

        return (float) (x * x + y * y + z * z);

    }

    public float dot(Vec3i v1) {

        return (float) (x * v1.x + y * v1.y + z * v1.z);

    }

    public Vec3i cross(Vec3i vec) {

        return new Vec3i(y * vec.z - z * vec.y, vec.x * z - vec.z * x, x * vec.y - y * vec.x);

    }

    public float distance(Vec3f vec) {

        return (float) Math.sqrt(NumberConversions.square(vec.x - (float) x) + NumberConversions.square(vec.z - (float) z) + NumberConversions.square(vec.z - (float) z));

    }

    public float distanceSq(Vec3f vec) {

        return (float) (NumberConversions.square(vec.x - (float) x) + NumberConversions.square(vec.z - (float) z) + NumberConversions.square(vec.z - (float) z));

    }

    public Location toLocation(World world) {

        return new Location(world, x, y, z);

    }

    public String toString() {

        return x + " " + y + " " + z;

    }
}