package outbreak.model;

import java.util.Random;

import com.sun.javafx.geom.Dimension2D;
import com.sun.javafx.geom.Point2D;

import javafx.scene.paint.Color;

public class Ball {
	
	private static final int DEFAULT_RADIUS = 5;
	private static final Color DEFAULT_HEALTHY_COLOR = Color.BLUE;
	private static final Color DEFAULT_INFECTED_COLOR = Color.RED;
	
	public static final int MAX_ANGLE = 360;
	public static final int HALF_ANGLE = MAX_ANGLE / 2;
	
	private Point2D position;
	private Dimension2D playground;
	
	private Color color;
	private double radius;
	
	private int direction; // degree: between 0 and 360
	private int speed; // between 5 and 10
	private boolean infected;
	private boolean inside;
	
	private Random random;
	
	
	public Ball(Dimension2D playingSurface, Point2D position) {
		this.random = new Random();
		
		this.position = position;
		this.color = DEFAULT_HEALTHY_COLOR;
		
		this.playground = playingSurface;
		setRandomDirection();
		this.speed = 5;
		this.radius = DEFAULT_RADIUS;
		
		this.infected = false;
		this.inside = false;
		
		System.out.println(this);
	}
	
	public void move() {
		// System.out.println(this.getDirection());
		double maxX = this.playground.width;
		double maxY = this.playground.height;
		// calculate delta between old coordinates and new ones based on speed and
		// direction
		double deltaX = this.speed * Math.sin(Math.toRadians(this.direction));
		double deltaY = this.speed * Math.cos(Math.toRadians(this.direction));
		double newX = this.position.x + deltaX;
		double newY = this.position.y + deltaY;

		// calculate position in case the border of the game board has been reached
		if (newX < 0) {
			newX = -newX;
			this.direction = MAX_ANGLE - this.direction;
		} else if (newX + this.getRadius() > maxX) {
			newX = 2 * maxX - newX - 2 * this.getRadius();
			this.direction = MAX_ANGLE - this.direction;
		}

		if (newY < 0) {
			newY = -newY;
			this.direction = HALF_ANGLE - this.direction;
			if (this.direction < 0) {
				this.direction = MAX_ANGLE + this.direction;
			}
		} else if (newY + this.getRadius() > maxY) {
			newY = 2 * maxY - newY - 2 * this.getRadius();
			this.direction = HALF_ANGLE - this.direction;
			if (this.direction < 0) {
				this.direction = MAX_ANGLE + this.direction;
			}
		}
		// set coordinates
		this.position.x = (float) newX;
		this.position.y = (float) newY;
	}
	
	public void infect() {
		this.infected = true;
		this.color = DEFAULT_INFECTED_COLOR;
	}
	
	public void recover() {
		this.infected = false;
		this.color = DEFAULT_HEALTHY_COLOR;
	}
	
	public boolean isInfected() {
		return this.infected;
	}
	
	public boolean isInside() {
		return this.inside;
	}
	
	public void setIfInside(boolean v) {
		//System.out.println(this.inside);
		this.inside = v;
	}
	
	public Point2D getPosition() {
		return this.position;
	}
	
	public void setPosition(Point2D pos) {
		this.position = pos;
	}
	
	public double getRadius() {
		return this.radius;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public int getDirection() {
		return this.direction;
	}
	
	public void setDirection(int newDir) {
		this.direction = newDir % MAX_ANGLE;
		
		if (this.direction < 0)
			this.direction = this.direction + MAX_ANGLE;
	}
	
	public void setRandomDirection() {
		this.direction = this.random.nextInt(MAX_ANGLE);
	}
	
	@Override
	public String toString() {
		return "Ball at " + this.position.toString() + " with Radius of " + radius + ", Infected: " + this.infected + ", Inside: " + this.inside;
	}

}
