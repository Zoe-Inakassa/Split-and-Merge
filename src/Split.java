import java.util.ArrayList;
public class Split {
    public static void Splitee(float homogeneityC, int volumeMin, float[][][] image, Cube cube, ArrayList<Cube>
            cubelist, ArrayList<Group> grouplist){
        //do something
    }

    public static boolean HomogeneityTest(Cube cube, float[][][] tabImage, float homogeneityC){
        float tempcolor;
        float colormin=tabImage[0][0][0];
        float colormax=tabImage[0][0][0];
        for(int i=cube.getStart().getX(); i<cube.getEnd().getX(); i++){
            for(int j=cube.getStart().getY(); j<cube.getEnd().getY(); j++){
                for(int k=cube.getStart().getZ(); k<cube.getEnd().getZ(); k++){
                    tempcolor=tabImage[i][j][k];
                    if(tempcolor<colormin) colormin = tempcolor;
                    if(tempcolor>colormax) colormax = tempcolor;
                }
            }
        }
        return (colormax - colormin) < homogeneityC;
    }

    public static Boolean HomogeneityTest(Group group1,Group group2, float homogeneityC){
        float maxcolor = Math.max(group1.getColormax(),group2.getColormax());
        float mincolor = Math.min(group1.getColormin(),group2.getColormin());
        return (maxcolor - mincolor) < homogeneityC;
    }

}
