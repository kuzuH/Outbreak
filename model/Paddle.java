package outbreak.model;

import com.sun.javafx.geom.Dimension2D;
import com.sun.javafx.geom.Point2D;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle {
	
	public static final float DEFAULT_WIDTH = 80;
	public static final float DEFAULT_HEIGHT = 8;
	
	private static final Color DEFAULT_COLOR = Color.GRAY;
	
	private Point2D position; // position of the upper left corner
	private Dimension2D size;
		
	private Dimension2D playground;
	
	private Color color;
	
	public Paddle(Dimension2D playingSurface, Point2D pos) {
		this.position = pos;
		this.size = new Dimension2D(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.color = DEFAULT_COLOR;
		this.playground = playingSurface;
	}
	
	public void setPosition(Point2D point) {
		if (point.x + size.width > this.playground.width)
			point.x = this.playground.width - size.width;
		
		this.position = point;
	}
	
	public Point2D getPosition() {
		return this.position;
	}
	
	public Dimension2D getSize() {
		return this.size;
	}

	public Color getColor() {
		return this.color;
	}
}
