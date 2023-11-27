package Main;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Contains 2 methods to create a neighbour graph and associated functions
 */
public class Graph {
    /**
     * Creates a neighbour's graph from a list of cubes
     * @param cubelist ArrayList<Cube>, list of cubes
     * @return arcs, ArrayList<Arc>
     */
    public static ArrayList<Arc> makeNeighbourGraph(ArrayList<Cube> cubelist){
        //list of arcs representing the graph
        ArrayList<Arc> arcs = new ArrayList<>();
        //for each cube, we test if the other cubes are its neighbour
        for(int firstcube=0; firstcube< cubelist.size()-1; firstcube++){
            for(int secondcube=firstcube+1; secondcube< cubelist.size(); secondcube++){
                //if they are neighbours, we create an arc that we add to the graph
                if(cubelist.get(firstcube).isNeighbourWith(cubelist.get(secondcube))){
                    Arc arc = new Arc(firstcube, secondcube);
                    arcs.add(arc);
                }
            }
        }
        return arcs;
    }

    /**
     * Creates a neighbour's graph from a list of cubes and the image
     * This method use proximity of pixels to find neighbours
     * This method must be used in association with suppressingDuplicates in order to it to be efficient
     * @param cubelist ArrayList<Cube>, list of cubes
     * @param xsize int, size of the original image on the x dimension
     * @param ysize int, size of the original image on the y dimension
     * @param zsize int, size of the original image on the z dimension
     * @return arcs, ArrayList<Arc>
     */
    public static ArrayList<Arc> makeNeighbourGraph(ArrayList<Cube> cubelist, int xsize, int ysize, int zsize){
        int[][][] groupImage = makeGroupImage(cubelist, xsize, ysize, zsize);
        //list of arcs representing the graph
        ArrayList<Arc> arcs = new ArrayList<>();
        //for each group we check the borders pixels
        int x, y, z;
        for(int i=0; i< cubelist.size(); i++){
            //On x border
            x=cubelist.get(i).getEndX();
            if(x!=xsize){
                for(y=cubelist.get(i).getStartY(); y<cubelist.get(i).getEndY(); y++){
                    for(z=cubelist.get(i).getStartZ(); z<cubelist.get(i).getEndZ(); z++) {
                        if(i!= groupImage[x+1][y][z]){
                            Arc arc = new Arc(i, groupImage[x+1][y][z]);
                            arcs.add(arc);
                        }
                    }
                }
            }

            //On y border
            y=cubelist.get(i).getEndY();
            if(y!=ysize){
                for(x=cubelist.get(i).getStartX(); x<cubelist.get(i).getEndX(); x++){
                    for(z=cubelist.get(i).getStartZ(); z<cubelist.get(i).getEndZ(); z++) {
                        if(i!= groupImage[x][y+1][z]){
                            Arc arc = new Arc(i, groupImage[x][y+1][z]);
                            arcs.add(arc);
                        }
                    }
                }
            }

            //On z border
            z=cubelist.get(i).getEndZ();
            if(z!=zsize){
                for(y=cubelist.get(i).getStartY(); y<cubelist.get(i).getEndY(); y++){
                    for(x=cubelist.get(i).getStartX(); x<cubelist.get(i).getEndX(); x++) {
                        if(i!= groupImage[x][y][z+1]){
                            Arc arc = new Arc(i, groupImage[x][y][z+1]);
                            arcs.add(arc);
                        }
                    }
                }
            }
        }
        return arcs;
    }

    /**
     * Creates an 3Dtab in wich every pixel value is its group number
     * @param cubelist ArrayList<Cube>, list of cubes
     * @param xsize int, size of the original image on the x dimension
     * @param ysize int, size of the original image on the y dimension
     * @param zsize int, size of the original image on the z dimension
     * @return int[][][], new GroupTab
     */
    private static int[][][] makeGroupImage(ArrayList<Cube> cubelist, int xsize, int ysize, int zsize){
        int[][][] groupImage = new int[xsize][ysize][zsize];
        for(int i=0;i<cubelist.size();i++){
            for(int x=cubelist.get(i).getStartX();x<cubelist.get(i).getEndX();x++){
                for(int y=cubelist.get(i).getStartY();y<cubelist.get(i).getEndY();y++){
                    for(int z=cubelist.get(i).getStartZ();z<cubelist.get(i).getEndZ();z++){
                        groupImage[x][y][z]=i;
                    }
                }
            }
        }
        return groupImage;
    }

    /**
     * Suppress all duplicates arcs from the list
     * only the return list is modified
     * @param arcs ArrayList<Arc>, list of arcs
     * @return arcs, ArrayList<Arc>, new modified list
     */
    public static ArrayList<Arc> suppressingDuplicates(ArrayList<Arc> arcs){
        return new ArrayList<>(new HashSet<>(arcs));
    }

}
