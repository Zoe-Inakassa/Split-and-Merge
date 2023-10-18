// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import org.itk.simple.*;
public class Main {
    public static void main(String[] args) {
        if(args.length<4){
            System.exit(1);
        }

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

        //writing picture
        for(int x=0;x<xImage;x++){
            vector.set(0,x*e);
            for(int y=0;y<yImage;y++){
                vector.set(1,y*e);
                for(int z=0;z<zImage;z++){
                    vector.set(2,z*e);
                    image.setPixelAsFloat(vector,tabImage[x][y][z]);
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