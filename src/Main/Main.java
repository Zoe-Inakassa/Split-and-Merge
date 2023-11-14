package Main;// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.util.ArrayList;

import org.itk.simple.*;
public class Main {
    public static void main(String[] args) {
        if(args.length<4){
            System.exit(1);
        }
        float homogeneityC = Float.parseFloat(args[1]);
        int volumeMin = Integer.parseInt(args[2]);
        Image image = SimpleITK.readImage(args[0], PixelIDValueEnum.sitkFloat32, "NiftiImageIO");
        /*ImageFileReader reader = new ImageFileReader();
        reader.setImageIO("NiftiImageIO");
        reader.setFileName(args[0]);
        Image image = reader.execute();*/
        if(image.getNumberOfComponentsPerPixel()!=1) System.out.println("careful number of component per pixel !=1");
        if(image.getSize().size()!=3){
            System.exit(2);//bad image dimension
        }
        int xImage =Math.toIntExact(image.getSize().get(0));
        int yImage =Math.toIntExact(image.getSize().get(1));
        int zImage =Math.toIntExact(image.getSize().get(2));
        System.out.println("Volume of the image :"+xImage*yImage*zImage);
        System.out.println("Dimension x of size :"+xImage);
        System.out.println("Dimension y of size :"+yImage);
        System.out.println("Dimension z of size :"+zImage);

        long startTime = System.currentTimeMillis();
        //reading picture
        float[][][] tabImage= new float[xImage][yImage][zImage];
        VectorUInt32 vector = new VectorUInt32(3,0);
        vector.set(0,0L);
        vector.set(1,0L);
        vector.set(2,0L);
        float minColor=image.getPixelAsFloat(vector);
        float maxColor=image.getPixelAsFloat(vector);
        Long e = 1L;
        for(int x=0;x<xImage;x++){
            vector.set(0,x*e);
            for(int y=0;y<yImage;y++){
                vector.set(1,y*e);
                for(int z=0;z<zImage;z++){
                    vector.set(2,z*e);
                    tabImage[x][y][z]= image.getPixelAsFloat(vector);
                    if(tabImage[x][y][z]<minColor) minColor=tabImage[x][y][z];
                    if(tabImage[x][y][z]>maxColor) maxColor=tabImage[x][y][z];
                }
            }
        }
        System.out.println("maxcolor=" + maxColor);
        System.out.println("mincolor=" + minColor);
        homogeneityC=homogeneityC*(maxColor);
        //algorithm
        ArrayList<Cube> cubeList = new ArrayList<>();
        ArrayList<Group> groupList = new ArrayList<>();
        Cube imageCube = new Cube(0,0,0,xImage,yImage,zImage);

        long startSplitTime = System.currentTimeMillis();
        Split.split(homogeneityC,volumeMin,tabImage,imageCube,cubeList,groupList);
        long endSplitTime = System.currentTimeMillis() - startSplitTime;
        long elapsedSplitSeconds = endSplitTime / 1000;
        System.out.println("Le split a mis " + elapsedSplitSeconds + " secondes.");
        System.out.println("Il y a " + Group.getNbofGroup() + " groupes après l'opération split.");

        long startGrapheTime = System.currentTimeMillis();
        ArrayList<Arc> arcList= Graph.makeNeighbourGraph(cubeList);
        long endGrapheTime = System.currentTimeMillis() - startGrapheTime;
        long elapsedGrapheSeconds = endGrapheTime / 1000;
        System.out.println("La création du graphe a mis " + elapsedGrapheSeconds + " secondes.");


        long startMergeTime = System.currentTimeMillis();
        int[] associatedGroups = Merge.merge(homogeneityC, arcList, groupList);
        long endMergeTime = System.currentTimeMillis() - startMergeTime;
        long elapsedMergeSeconds = endMergeTime / 1000;
        System.out.println("Le merge a mis " + elapsedMergeSeconds + " secondes.");

        //writing picture
        //carefull this only work if the image haven't been merged
        float color;
        Group group;
        for(int i=0;i<cubeList.size();i++){
            group = groupList.get(associatedGroups[i]);
            color=(group.getColormax()-group.getColormin())/2+group.getColormin();
            for(int x=cubeList.get(i).getStartX();x<cubeList.get(i).getEndX();x++){
                vector.set(0,x*e);
                for(int y=cubeList.get(i).getStartY();y<cubeList.get(i).getEndY();y++){
                    vector.set(1,y*e);
                    for(int z=cubeList.get(i).getStartZ();z<cubeList.get(i).getEndZ();z++){
                        vector.set(2,z*e);
                        image.setPixelAsFloat(vector,color);
                    }
                }
            }
        }
        ImageFileWriter writer = new ImageFileWriter();
        writer.setFileName(args[3]);
        writer.execute(image);
        long elapsedTime = System.currentTimeMillis() - startTime;
        long elapsedSeconds = elapsedTime / 1000;
        System.out.println("Il y a " + Group.getNbofGroup() + " groupes finaux.");
        System.out.println("Fin. Le programme a mis " + elapsedSeconds + " secondes à lire puis réécrire l'image.");

        System.exit(0);
    }
}