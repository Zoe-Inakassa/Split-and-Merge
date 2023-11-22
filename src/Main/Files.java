package Main;

import org.itk.simple.PixelIDValueEnum;
import org.itk.simple.SimpleITK;
import org.itk.simple.*;

import java.util.ArrayList;

public class Files {
    public static void ReadingImage(String fileName, ImageProcessing imageProcessing) {
        imageProcessing.setImage(SimpleITK.readImage(fileName, PixelIDValueEnum.sitkFloat32, "NiftiImageIO"));
        /*ImageFileReader reader = new ImageFileReader();
        reader.setImageIO("NiftiImageIO");
        reader.setFileName(args[0]);
        imageTraitement.setImage(reader.execute());*/
        Image image =imageProcessing.getImage();
        if (image.getNumberOfComponentsPerPixel() != 1) System.out.println("careful number of component per pixel !=1");
        if (image.getSize().size() != 3) {
            System.out.println("bad image dimension");
            System.exit(2);//bad image dimension
        }
        imageProcessing.setxLengthImage(Math.toIntExact(image.getSize().get(0)));
        int xImage = imageProcessing.getxLengthImage();
        imageProcessing.setyLengthImage(Math.toIntExact(image.getSize().get(1)));
        int yImage = imageProcessing.getyLengthImage();
        imageProcessing.setzLengthImage(Math.toIntExact(image.getSize().get(2)));
        int zImage = imageProcessing.getzLengthImage();
        //reading picture
        imageProcessing.setTabImage(new float[xImage][yImage][zImage]);
        float[][][] tabImage= imageProcessing.getTabImage();
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
        imageProcessing.setMaxcolor(maxColor);
        imageProcessing.setMincolor(minColor);
    }

    public static void WritingImage(ImageProcessing imageProcessing,String fileName, ArrayList<Cube> cubeList, ArrayList<Group> groupList, int[] associatedGroups) {
        VectorUInt32 vector = new VectorUInt32(3,0);
        Long e = 1L;
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
                        imageProcessing.getImage().setPixelAsFloat(vector,color);
                    }
                }
            }
        }
        ImageFileWriter writer = new ImageFileWriter();
        writer.setFileName(fileName);
        writer.execute(imageProcessing.getImage());
    }
}
