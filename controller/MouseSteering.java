package outbreak.controller;

import com.sun.javafx.geom.Point2D;

import javafx.scene.input.MouseEvent;
import outbreak.model.Paddle;
import outbreak.view.GameBoardUI;

public class MouseSteering {
	
	private Paddle paddle;
	
	public MouseSteering(GameBoardUI gameBoardUI, Paddle paddle) {
		this.paddle = paddle;
		gameBoardUI.addEventHandler(MouseEvent.MOUSE_PRESSED, this::mousePressed);
	}
	
	public void mousePressed(MouseEvent clickEvent) {		
		Point2D click = new Point2D();
		click.setLocation((float) clickEvent.getX() - paddle.getSize().width / 2, (float) paddle.getPosition().y);
		paddle.setPosition(click);
	}
	
}
