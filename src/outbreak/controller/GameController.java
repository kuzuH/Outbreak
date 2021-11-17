package outbreak.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.sun.javafx.geom.Dimension2D;
import com.sun.javafx.geom.Point2D;

import outbreak.model.*;
import outbreak.view.GameBoardUI;

public class GameController {

	private static final int NUMBER_OF_BALLS = 5;
	private static final int NUMBER_OF_BUILDINGS = 3;

	private List<Ball> balls;

	private List<Building> buildings;

	private boolean running;

	private Paddle paddle;

	private Dimension2D surface;

	private Random random;

	public GameController(Dimension2D surface) {
		this.balls = new ArrayList<>();
		this.buildings = new ArrayList<>();

		this.running = false;

		Point2D pos = new Point2D();
		pos.x = surface.width / 2 - Paddle.DEFAULT_WIDTH / 2;
		pos.y = surface.height * 0.9f;
		this.paddle = new Paddle(surface, pos);

		this.surface = surface;

		this.random = new Random();

		initiate();
	}

	public void initiate() {
		for (int i = 0; i < NUMBER_OF_BALLS; i++) {
			balls.add(newBall());
		}
		for (int i = 0; i < NUMBER_OF_BUILDINGS; i++) {
			// int rows = i/5; //if we have more than 5 buildings
			Point2D pos = new Point2D();
			pos.x = (float) random.nextInt((int) surface.width); // i have it here now so that later we can base the
																	// spawn on which number of building it is
			pos.y = (float) random.nextInt((int) surface.height / 2);
			buildings.add(newBuilding(pos));
		}
	}

	public void update() {
		for (Ball b : this.balls) {
			if (!b.isInside()) {
				b.move();
			}
		}

		for (Building bui : this.buildings) {
			bui.increaseTime();
			bui.checkForOverdue();
		}

		for (Ball b : this.balls) {
			if (!b.isInside()) {
				PaddleBallCollision pCol = new PaddleBallCollision(b, paddle);
				if (pCol.detectCollision()) {
					pCol.collide();
				}
				for (Ball ba : balls) {
					BallBallCollision bCol = new BallBallCollision(b, ba);
					if (bCol.detectCollision()) {
						bCol.collide();
					}
				}
				for (Building bu : buildings) {
					BrickBallCollision brCol = new BrickBallCollision(b, bu);
					if (brCol.detectCollision()) {
						brCol.collide();
					}
				}
			}
		}
	}

	private Ball newBall() {
		Point2D pos = new Point2D();
		pos.x = (float) random.nextInt((int) surface.width);
		pos.y = (float) random.nextInt((int) surface.height / 2);
		Ball b = new Ball(surface, pos);

		if (random.nextBoolean()) {
			b.infect();
		}
		return b;
	}

	private Building newBuilding(Point2D pos) {
		if (random.nextBoolean()) {
			return new Hospital(surface, pos);
		}
		return new House(surface, pos);
	}

	public void startGame() {
		this.running = true;
	}

	public void stopGame() {
		this.running = false;
	}

	public boolean isRunning() {
		return this.running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public Paddle getPaddle() {
		return this.paddle;
	}

	public List<Ball> getBalls() {
		return this.balls;
	}

	public List<Building> getBuildings() {
		return buildings;
	}
}
