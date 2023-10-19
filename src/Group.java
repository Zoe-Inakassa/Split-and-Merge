public class Group {
    private int groupnumber;
    private float colormin;
    private float colormax;
    private static int nbofgroup = 0;

    //Constructors
    public Group() {
        groupnumber = nbofgroup;
        nbofgroup++;
        colormin = Integer.MAX_VALUE;
        colormax = 0;
    }

    public Group(float colormin, float colormax) {
        groupnumber = nbofgroup;
        nbofgroup++;
        this.colormin = colormin;
        this.colormax = colormax;
    }

    //Getters and setters
    public int getGroupnumber() {
        return groupnumber;
    }

    public float getColormin() {
        return colormin;
    }

    public void setColormin(float colormin) {
        this.colormin = colormin;
    }

    public float getColormax() {
        return colormax;
    }

    public void setColormax(float colormax) {
        this.colormax = colormax;
    }

    public static int getNbofGroup() {
        return nbofgroup;
    }

    public static void lessNbofGroup() {
        nbofgroup--;
    }

    //Seeking colormin and colormax within a cube
    public void seekingMinMax(Cube cube, float[][][] tabimage){
        float colorintab;
        for(int i=cube.getStart().getX(); i<cube.getEnd().getX(); i++){
            for(int j=cube.getStart().getY(); j<cube.getEnd().getY(); j++){
                for(int k=cube.getStart().getZ(); k<cube.getEnd().getZ(); k++){
                    colorintab=tabimage[i][j][k];
                    if(colorintab<colormin) colormin = colorintab;
                    if(colorintab>colormax) colormax = colorintab;
                }
            }
        }
    }

}