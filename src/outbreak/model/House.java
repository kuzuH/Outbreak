package outbreak.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sun.javafx.geom.Dimension2D;
import com.sun.javafx.geom.Point2D;

public class House extends Building {
	private static final int DEFAULT_VISIT_TIME = 2;

	private static final double DEFAULT_CHANCE_OF_INFECTION = 0.2;

	private boolean infectedHouse = false;

	public House(Dimension2D playingSurface, Point2D pos) {
		this.WIDTH = 50;
		this.HEIGHT= 20;
		this.position = pos;
		this.size = new Dimension2D(WIDTH, HEIGHT);
		this.imageLocation = "house.png";
		this.playground = playingSurface;
		this.inhabitants = new ArrayList();
		System.out.println(this);
	}

	@Override
	public void addInhabitant(Ball b) {
		super.addInhabitant(b);
		if (b.isInfected()) {
			this.infectedHouse = true;
		}
	}

	@Override
	public void checkForOverdue() {
		if (time > DEFAULT_VISIT_TIME * 25) {
			time = 0;
			this.releaseInhabitants();
		}
	}

	@Override
	protected synchronized void releaseInhabitants() {
		List<Ball> toRemove = new ArrayList<Ball>();
		
		for (Ball b : this.inhabitants) {
			if (this.infectedHouse && Math.random() < DEFAULT_CHANCE_OF_INFECTION) {
				b.infect();
			}
			toRemove.add(b);
		}
		
		this.removeInhabitants(toRemove);
	}

}
