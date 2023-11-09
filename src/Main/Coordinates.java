package Main;

/**
 * Class Coordinates
 */
public class Coordinates {
    private int x;
    private int y;
    private int z;

    /**
     * Constructor
     * @param x, int
     * @param y, int
     * @param z, int
     */
    public Coordinates(int x, int y, int z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    /**
     * Getter on x
     * @return x, int
     */
    public int getX() {
        return x;
    }

    /**
     * Setter on x
     * @param x int, coordinate x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Getter on y
     * @return y, int
     */
    public int getY() {
        return y;
    }

    /**
     * Setter on y
     * @param y int, coordinate y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Getter on z
     * @return z, int
     */
    public int getZ() {
        return z;
    }

    /**
     * Setter on z
     * @param z int, coordinate z
     */
    public void setZ(int z) {
        this.z = z;
    }
}
