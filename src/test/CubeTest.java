package test;

import Main.Cube;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertAll;

//import static org.junit.jupiter.api.Assertions.*;
class CubeTest {
    private final Cube[] cube = new Cube[8];
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        cube[0]= new Cube(0,0,0,100,0,5);
        cube[1]= new Cube(0,30,0,1,0,10);
        cube[2]= new Cube(0,0,0,100,40,5);
        cube[3]= new Cube(0,0,30,100,20,90);
        cube[4]= new Cube(0,0,5,100,20,90);
        cube[5]= new Cube(100,0,90,110,20,100);
        cube[6]= new Cube(0,10,5,100,20,60);
        cube[7]= new Cube(0,10,5,100,20,95);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void testisNeighbourWith() {
        assertAll(() -> Assertions.assertFalse(cube[2].isNeighbourWith(cube[3])),//not neighbour on z
                () -> Assertions.assertFalse(cube[4].isNeighbourWith(cube[5])),//touching on two sides
                () -> Assertions.assertTrue(cube[2].isNeighbourWith(cube[4])),//neighbours on 2 coordinates + z ouch
                () -> Assertions.assertTrue(cube[2].isNeighbourWith(cube[6])),//neighbours with one coordinates within another coordinates
                () -> Assertions.assertTrue(cube[5].isNeighbourWith(cube[7])));//first cube called before the second
    }

    @org.junit.jupiter.api.Test
    void testgetCubeVolume() {
        // Assert
        assertAll(() -> Assertions.assertEquals(0, cube[0].getCubeVolume()),
                () -> Assertions.assertEquals(-30*10, cube[1].getCubeVolume()),//a bad created cube lead to negative volume
                () -> Assertions.assertEquals(100*40*5, cube[2].getCubeVolume()),
                () -> Assertions.assertEquals(100*20*60, cube[3].getCubeVolume()));
    }
}