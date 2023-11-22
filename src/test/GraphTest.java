package test;

import Main.Arc;
import Main.Cube;
import Main.Graph;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    private final ArrayList<Cube> cube = new ArrayList<>();
    private final int xsize=200, ysize=200, zsize=200;
    @BeforeEach
    void setUp() {
        cube.add(new Cube(0,0,0,100,100,100));       //0
        cube.add(new Cube(0,0,100,100,100,200));     //1
        cube.add(new Cube(0,100,0,100,200,100));     //2
        cube.add(new Cube(0,100,100,100,200,200));   //3
        cube.add(new Cube(100,0,0,200,100,100));     //4
        cube.add(new Cube(100,0,100,200,100,200));   //5
        cube.add(new Cube(100,100,0,200,200,100));   //6
        cube.add(new Cube(100,100,100,200,200,200)); //7
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testmakeNeighbourGraph() {
        ArrayList<Arc> arcs= Graph.makeNeighbourGraph(cube);
        assertAll(() -> Assertions.assertEquals(12, arcs.size()),
                () -> Assertions.assertTrue(arcs.contains(new Arc(0,1))),
                () -> Assertions.assertTrue(arcs.contains(new Arc(0,2))),
                () -> Assertions.assertFalse(arcs.contains(new Arc(0,3))),
                () -> Assertions.assertTrue(arcs.contains(new Arc(0,4))),
                () -> Assertions.assertFalse(arcs.contains(new Arc(0,5))),
                () -> Assertions.assertFalse(arcs.contains(new Arc(0,6))),
                () -> Assertions.assertFalse(arcs.contains(new Arc(0,7))),
                () -> Assertions.assertFalse(arcs.contains(new Arc(1,2))),
                () -> Assertions.assertTrue(arcs.contains(new Arc(1,3))),
                () -> Assertions.assertFalse(arcs.contains(new Arc(1,4))),
                () -> Assertions.assertTrue(arcs.contains(new Arc(1,5))),
                () -> Assertions.assertFalse(arcs.contains(new Arc(1,6))),
                () -> Assertions.assertFalse(arcs.contains(new Arc(1,7))),
                () -> Assertions.assertTrue(arcs.contains(new Arc(2,3))),
                () -> Assertions.assertFalse(arcs.contains(new Arc(2,4))),
                () -> Assertions.assertFalse(arcs.contains(new Arc(2,5))),
                () -> Assertions.assertTrue(arcs.contains(new Arc(2,6))),
                () -> Assertions.assertFalse(arcs.contains(new Arc(2,7))),
                () -> Assertions.assertFalse(arcs.contains(new Arc(3,4))),
                () -> Assertions.assertFalse(arcs.contains(new Arc(3,5))),
                () -> Assertions.assertFalse(arcs.contains(new Arc(3,6))),
                () -> Assertions.assertTrue(arcs.contains(new Arc(3,7))),
                () -> Assertions.assertTrue(arcs.contains(new Arc(4,5))),
                () -> Assertions.assertTrue(arcs.contains(new Arc(4,6))),
                () -> Assertions.assertFalse(arcs.contains(new Arc(4,7))),
                () -> Assertions.assertFalse(arcs.contains(new Arc(5,6))),
                () -> Assertions.assertTrue(arcs.contains(new Arc(5,7))),
                () -> Assertions.assertTrue(arcs.contains(new Arc(6,7))));

    }

    @Test
    void testMakeNeighbourGraphAndSuppressingDuplicates() {
        ArrayList<Arc> arcs= Graph.makeNeighbourGraph(cube,200,200,200);
        ArrayList<Arc> arcs2 = Graph.suppressingDuplicates(arcs);
        assertAll(() -> Assertions.assertEquals(12, arcs2.size()),
                () -> Assertions.assertTrue(arcs2.contains(new Arc(0,1))),
                () -> Assertions.assertTrue(arcs2.contains(new Arc(0,2))),
                () -> Assertions.assertFalse(arcs2.contains(new Arc(0,3))),
                () -> Assertions.assertTrue(arcs2.contains(new Arc(0,4))),
                () -> Assertions.assertFalse(arcs2.contains(new Arc(0,5))),
                () -> Assertions.assertFalse(arcs2.contains(new Arc(0,6))),
                () -> Assertions.assertFalse(arcs2.contains(new Arc(0,7))),
                () -> Assertions.assertFalse(arcs2.contains(new Arc(1,2))),
                () -> Assertions.assertTrue(arcs2.contains(new Arc(1,3))),
                () -> Assertions.assertFalse(arcs2.contains(new Arc(1,4))),
                () -> Assertions.assertTrue(arcs2.contains(new Arc(1,5))),
                () -> Assertions.assertFalse(arcs2.contains(new Arc(1,6))),
                () -> Assertions.assertFalse(arcs2.contains(new Arc(1,7))),
                () -> Assertions.assertTrue(arcs2.contains(new Arc(2,3))),
                () -> Assertions.assertFalse(arcs2.contains(new Arc(2,4))),
                () -> Assertions.assertFalse(arcs2.contains(new Arc(2,5))),
                () -> Assertions.assertTrue(arcs2.contains(new Arc(2,6))),
                () -> Assertions.assertFalse(arcs2.contains(new Arc(2,7))),
                () -> Assertions.assertFalse(arcs2.contains(new Arc(3,4))),
                () -> Assertions.assertFalse(arcs2.contains(new Arc(3,5))),
                () -> Assertions.assertFalse(arcs2.contains(new Arc(3,6))),
                () -> Assertions.assertTrue(arcs2.contains(new Arc(3,7))),
                () -> Assertions.assertTrue(arcs2.contains(new Arc(4,5))),
                () -> Assertions.assertTrue(arcs2.contains(new Arc(4,6))),
                () -> Assertions.assertFalse(arcs2.contains(new Arc(4,7))),
                () -> Assertions.assertFalse(arcs2.contains(new Arc(5,6))),
                () -> Assertions.assertTrue(arcs2.contains(new Arc(5,7))),
                () -> Assertions.assertTrue(arcs2.contains(new Arc(6,7))));
    }

    @Test
    void testsuppressingDuplicates() {
        ArrayList<Arc> arcs=new ArrayList<>();
        arcs.add(new Arc(3,4));
        arcs.add(new Arc(3,4));
        arcs.add(new Arc(4,3));
        arcs.add(new Arc(5,6));
        arcs.add(new Arc(7,6));
        arcs.add(new Arc(2,6));
        arcs.add(new Arc(2,3));
        arcs.add(new Arc(7,6));
        ArrayList<Arc> arcs2 = Graph.suppressingDuplicates(arcs);
        assertAll(() -> Assertions.assertEquals(5, arcs2.size()),
                () -> Assertions.assertTrue(arcs2.contains(new Arc(3,4))),
                () -> Assertions.assertTrue(arcs2.contains(new Arc(5,6))),
                () -> Assertions.assertTrue(arcs2.contains(new Arc(6,7))),
                () -> Assertions.assertTrue(arcs2.contains(new Arc(2,6))),
                () -> Assertions.assertTrue(arcs2.contains(new Arc(2,3))));
    }
}