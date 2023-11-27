package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import Main.Cube;
import Main.Split;
class SplitTest extends Split{
    Cube cube1;
    Cube cube2;
    float[][][] image = new float[5][5][5];
    float homogeneityC;
    @BeforeEach
    void setUp() {
        homogeneityC=6;
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

    @AfterEach
    void tearDown() {
    }

    @Test
    void homogeneityTest() {
        //Those tests check if the function behaves as expected:
        //If the difference of the min and max of a group is strictly inferior to homogeneityC, true is returned
        //Otherwise, false is returned
        assertAll(
                () -> assertTrue(Split.homogeneityTest(cube1, image, homogeneityC)),//difference inferior to homogeneityC
                () -> assertFalse(Split.homogeneityTest(cube2, image, homogeneityC))//difference equals homogeneityC
        );
    }
}