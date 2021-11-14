package outbreak.controller;

import outbreak.model.Ball;
import outbreak.model.Paddle;

public class PaddleBallCollision extends Collision {
	
	private Paddle paddle;
	private Ball ball;
	
	public PaddleBallCollision(Ball ball, Paddle paddle) {
		this.paddle = paddle;
		this.ball = ball;
	}

	@Override
	public boolean detectCollision() {
		float bX = ball.getPosition().x;
		float bY = ball.getPosition().y;
		double bR = ball.getRadius();
		
		float pX = paddle.getPosition().x;
		float pY = paddle.getPosition().y;
		float pW = paddle.getSize().width;
		float pH = paddle.getSize().height;
		
		return bX + bR > pX && bX + bR < pX + pW && bY + bR > pY && bY + bR < pY + pH;
	}

	@Override
	public void collide() {
		float bX = ball.getPosition().x;
		float bY = ball.getPosition().y;
		double bR = ball.getRadius();
		
		float pX = paddle.getPosition().x;
		float pY = paddle.getPosition().y;
		float pW = paddle.getSize().width;
		float pH = paddle.getSize().height;
		
		double dir;
		
		if (bX > pX + (pW / 2)) {
			dir = (double) ((bX - pX - (pW/2)) / (pW/2)) * (ball.HALF_ANGLE/2) + (ball.HALF_ANGLE / 2);
		} else {
			dir = (double) (1 - (bX - pX) / (pW/2)) * (ball.HALF_ANGLE/2) + ball.HALF_ANGLE;
		}
		
		//System.out.println(ball.MAX_ANGLE - dir);
		ball.setDirection((int) dir);
	}

}
