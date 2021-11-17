package outbreak.controller;

import outbreak.model.Ball;

public class BallBallCollision extends Collision {
	
	private static final double DEFAULT_CHANCE_OF_INFECTION = 0.2;
	
	private Ball ball1;
	private Ball ball2;
		
	public BallBallCollision(Ball b1, Ball b2) {
		this.ball1 = b1;
		this.ball2 = b2;
	}

	@Override
	public boolean detectCollision() {
		float b1X = ball1.getPosition().x;
		float b1Y = ball1.getPosition().y;
		double b1R = ball1.getRadius();
		
		float b2X = ball2.getPosition().x;
		float b2Y = ball2.getPosition().y;
		double b2R = ball2.getRadius();
		
		boolean above = b1Y + b1R < b2Y - b2R;
		boolean below = b1Y - b1R> b2Y + b2R;
		boolean right = b1X + b1R < b2X - b2R;
		boolean left = b1X - b1R > b2X + b2R;
		
		return !above && !below && !right && !left;	
	}

	@Override
	public void collide() {
		// System.out.println("here");
		if ((ball1.isInfected() || ball2.isInfected()) && Math.random() < DEFAULT_CHANCE_OF_INFECTION) {
			ball1.infect();
			ball2.infect();
		}
		
	}

}
