public class Cube {
    final private Coordinates start;
    final private Coordinates end;

    //Constructor
    public Cube(int x0, int y0, int z0, int x1, int y1, int z1){
        start = new Coordinates(x0, y0, z0);
        end = new Coordinates(x1, y1, z1);
    }

    //Getters
    public int getStartX() {
        return start.getX();
    }
    public int getStartY() {
        return start.getY();
    }
    public int getStartZ() {
        return start.getZ();
    }


    public int getEndX() {
        return end.getX();
    }
    public int getEndY() {
        return end.getY();
    }
    public int getEndZ() {
        return end.getZ();
    }

    //Test if another cube is its neighbour
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

    public int getCubeVolume() {
        int volume =(end.getX()-start.getX());
        volume = volume * (end.getY()-start.getY());
        volume = volume * (end.getZ()-start.getZ());
        return volume;
    }
}
