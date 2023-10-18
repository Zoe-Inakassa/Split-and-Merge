import java.util.ArrayList;
public class Split {
    public static void Splitee(float homogeneityC, int volumeMin, float[][][] image, Cube cube, ArrayList<Cube>
            cubelist, ArrayList<Group> grouplist){
        //do something
    }

    public static Boolean HomogeneityTest(Cube cube, float[][][] tabImage, int homogeneityC){
        float colorintab;
        float colormin=tabImage[0][0][0];
        float colormax=tabImage[0][0][0];
        for(int i=cube.getStart().getX(); i<cube.getEnd().getX(); i++){
            for(int j=cube.getStart().getY(); j<cube.getEnd().getY(); j++){
                for(int k=cube.getStart().getZ(); k<cube.getEnd().getZ(); k++){
                    colorintab=tabImage[i][j][k];
                    if(colorintab<colormin) colormin = colorintab;
                    if(colorintab>colormax) colormax = colorintab;
                }
            }
        }
        if((colormax-colormin)<homogeneityC) return Boolean.TRUE;
        else return Boolean.FALSE;
    }
}
