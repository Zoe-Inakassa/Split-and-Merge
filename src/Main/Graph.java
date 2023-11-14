package Main;

import java.util.ArrayList;

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
     * @param image float[][][], a 3Dtab containing pixel colors
     * @param cubelist ArrayList<Cube>, list of cubes
     * @return arcs, ArrayList<Arc>
     */
    public static ArrayList<Arc> makeNeighbourGraph(float[][][] image, ArrayList<Cube> cubelist){
        int[][][] groupImage = makeGroupImage(image,cubelist);
        //list of arcs representing the graph
        ArrayList<Arc> arcs = new ArrayList<>();
        //for each group we check the borders pixels
        int x=0, y=0, z=0;
        for(int i=0; i< cubelist.size(); i++){
            //On x border
            x=cubelist.get(i).getEndX();
            if(x!=image.length){
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
            if(y!=image[0].length){
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
            if(z!=image[0][0].length){
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
     * @param image float[][][], a 3Dtab containing pixel colors
     * @param cubelist ArrayList<Cube>, list of cubes
     * @return int[][][], new GroupTab
     */
    public static int[][][] makeGroupImage(float[][][] image, ArrayList<Cube> cubelist){
        int xsize = image.length;
        int ysize = image[0].length;
        int zsize = image[0][0].length;
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
}
