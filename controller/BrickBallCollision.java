package outbreak.controller;

import com.sun.javafx.geom.Dimension2D;
import com.sun.javafx.geom.Point2D;

import outbreak.model.Ball;
import outbreak.model.Building;

public class BrickBallCollision extends Collision{
	
	private Ball ball;
	private Building building;
	
	public BrickBallCollision(Ball ball, Building building) {
		this.ball = ball;
		this.building = building;
	}

	@Override
	public boolean detectCollision() {
		Point2D pBall = ball.getPosition();
		double radBall = ball.getRadius();

		Point2D pBuild = building.getPosition();
		Dimension2D dBuild = building.getSize();

		boolean above = pBall.y + radBall < pBuild.y;
		boolean below = pBall.y - radBall > pBuild.y + dBuild.height;
		boolean right = pBall.x - radBall > pBuild.x + dBuild.width;
		boolean left = pBall.x + radBall < pBuild.x;

		return !above && !below && !right && !left;
	}

	@Override
	public void collide() {
		building.addInhabitant(ball);
		// System.out.println("Ball in Brick");
	}

}
