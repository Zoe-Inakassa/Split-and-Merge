/**
 * Class Cube
 */
public class Cube {
    final private Coordinates start;
    final private Coordinates end;

    /**
     * Constructor of a Cube object
     * @param x0 int, coordinate x of starting point of the cube
     * @param y0 int, coordinate y of starting point of the cube
     * @param z0 int, coordinate z of starting point of the cube
     * @param x1 int, coordinate x of ending point of the cube
     * @param y1 int, coordinate y of ending point of the cube
     * @param z1 int, coordinate z of ending point of the cube
     */
    public Cube(int x0, int y0, int z0, int x1, int y1, int z1){
        start = new Coordinates(x0, y0, z0);
        end = new Coordinates(x1, y1, z1);
    }

    /**
     * Getter on the x coordinate of starting point
     * @return x0, int
     */
    public int getStartX() {
        return start.getX();
    }
    /**
     * Getter on the y coordinate of starting point
     * @return y0, int
     */
    public int getStartY() {
        return start.getY();
    }
    /**
     * Getter on the z coordinate of starting point
     * @return z0, int
     */
    public int getStartZ() {
        return start.getZ();
    }

    /**
     * Getter on the x coordinate of ending point
     * @return x1, int
     */
    public int getEndX() {
        return end.getX();
    }
    /**
     * Getter on the y coordinate of ending point
     * @return y1, int
     */
    public int getEndY() {
        return end.getY();
    }
    /**
     * Getter on the z coordinate of ending point
     * @return z1, int
     */
    public int getEndZ() {
        return end.getZ();
    }

    /**
     * Test if the cube calling the function is neighbour with another cube
     * @param secondcube Cube, the possible neighbour of the cube calling the function
     * @return boolean, true if neighbours, false if not neighbours
     */
    public boolean isNeighbourWith(Cube secondcube){
        //variables representing how many sides of the two cubes are in limit-contact
        int nbofattachedsides=0;
        //if a cube starts after the other one ends in any dimension, they are not neighbours
        if(start.getX()>secondcube.getEndX() || secondcube.getStartX()>end.getX())
            return false;
        if(start.getY()>secondcube.getEndY() || secondcube.getStartY()>end.getY())
            return false;
        if(start.getZ()>secondcube.getEndZ() || secondcube.getStartZ()>end.getZ())
            return false;
        //if more than one side are in limit-contact, only the edges touch and they are not neighbours
        if(start.getX()==secondcube.getEndX() || end.getX()==secondcube.getStartX()) {
            nbofattachedsides++;
        }
        if(start.getY()==secondcube.getEndY() || end.getY()==secondcube.getStartY()) {
            nbofattachedsides++;
            if(nbofattachedsides>1) return false;
        }
        if(start.getZ()==secondcube.getEndZ() || end.getZ()==secondcube.getStartZ()) {
            nbofattachedsides++;
            if(nbofattachedsides>1) return false;
        }
        //otherwise they are neighbours
        return true;
    }

    /**
     * Function to calculate the volume of a cube
     * @return volume, int
     */
    public int getCubeVolume() {
        int volume =(end.getX()-start.getX());
        volume = volume * (end.getY()-start.getY());
        volume = volume * (end.getZ()-start.getZ());
        return volume;
    }
}
