public class Cube {
    private Coordinates start;
    private Coordinates end;

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
    public Boolean IsNeighbourWith(Cube secondcube){
        Boolean test = Boolean.FALSE;

        //test to see if the end of se second cube enters in contact with the three faces entering in contact with the start of the first one
        if(start.getX()==secondcube.getEnd().getX()-1 && (start.getY()==secondcube.getEnd().getY() || start.getZ()==secondcube.getEnd().getZ()) )
            test=Boolean.TRUE;
        else if (start.getY()==secondcube.getEnd().getY()-1 && (start.getX()==secondcube.getEnd().getX() || start.getZ()==secondcube.getEnd().getZ()))
            test=Boolean.TRUE;
        else if(start.getZ()==secondcube.getEnd().getZ()-1 && (start.getX()==secondcube.getEnd().getX() || start.getY()==secondcube.getEnd().getY()))
            test=Boolean.TRUE;
        //test to check if the start of the second cube enters in contact with the three faces entering in contact with the end of the first one
        else if(end.getX()==secondcube.getStart().getX()+1 && (end.getY()==secondcube.getStart().getY() || end.getZ()==secondcube.getStart().getZ()))
            test=Boolean.TRUE;
        else if(end.getY()==secondcube.getStart().getY()+1 && (end.getX()==secondcube.getStart().getX() || end.getZ()==secondcube.getStart().getZ()))
            test=Boolean.TRUE;
        else if(end.getZ()==secondcube.getStart().getZ()+1 && (end.getX()==secondcube.getStart().getX() || end.getY()==secondcube.getStart().getY()))
            test=Boolean.TRUE;

        return test;
    }
}
