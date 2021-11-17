package outbreak.model;

import com.sun.javafx.geom.Dimension2D;
import com.sun.javafx.geom.Point2D;
import javafx.scene.paint.Color;

import java.util.List;

public abstract class Building {

	protected Point2D position;

	protected int WIDTH;
	protected int HEIGHT;

	protected Dimension2D playground;
	protected Dimension2D size;

	protected int time = 0;

	protected List<Ball> inhabitants;
	
	protected String imageLocation;

	public abstract void checkForOverdue();

	protected abstract void releaseInhabitants();

	public void increaseTime() {
		time++;
	}

	public void addInhabitant(Ball b) {
		if (!this.inhabitants.contains(b)) {
			this.inhabitants.add(b);
			b.setIfInside(true);
		}
	}

	public synchronized void removeInhabitants(List<Ball> balls) {
		for (Ball b : balls) {
			b.setIfInside(false);
			
			// moving the balls away from the building to avoid new Collisions
			int down = 1;
			if(b.getDirection() > 90 && b.getDirection() < 270)
				down = -1;
			
			Point2D p = new Point2D();
			p.setLocation(b.getPosition().x, b.getPosition().y + 2 * this.HEIGHT * down);
			b.setPosition(p);
		}
		this.inhabitants.removeAll(balls);
	}

	public Point2D getPosition() {
		return this.position;
	}

	public void setPosition(Point2D point) {
		this.position = point;
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public void setWIDTH(int WIDTH) {
		this.WIDTH = WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public void setHEIGHT(int HEIGHT) {
		this.HEIGHT = HEIGHT;
	}

	public Dimension2D getPlayground() {
		return playground;
	}

	public void setPlayground(Dimension2D playground) {
		this.playground = playground;
	}

	public Dimension2D getSize() {
		return size;
	}

	public void setSize(Dimension2D size) {
		this.size = size;
	}

	public List<Ball> getInhabitants() {
		return inhabitants;
	}

	public void setInhabitants(List<Ball> inhabitants) {
		this.inhabitants = inhabitants;
	}
	
	public String getImageLocation() {
		return this.imageLocation;
	}
	
	@Override
	public String toString() {
		return this.getClass().toString().replace("class outbreak.model.", "") + " at " + this.position.toString() + " with size of " + this.WIDTH + " * " + this.HEIGHT;
	}

}
