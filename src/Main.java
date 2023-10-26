// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
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

        ImageFileReader reader = new ImageFileReader();
        reader.setImageIO("NiftiImageIO");
        reader.setFileName(args[0]);
        Image image = reader.execute();
        if(image.getNumberOfComponentsPerPixel()!=1) System.out.println("carefull number of component per pixel !=1");
        if(image.getSize().size()!=3){
            System.exit(2);//bad image dimension
        }
        int xImage =Math.toIntExact(image.getSize().get(0));
        int yImage =Math.toIntExact(image.getSize().get(1));
        int zImage =Math.toIntExact(image.getSize().get(2));

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
        Split.split(homogeneityC,volumeMin,tabImage,imageCube,cubeList,groupList);

        //writing picture
        //carefull this only work if the image haven't been merged
        float color;
        for(int i=0;i<groupList.size();i++){
            color=(groupList.get(i).getColormax()-groupList.get(i).getColormin())/2+groupList.get(i).getColormin();
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

        System.out.println("fin");
        System.exit(0);
    }
}