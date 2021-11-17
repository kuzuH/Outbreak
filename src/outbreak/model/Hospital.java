package outbreak.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sun.javafx.geom.Dimension2D;
import com.sun.javafx.geom.Point2D;
import javafx.scene.paint.Color;

public class Hospital extends Building {

	private static final int DEFAULT_RECOVER_TIME = 4; // Time until leaving the hospital in seconds

	public Hospital(Dimension2D playingSurface, Point2D pos) {
		this.WIDTH = 70;
		this.HEIGHT= 60;
		this.position = pos;
		this.size = new Dimension2D(WIDTH, HEIGHT);
		this.playground = playingSurface;
		this.inhabitants = new ArrayList();
		this.imageLocation = "hospital.png";
		System.out.println(this.toString());
	}

	@Override
	public void checkForOverdue() {
		if (time > DEFAULT_RECOVER_TIME * 25) {
			// System.out.println("Hospital Release of " + this.inhabitants.toString());
			time = 0;
			this.releaseInhabitants();
		}
	}

	@Override
	protected synchronized void releaseInhabitants() {
		List<Ball> toRemove = new ArrayList<Ball>();

		for (Ball b : this.inhabitants) {
			b.recover();
			toRemove.add(b);
		}

		this.removeInhabitants(toRemove);
	}
	
}
