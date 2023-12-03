package net.ldoin.jmines.util;

import net.ldoin.jmines.util.vec.Vec3f;
import net.ldoin.jmines.util.vec.Vec3i;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;

@SuppressWarnings({"unused"})
public class Cuboid implements Iterable<Vec3i> {

    protected Vec3i min;
    protected Vec3i max;

    public Cuboid() {

        min = new Vec3i();
        max = new Vec3i();

    }

    public Cuboid(Vec3i p1, Vec3i p2) {

        setBounds(p1, p2);

    }

    public void setBounds(Vec3i p1, Vec3i p2) {

        min = new Vec3i(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y), Math.min(p1.z, p2.z));
        max = new Vec3i(Math.max(p1.x, p2.x), Math.max(p1.y, p2.y), Math.max(p1.z, p2.z));

    }

    public Vec3i getMin() {

        return min;

    }

    public Vec3i getMax() {

        return max;

    }

    public Vec3f getCenter() {

        return new Vec3f(min.add(max)).divide(2.0F);

    }

    public int getWidth() {

        return max.x - min.x + 1;

    }

    public int getHeight() {

        return max.y - min.y + 1;

    }

    public int getLength() {

        return max.z - min.z + 1;

    }

    public int getArea() {

        return (max.x - min.x + 1) * (max.y - min.y + 1) * (max.z - min.z + 1);

    }

    public boolean contains(Vec3i vec) {

        return vec.x >= min.x && vec.x <= max.x && vec.y >= min.y && vec.y <= max.y && vec.z >= min.z && vec.z <= max.z;

    }

    public boolean contains(Location loc) {

        return loc.getX() >= (double) min.x && loc.getX() < (double) (max.x + 1) && loc.getY() >= (double) min.y && loc.getY() < (double) (max.y + 1) && loc.getZ() >= (double) min.z && loc.getZ() < (double) (max.z + 1);

    }

    public boolean contains(Block block) {

        return block.getX() >= min.x && block.getX() <= max.x && block.getY() >= min.y && block.getY() <= max.y && block.getZ() >= min.z && block.getZ() <= max.z;

    }

    public Vec3i size() {

        return max.add(min.invert()).add(1, 1, 1);

    }

    public Cuboid clone() {

        return new Cuboid(min, max);

    }

    public Iterator<Vec3i> iterator() {

        return new Iterator<Vec3i>() {

            private final Vec3i min = getMin();
            private final Vec3i max = getMax();

            private int nextX;
            private int nextY;
            private int nextZ;

            {

                nextX = min.x;
                nextY = min.y;
                nextZ = min.z;

            }

            public boolean hasNext() {

                return nextX != Integer.MIN_VALUE;

            }

            public Vec3i next() {

                if (!hasNext())
                    throw new NoSuchElementException();
                else {

                    Vec3i answer = new Vec3i(nextX, nextY, nextZ);

                    if (nextX++ > max.x) {

                        nextX = min.x;

                        if (nextY++ > max.y) {

                            nextY = min.y;

                            if (nextZ++ > max.z)
                                nextX = Integer.MIN_VALUE;

                        }
                    }

                    return answer;

                }
            }
        };
    }

    public Spliterator<Vec3i> spliterator() {

        return Spliterators.spliterator(iterator(), getArea(), 0);

    }
}