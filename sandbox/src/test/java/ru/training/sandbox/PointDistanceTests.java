package ru.training.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;


public class PointDistanceTests {

    @Test
    public void testDistance1() {
        Point p1 = new Point(1,1);
        Point p2 = new Point(2,2);

        Assert.assertEquals((Math.round(p1.distance(p2)*1000.0)/1000.0), 1.414);
    }

    @Test
    public void testDistance2() {
        Point p1 = new Point(-1,-1);
        Point p2 = new Point(-2,-2);

        Assert.assertEquals((Math.round(p1.distance(p2)*1000.0)/1000.0), 1.414);
    }

    @Test
    public void testDistance3() {
        Point p1 = new Point(0,0);
        Point p2 = new Point(0,0);

        Assert.assertEquals(p1.distance(p2), 0.0);
    }


}
