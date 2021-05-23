/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.List;
import java.util.Set;

import java.util.ArrayList;
import java.util.HashSet;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        //throw new RuntimeException("implement me!");
    	
    	for (int i = 0; i < 4; i++) {
    		turtle.forward(sideLength);
    		turtle.turn(90);
    	}
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        //throw new RuntimeException("implement me!");
    	return (double)(sides - 2) * 180 / sides;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        //throw new RuntimeException("implement me!");
    	return (int) Math.ceil(360 / (180 - angle));
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        //throw new RuntimeException("implement me!");
    	for (int i = 0; i < sides; i++) {
    		turtle.forward(sideLength);
    		turtle.turn(180 - calculateRegularPolygonAngle(sides));
    	}
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
        //throw new RuntimeException("implement me!");
    	double targetBearing;

    	targetBearing = Math.atan2(targetY - currentY, targetX - currentX) / Math.PI * 180.0;
    	
    	if (targetBearing < 0)
    		targetBearing += 360.0;
    	targetBearing = 360.0 - targetBearing + 90.0;
    	if (targetBearing >= 360)
    		targetBearing -= 360.0;
    	double bearing = targetBearing - currentBearing;
    	if (bearing < 0)
    		bearing += 360.0;
    	return bearing;
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
        //throw new RuntimeException("implement me!");
    	double currentBearing = 0.0;
    	List<Double> bearing = new ArrayList<>();
    	for (int i = 0; i < xCoords.size() - 1; i++) {
    		bearing.add(calculateBearingToPoint(currentBearing, xCoords.get(i), yCoords.get(i), xCoords.get(i + 1), yCoords.get(i + 1)));
    		currentBearing = bearing.get(i);
    	}
    	return bearing;
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
        //throw new RuntimeException("implement me!");
    	if (points.size() <= 3)
    		return points;
    	
    	Set<Point> convexHull = new HashSet<Point>();
    	Point startPoint = new Point(Double.MAX_VALUE, Double.MAX_VALUE);
    	for (Point p : points) {
    		if (p.x() < startPoint.x() || (p.x() == startPoint.x() && p.y() < startPoint.y()))
    			startPoint = p;
    	}

    	Point pointInHull = startPoint;
    	Point tempPoint = null;
    	double minAngle, pAngle = 0;
    	do {
    		convexHull.add(pointInHull);
    		
    		for (Point p : points) {
    			if (p == pointInHull)
    				continue;
    			else {
    				tempPoint = p;
    				break;
    			}
    		}
    		minAngle = calculateBearingToPoint(pAngle, (int)pointInHull.x(), (int)pointInHull.y(), (int)tempPoint.x(), (int)tempPoint.y());

    		for (Point p : points) {
    			if (p == pointInHull)
    				continue;
    			
    			double tempAngle;
    			if (p == pointInHull)
    				tempAngle = 360.0;
    			else
    				tempAngle = calculateBearingToPoint(pAngle, (int)pointInHull.x(), (int)pointInHull.y(), (int)p.x(), (int)p.y());
    			
    			if (tempAngle < minAngle || (tempAngle == minAngle && (Math.pow((p.x() - pointInHull.x()), 2) + Math.pow((p.y() - pointInHull.y()), 2))
    					> (Math.pow((tempPoint.x() - pointInHull.x()), 2) + Math.pow((tempPoint.y() - pointInHull.y()), 2)))) {
    				minAngle = tempAngle;
    				tempPoint = p;
    			}
    		}

    		pAngle = calculateBearingToPoint(0, (int)pointInHull.x(), (int)pointInHull.y(), (int)tempPoint.x(), (int)tempPoint.y());
    		pointInHull = tempPoint;
    		//System.out.println("addPoint: " + tempPoint.x() + ", " + tempPoint.y());
    	} while (pointInHull != startPoint);
    	
    	return convexHull;
    }
    
    
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        //throw new RuntimeException("implement me!");
    	for (int i = 0; i < 6; i++) {
        	switch(i) {
    		case 0:
    			turtle.color(PenColor.BLUE);
    			break;
    		case 1:
    			turtle.color(PenColor.GREEN);
    			break;
    		case 2:
    			turtle.color(PenColor.YELLOW);
    			break;
    		case 3:
    			turtle.color(PenColor.ORANGE);
    			break;
    		case 4:
    			turtle.color(PenColor.RED);
    			break;
    		case 5:
    			turtle.color(PenColor.MAGENTA);
    		}
        	for (int j = 0; j < 20 - 3 * i; j++) {
                turtle.turn(360.0 / (20 - 3 * i));
                drawRegularPolygon(turtle, i + 3, (i + 3) * 10);
        	}
        }
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        //drawSquare(turtle, 40);
        //drawRegularPolygon(turtle, 4, 50);
        drawPersonalArt(turtle);

        // draw the window
        turtle.draw();
        
//        Set<Point> points = new HashSet<Point>();
//		Set<Point> convexHull = new HashSet<Point>();
//
//		Point p11 = new Point(1, 1);
//		Point p12 = new Point(1, 2);
//		Point p13 = new Point(1, 3);
//		Point p23 = new Point(2, 3);
//		Point p25 = new Point(2, 5);
//		Point p2f1 = new Point(2, -1);
//		Point p32 = new Point(3, 2);
//		Point p35 = new Point(3, 5);
//		Point p3f1 = new Point(3, -1);
//		Point p41 = new Point(4, 1);
//		Point p55 = new Point(5, 5);
//		Point p5f1 = new Point(5, -1);
//		Point p62 = new Point(6, 2);
//
//		points.add(p11);
//		points.add(p23);
//		points.add(p3f1);
//		points.add(p41);
//		points.add(p55);
//		points.add(p62);
//		points.add(p13);
//		points.add(p12);
//		points.add(p25);
//		points.add(p2f1);
//		points.add(p32);
//		points.add(p35);
//		points.add(p5f1);
//		
//		convexHull = convexHull(points);
//		
//		for (Point p : convexHull)
//			System.out.println(p.x() + ", " + p.y());
    }

}
