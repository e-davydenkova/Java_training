package ru.training.sandbox;

public class Main {

    public static void main(String[] args) {
        Point p1 = new Point(1,1);
        Point p2 = new Point(2,2);

        System.out.println("Distance between 2 point is " + p1.distance(p2));
    }

//    public static double distance(Point p1, Point p2){
//        return Math.sqrt((p2.x-p1.x)*(p2.x-p1.x)+(p2.y-p1.y)*(p2.y-p1.y));
//    };

}
