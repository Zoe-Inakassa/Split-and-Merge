package Main;

public class Group {
    private float colormin;
    private float colormax;
    private static int nbofgroup = 0;

    /**
     * Constructor by default put colormin at a high value and color max at 0
     */
    public Group() {
        nbofgroup++;
        colormin = Integer.MAX_VALUE;
        colormax = 0;
    }

    /**
     * Constructor
     * @param colormin float, value of colormin
     * @param colormax float, value of colormax
     */
    public Group(float colormin, float colormax) {
        nbofgroup++;
        this.colormin = colormin;
        this.colormax = colormax;
    }

    //Getters and setters

    /**
     * Getter of colormin
     * @return float, value of colormin
     */
    public float getColormin() {
        return colormin;
    }

    /**
     * Setter of colormin
     * @param colormin float, new value of colormin
     */
    public void setColormin(float colormin) {
        this.colormin = colormin;
    }

    /**
     * Getter of colormax
     * @return float, value of colormax
     */
    public float getColormax() {
        return colormax;
    }

    /**
     * Setter of colormax
     * @param colormax float, new value of colormax
     */
    public void setColormax(float colormax) {
        this.colormax = colormax;
    }

    /**
     * Getter of nbofgroup
     * @return int, value of nbofgroup
     */
    public static int getNbofGroup() {
        return nbofgroup;
    }

    /**
     * Accessor on nbofgroup, decrease the number of group by one
     */
    public static void lessNbofGroup() {
        nbofgroup--;
    }

    /**
     * Seeking colormin and colormax within a cube
     * @param cube Cube, the coordinates of the group
     * @param tabimage float[][][], a 3Dtab containing pixel colors
     */
    public void seekingMinMax(Cube cube, float[][][] tabimage){
        float colorintab;
        for(int i=cube.getStartX(); i<cube.getEndX(); i++){
            for(int j=cube.getStartY(); j<cube.getEndY(); j++){
                for(int k=cube.getStartZ(); k<cube.getEndZ(); k++){
                    colorintab=tabimage[i][j][k];
                    if(colorintab<colormin) colormin = colorintab;
                    if(colorintab>colormax) colormax = colorintab;
                }
            }
        }
    }

}