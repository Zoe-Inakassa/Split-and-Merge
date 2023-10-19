public class Cube {
    final private Coordinates start;
    final private Coordinates end;

    //Constructor
    public Cube(int x0, int y0, int z0, int x1, int y1, int z1){
        start = new Coordinates(x0, y0, z0);
        end = new Coordinates(x1, y1, z1);
    }

    //Getters
    public Coordinates getStart() {
        return start;
    }

    public Coordinates getEnd() {
        return end;
    }

    //Test if another cube is its neighbour
    public boolean IsNeighbourWith(Cube secondcube){
        //variables representing how many sides of the two cubes are in limit-contact
        int nbofattachedsides=0;
        //if a cube starts after the other one ends in any dimension, they are not neighbours
        if(start.getX()>secondcube.getEnd().getX() || secondcube.getStart().getX()>end.getX())
            return false;
        if(start.getY()>secondcube.getEnd().getY() || secondcube.getStart().getY()>end.getY())
            return false;
        if(start.getZ()>secondcube.getEnd().getZ() || secondcube.getStart().getZ()>end.getZ())
            return false;
        //if more than one side are in limit-contact, only the edges touch and they are not neighbours
        if(start.getX()==secondcube.getEnd().getX() || end.getX()==secondcube.getStart().getX()) {
            nbofattachedsides++;
        }
        if(start.getY()==secondcube.getEnd().getY() || end.getY()==secondcube.getStart().getY()) {
            nbofattachedsides++;
            if(nbofattachedsides>1) return false;
        }
        if(start.getZ()==secondcube.getEnd().getZ() || end.getZ()==secondcube.getStart().getZ()) {
            nbofattachedsides++;
            if(nbofattachedsides>1) return false;
        }
        //otherwise they are neighbours
        return true;
    }
}
