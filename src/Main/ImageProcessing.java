package Main;

import java.util.ArrayList;
import org.itk.simple.*;

/**
 * Main class, store information and contain main method
 */
public class ImageProcessing {

    private Image image;
    private float[][][] tabImage;
    private int xLengthImage;
    private int yLengthImage;
    private int zLengthImage;
    private float maxcolor;
    private float mincolor;

    /**
     * The main method, the heart of our program that will do all operations
     * This program objective is to apply the Split-and-Merge algorithm on an image
     * @param args String[], in order args must contain: an homogeneity criteria (always between 0 and 1),
     *             a volumeMin value (the algorithm will not split a cube which is already under this volume but the splited cube obtained can be under this volume)
     *             a method value 0 for 1rst method anything else for the 2nd method,
     *             the path and name to the original image,
     *             the path and name of the new image
     */
    public static void main(String[] args) {
        //reading arguments
        if(args.length<5){
            System.out.println("miss some arguments");
            System.exit(1);
        }
        float homogeneityC = Float.parseFloat(args[0]);
        if(homogeneityC>1 || homogeneityC<0){
            System.out.println("Homogeneity criteria is out of bounds");
            System.exit(2);
        }
        int volumeMin = Integer.parseInt(args[1]);
        ImageProcessing imageProcessing =new ImageProcessing();

        //START-----------------------------------------------------------------------------
        long startTime = System.currentTimeMillis();
        Files.ReadingImage(args[3], imageProcessing);
        homogeneityC=homogeneityC*(imageProcessing.maxcolor);
        int xImage=imageProcessing.xLengthImage;
        int yImage=imageProcessing.yLengthImage;
        int zImage=imageProcessing.zLengthImage;

        //Giving information on the image
        System.out.println("Volume of the image :" + xImage * yImage * zImage);
        System.out.println("Dimension x of size :" + xImage);
        System.out.println("Dimension y of size :" + yImage);
        System.out.println("Dimension z of size :" + zImage);
        System.out.println("maxcolor=" + imageProcessing.maxcolor);
        System.out.println("mincolor=" + imageProcessing.mincolor);

        //Algorithm
        ArrayList<Cube> cubeList = new ArrayList<>();
        ArrayList<Group> groupList = new ArrayList<>();
        Cube imageCube = new Cube(0,0,0, xImage, yImage,zImage);

        //Split
        long startSplitTime = System.currentTimeMillis();
        Split.split(homogeneityC,volumeMin,imageProcessing.tabImage,imageCube,cubeList,groupList);
        long endSplitTime = System.currentTimeMillis() - startSplitTime;
        long elapsedSplitSeconds = endSplitTime / 1000;
        System.out.println("Le split a mis " + elapsedSplitSeconds + " secondes.");
        System.out.println("Il y a " + Group.getNbofGroup() + " groupes après l'opération split.");

        //Graph
        long startGraphTime = System.currentTimeMillis();
        //choose a method here-----------------------------------------
        ArrayList<Arc> arcList;
        if(args[2].equals("0")){
            //method 1
            System.out.println("Méthode 1 utilisée");
            arcList= Graph.makeNeighbourGraph(cubeList);
        }else{
            //method 2
            System.out.println("Méthode 2 utilisée");
            arcList= Graph.makeNeighbourGraph(cubeList,xImage,yImage,zImage);
            arcList = Graph.suppressingDuplicates(arcList);
        }
        //-------------------------------------------------------------
        long endGraphTime = System.currentTimeMillis() - startGraphTime;
        long elapsedGraphSeconds = endGraphTime / 1000;
        System.out.println("La création du graphe a mis " + elapsedGraphSeconds + " secondes.");


        //Merge
        long startMergeTime = System.currentTimeMillis();
        int[] associatedGroups = Merge.merge(homogeneityC, arcList, groupList);
        long endMergeTime = System.currentTimeMillis() - startMergeTime;
        long elapsedMergeSeconds = endMergeTime / 1000;
        System.out.println("Le merge a mis " + elapsedMergeSeconds + " secondes.");

        //writing picture
        Files.WritingImage(imageProcessing,args[4],cubeList,groupList,associatedGroups);

        //END------------------------------------------------------------------------------
        long elapsedTime = System.currentTimeMillis() - startTime;
        long elapsedSeconds = elapsedTime / 1000;
        System.out.println("Il y a " + Group.getNbofGroup() + " groupes finaux.");
        System.out.println("Fin. Le programme a mis " + elapsedSeconds + " secondes à lire puis réécrire l'image.");
    }

    /**
     * Getter on image
     * @return Image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Setter on Image
     * @param image Image, the new Image
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Getter on tabImage
     * @return tabImage
     */
    public float[][][] getTabImage() {
        return tabImage;
    }

    /**
     * Setter on tabImage
     * @param tabImage float[][][], the new tab
     */
    public void setTabImage(float[][][] tabImage) {
        this.tabImage = tabImage;
    }

    /**
     * Getter on xLengthImage
     * @return xLengthImage
     */
    public int getxLengthImage() {
        return xLengthImage;
    }

    /**
     * Setter on xLengthImage
     * @param xLengthImage int, the new length on x side
     */
    public void setxLengthImage(int xLengthImage) {
        this.xLengthImage = xLengthImage;
    }

    /**
     * Getter on yLengthImage
     * @return yLengthImage
     */
    public int getyLengthImage() {
        return yLengthImage;
    }

    /**
     * Setter on yLengthImage
     * @param yLengthImage int, the new length on y side
     */
    public void setyLengthImage(int yLengthImage) {
        this.yLengthImage = yLengthImage;
    }

    /**
     * Getter on zLengthImage
     * @return zLengthImage
     */
    public int getzLengthImage() {
        return zLengthImage;
    }

    /**
     * Setter on zLengthImage
     * @param zLengthImage int, the new length on z side
     */
    public void setzLengthImage(int zLengthImage) {
        this.zLengthImage = zLengthImage;
    }

    /**
     * Getter on maxcolor
     * @return maxcolor
     */
    public float getMaxcolor() {
        return maxcolor;
    }

    /**
     * Setter on maxcolor
     * @param maxcolor float
     */
    public void setMaxcolor(float maxcolor) {
        this.maxcolor = maxcolor;
    }

    /**
     * Getter on mincolor
     * @return mincolor
     */
    public float getMincolor() {
        return mincolor;
    }

    /**
     * Setter on mincolor
     * @param mincolor float
     */
    public void setMincolor(float mincolor) {
        this.mincolor = mincolor;
    }

    /**
     * Constructor of ImageProcessing
     * choose basic values for each attributes to stay coherent
     */
    public ImageProcessing() {
        image=null;tabImage=null;
        xLengthImage=0;yLengthImage=0;zLengthImage=0;
        maxcolor=0;mincolor=0;
    }
}