package outbreak.view;

import java.net.URL;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.javafx.geom.Dimension2D;
import com.sun.javafx.geom.Point2D;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import outbreak.controller.AudioPlayerAdapter;
import outbreak.controller.GameController;
import outbreak.controller.MouseSteering;
import outbreak.model.Ball;
import outbreak.model.Building;
import outbreak.model.Hospital;
import outbreak.model.Paddle;

public class GameBoardUI extends Canvas {

	private static final int UPDATE_PERIOD = 1000 / 25; // 1s:25 -> 25 fps

	private static final Color BACKGROUND_COLOR = Color.WHITE;
	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 300;
	private static final Dimension2D DEFAULT_SIZE = new Dimension2D(DEFAULT_WIDTH, DEFAULT_HEIGHT);

	public static Dimension2D getPreferredSize() {
		return DEFAULT_SIZE;
	}

	private Timer gameTimer;
	private final GameToolBar gameToolBar;
	private GameController gameController;
	private MouseSteering mouseSteering;
	
	private final AudioPlayerAdapter audioPlayer;
	
	private HashMap<String, Image> imageCache;

	public GameBoardUI(GameToolBar gameToolBar) {
		this.gameToolBar = gameToolBar;
		this.audioPlayer = new AudioPlayerAdapter();
		setup();
	}

	public void setup() {
		setupGameController();
		setupImageCache();
		this.gameToolBar.updateToolBarStatus(false);
		paint();
	}

	private void setupImageCache() {
		this.imageCache = new HashMap<>();
		for (Building bui : this.gameController.getBuildings()) {
			String imageLocation = bui.getImageLocation();
			this.imageCache.computeIfAbsent(imageLocation, this::getImage);
		}
	}
	
	private Image getImage(String carImageFilePath) {
		URL carImageUrl = getClass().getClassLoader().getResource(carImageFilePath);
		if (carImageUrl == null) {
			throw new IllegalArgumentException(
					"Please ensure that your resources folder contains the appropriate files for this exercise.");
		}
		return new Image(carImageUrl.toExternalForm());
	}

	public void setupGameController() {
		Dimension2D size = getPreferredSize();
		this.gameController = new GameController(size);
		widthProperty().set(size.width);
		heightProperty().set(size.height);
		this.mouseSteering = new MouseSteering(this, this.gameController.getPaddle());
	}

	public void startGame() {
		if (!this.gameController.isRunning()) {
			this.gameController.startGame();
			this.gameToolBar.updateToolBarStatus(true);
			startTimer();
			paint();
			this.audioPlayer.startMusic();
		}
	}

	private void startTimer() {
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				updateGame();
			}
		};
		if (this.gameTimer != null) {
			this.gameTimer.cancel();
		}
		this.gameTimer = new Timer();
		this.gameTimer.scheduleAtFixedRate(timerTask, UPDATE_PERIOD, UPDATE_PERIOD);
	}

	private void updateGame() {
		if (this.gameController.isRunning()) {
			this.gameController.update();
		} else {
			showAsyncAlert("The game has ended!");
		}

		paint();
	}

	/**
	 * Stops the game board and set the tool bar to default values.
	 */
	public void stopGame() {
		if (this.gameController.isRunning()) {
			this.gameController.stopGame();
			this.gameToolBar.updateToolBarStatus(false);
			this.gameTimer.cancel();
			this.audioPlayer.stopMusic();
		}
	}

	/**
	 * Render the graphics of the whole game by iterating through the cars of the
	 * game board at render each of them individually.
	 */
	private void paint() {
		getGraphicsContext2D().setFill(BACKGROUND_COLOR);
		getGraphicsContext2D().fillRect(0, 0, getWidth(), getHeight());

		for (Ball b : gameController.getBalls()) {
			paintBall(b);
		}

		for (Building b : gameController.getBuildings()) {
			paintBuilding(b);
		}

		paintPaddle();
	}

	private void paintBall(Ball b) {
		if (!b.isInside()) {
			this.getGraphicsContext2D().setFill(b.getColor());
			getGraphicsContext2D().fillOval(b.getPosition().x, b.getPosition().y, 2 * b.getRadius(), 2 * b.getRadius());
		}
	}

	private void paintPaddle() {
		Paddle p = this.gameController.getPaddle();
		this.getGraphicsContext2D().setFill(p.getColor());
		this.getGraphicsContext2D().fillRect(p.getPosition().x, p.getPosition().y, p.getSize().width,
				p.getSize().height);
	}

	private void paintBuilding(Building b) {
		Point2D buiPosition = b.getPosition();

		getGraphicsContext2D().drawImage(this.imageCache.get(b.getImageLocation()), buiPosition.x,
				buiPosition.y, b.getSize().width, b.getSize().height);
	}

	private void showAsyncAlert(String message) {
		Platform.runLater(() -> {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(message);
			alert.showAndWait();
			this.setup();
		});
	}

	public static int getDefaultWidth() {
		return DEFAULT_WIDTH;
	}

	public static int getDefaultHeight() {
		return DEFAULT_HEIGHT;
	}

}
