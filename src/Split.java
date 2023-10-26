import java.util.ArrayList;
public class Split {
    public static void split(float homogeneityC, int volumeMin, float[][][] image, Cube cube,
            ArrayList<Cube> cubeList, ArrayList<Group> groupList){
        boolean test = homogeneityTest(cube,image, homogeneityC);
        if(!test){
            if(cube.getCubeVolume()>volumeMin){
                int startX= cube.getStartX();
                int endX= cube.getEndX();
                int midX= (endX-startX)/2 + startX;
                int startY= cube.getStartY();
                int endY= cube.getEndY();
                int midY= (endY-startY)/2 + startY;
                int startZ= cube.getStartZ();
                int endZ= cube.getEndZ();
                int midZ= (endZ-startZ)/2 + startZ;
                //all the sides of the cube have to be at least of size 2 to be able to be split into 8 groups
                if(endX-startX>=2 && endY-startY>=2 && endZ-startZ>=2) {
                    //1
                    Cube nouveauCube = new Cube(startX, startY, startZ, midX, midY, midZ);
                    split(homogeneityC, volumeMin, image, nouveauCube, cubeList, groupList);
                    //2
                    nouveauCube = new Cube(midX, startY, startZ, endX, midY, midZ);
                    split(homogeneityC, volumeMin, image, nouveauCube, cubeList, groupList);
                    //3
                    nouveauCube = new Cube(startX, midY, startZ, midX, endY, midZ);
                    split(homogeneityC, volumeMin, image, nouveauCube, cubeList, groupList);
                    //4
                    nouveauCube = new Cube(startX, startY, midZ, midX, midY, endZ);
                    split(homogeneityC, volumeMin, image, nouveauCube, cubeList, groupList);
                    //5
                    nouveauCube = new Cube(midX, midY, startZ, endX, endY, midZ);
                    split(homogeneityC, volumeMin, image, nouveauCube, cubeList, groupList);
                    //6
                    nouveauCube = new Cube(midX, startY, midZ, endX, midY, endZ);
                    split(homogeneityC, volumeMin, image, nouveauCube, cubeList, groupList);
                    //7
                    nouveauCube = new Cube(startX, midY, midZ, midX, endY, endZ);
                    split(homogeneityC, volumeMin, image, nouveauCube, cubeList, groupList);
                    //8
                    nouveauCube = new Cube(midX, midY, midZ, endX, endY, endZ);
                    split(homogeneityC, volumeMin, image, nouveauCube, cubeList, groupList);
                }
            }
            else{
                addToList(image, cube, cubeList, groupList);
            }
        }
        else{
            addToList(image, cube, cubeList, groupList);
        }
    }

    public static void addToList(float[][][] image, Cube cube, ArrayList<Cube> cubeList, ArrayList<Group> groupList){
        cubeList.add(cube);
        Group group= new Group();
        groupList.add(group);
        group.seekingMinMax(cube,image);
    }

    public static boolean homogeneityTest(Cube cube, float[][][] tabImage, float homogeneityC){
        float tempcolor;
        float colormin=tabImage[0][0][0];
        float colormax=tabImage[0][0][0];
        for(int i=cube.getStartX(); i<cube.getEndX(); i++){
            for(int j=cube.getStartY(); j<cube.getEndY(); j++){
                for(int k=cube.getStartZ(); k<cube.getEndZ(); k++){
                    tempcolor=tabImage[i][j][k];
                    if(tempcolor<colormin) colormin = tempcolor;
                    if(tempcolor>colormax) colormax = tempcolor;
                }
            }
        }
        return (colormax - colormin) < homogeneityC;
    }

}
