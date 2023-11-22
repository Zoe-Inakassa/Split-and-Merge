package Main;

import java.util.ArrayList;
import org.itk.simple.*;
public class ImageProcessing {

    private Image image;
    private float[][][] tabImage;
    private int xLengthImage;
    private int yLengthImage;
    private int zLengthImage;
    private int maxcolor;
    private int mincolor;

    public static void main(String[] args) {
        //reading arguments
        if(args.length<4){
            System.out.println("miss some arguments");
            System.exit(1);
        }
        float homogeneityC = Float.parseFloat(args[0]);
        int volumeMin = Integer.parseInt(args[1]);
        ImageProcessing imageProcessing =new ImageProcessing();

        //START-----------------------------------------------------------------------------
        long startTime = System.currentTimeMillis();
        Files.ReadingImage(args[2], imageProcessing);
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
        //method 1
        ArrayList<Arc> arcList= Graph.makeNeighbourGraph(cubeList,xImage,yImage,zImage);
        arcList = Graph.suppressingDuplicates(arcList);
        //method 2
        //ArrayList<Arc> arcList= Graph.makeNeighbourGraph(cubeList);
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
        Files.WritingImage(imageProcessing,args[3],cubeList,groupList,associatedGroups);

        //END------------------------------------------------------------------------------
        long elapsedTime = System.currentTimeMillis() - startTime;
        long elapsedSeconds = elapsedTime / 1000;
        System.out.println("Il y a " + Group.getNbofGroup() + " groupes finaux.");
        System.out.println("Fin. Le programme a mis " + elapsedSeconds + " secondes à lire puis réécrire l'image.");

        System.exit(0);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public float[][][] getTabImage() {
        return tabImage;
    }

    public void setTabImage(float[][][] tabImage) {
        this.tabImage = tabImage;
    }

    public int getxLengthImage() {
        return xLengthImage;
    }

    public void setxLengthImage(int xLengthImage) {
        this.xLengthImage = xLengthImage;
    }

    public int getyLengthImage() {
        return yLengthImage;
    }

    public void setyLengthImage(int yLengthImage) {
        this.yLengthImage = yLengthImage;
    }

    public int getzLengthImage() {
        return zLengthImage;
    }

    public void setzLengthImage(int zLengthImage) {
        this.zLengthImage = zLengthImage;
    }

    public int getMaxcolor() {
        return maxcolor;
    }

    public void setMaxcolor(int maxcolor) {
        this.maxcolor = maxcolor;
    }

    public int getMincolor() {
        return mincolor;
    }

    public void setMincolor(int mincolor) {
        this.mincolor = mincolor;
    }

    public ImageProcessing() {
        image=null;tabImage=null;
        xLengthImage=0;yLengthImage=0;zLengthImage=0;
        maxcolor=0;mincolor=0;
    }
}