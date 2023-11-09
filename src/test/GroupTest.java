package test;

import static org.junit.jupiter.api.Assertions.*;
import Main.Cube;
import Main.Group;
class GroupTest {
    private Group group1;
    private Group group2;
    private Cube cube1;
    private Cube cube2;
    private float[][][] image;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        group1 = new Group();
        group2 = new Group();
        cube1 = new Cube(0, 0, 0, 2, 2, 2);
        cube2 = new Cube(2, 2, 2, 5, 5, 5);
        image = new float[5][5][5];
        for(int x=0; x<5; x++){
            for(int y=0; y<5; y++){
                for(int z=0; z<5; z++){
                    image[x][y][z] = x+y+z;
                }
            }
        }
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void testSeekingMinMax() {
        //those first two tests are used to check if, for a new group, the first call of SeekingMinMax behaves as expected
        group1.seekingMinMax(cube1, image);
        assertAll(
                () -> assertEquals(3, group1.getColormax()),
                () -> assertEquals(0, group1.getColormin())
        );
        group2.seekingMinMax(cube2, image);
        assertAll(
                () -> assertEquals(12, group2.getColormax()),
                () -> assertEquals(6, group2.getColormin())
        );
        //This last test is used to check if the min and max of a group are changed rightfully if the function is called again for a group
        group2.seekingMinMax(cube1, image);
        assertAll(
                () -> assertEquals(12, group2.getColormax()),
                () -> assertEquals(0, group2.getColormin())
        );
    }
}