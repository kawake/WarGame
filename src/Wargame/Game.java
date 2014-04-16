package Wargame;

import java.io.IOException;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

public class Game {
	private int winWidth;
	private int winHeight;
	private RenderWindow window;
	private Event event;
	private Color backColor;
	private Camera camera;
	private Map map;
	private TextureManager tManager;
	private RandomUnitGenerator generator;
	private int selectID;
	private UnitManager uManager;

	// private Reticule reticule;

	/***
	 * @param fwinWidth
	 * @param fwinHeight
	 * @param title
	 *            Give the windows width, height and title
	 * @throws IOException
	 */
	Game(int fwinWidth, int fwinHeight, String title) throws IOException {
		winWidth = fwinWidth;
		winHeight = fwinHeight;

		backColor = new Color(0, 0, 255); // blue

		// initialize the window
		VideoMode mode = new VideoMode(winWidth, winHeight);
		window = new RenderWindow(mode, title);

		// initialize the map
		map = new Map(32, 32, "map.txt");

		setViewToActor();
		generator = new RandomUnitGenerator(map);
		uManager = generator.getUnits();
		// reticule = new Reticule(MouseMonitor.getMousePosition(window));
	}

	int getWidth() {
		return winWidth;
	}

	int getHeight() {
		return winHeight;
	}

	/**
	 * This function will keep looping until we quit the game
	 */
	void runGame() {
		while (window.isOpen()) {
			// check for new window events that occurred since the last loop
			// iteration
			event = window.pollEvent();
			if (event != null) {
				// if a close requested event occurs, close the window
				if (event.type == Type.CLOSED) {
					window.close();
				}

				// check for input
				checkInput();

				// clear the window and prep for the new frame
				window.clear(backColor);

				// set the view
				window.setView(camera.getView());

				// draw the map
				displayMap();

				// draw the Actors
				uManager.draw(window);
				// reticule.draw(window);

				window.display();

				// checkMousePosition();
				// getMousePosition();

				if (Mouse.isButtonPressed(Mouse.Button.LEFT)) {
					selectClickedUnit(getMousePosition());
				}

			}
		}
	}

	@SuppressWarnings("unused")
	private void checkMousePosition() {
		if (Mouse.isButtonPressed(Mouse.Button.LEFT)) {
			Point a = MouseMonitor.getMousePosition(window);
			System.out.println(a.getX() + " " + a.getY());
		}
	}

	private Point getMousePosition() {
		Point p = MouseMonitor.getMousePosition(window);
		System.out.println("---" + p.getXTile() + " " + p.getYTile() + "---");
		return p;
	}

	private void selectClickedUnit(Point p) {

		// System.out.println("***" + p.getXTile() + " " + p.getYTile() +
		// "***");
		for (int i = 0; i < uManager.getLength(); i++) {

			if (uManager.getUnit(i).getPoint().getXTile() == p.getXTile()
					&& uManager.getUnit(i).getPoint().getYTile() == p
							.getYTile()) {
				selectID = i;
				System.out.println("TRUE");
				return;
			}
		}
		System.out.println("FALSE");
	}

	private void checkInput() {
		if (event.type == Type.KEY_PRESSED) {

			if (Keyboard.isKeyPressed(Key.W)) {
				// actor.updateY((float) -1);
				camera.update(0, -10);
				// System.out.println(camera.getX() + " " + camera.getY());
			}
			if (Keyboard.isKeyPressed(Key.S)) {
				// actor.updateY((float) 1);
				camera.update(0, 10);
				// System.out.println(camera.getX() + " " + camera.getY());
			}
			if (Keyboard.isKeyPressed(Key.A)) {
				// actor.updateX((float) -1);
				camera.update(-10, 0);
				// System.out.println(camera.getX() + " " + camera.getY());
			}
			if (Keyboard.isKeyPressed(Key.D)) {
				// actor.updateX((float) 1);
				camera.update(10, 0);
				// System.out.println(camera.getX() + " " + camera.getY());
			}
			if (Keyboard.isKeyPressed(Key.X)) {
				camera.zoom((float) 2);
			}
			if (Keyboard.isKeyPressed(Key.Z)) {
				camera.zoom((float) .5);
			}
			if (Keyboard.isKeyPressed(Key.C)) {
				setViewToActor();
			}
			if (Keyboard.isKeyPressed(Key.SPACE)) {
				selectID++;
				setViewToActor();
			}
		}
	}

	/**
	 * Method sets the play view using the camera class
	 */
	public void setViewToActor() {

		/*
		 * //set the view to the player's Actor //start by finding the center of
		 * the Actor float xCenter = actor.getWidth() / 2; float yCenter =
		 * actor.getHeight() / 2;
		 * 
		 * //now find the (x,y) position of the center of the sprite float xPos
		 * = actor.getX() + xCenter; float yPos = actor.getY() + yCenter;
		 * 
		 * camera = new Camera(xPos, yPos, (float) winWidth, (float) winHeight);
		 */
		camera = new Camera(0, 0, winWidth, winHeight);
	}

	/**
	 * Function displays the map
	 */
	public void displayMap() {
		map.draw(window);
	}

	public static void main(String args[]) throws IOException {
		Game game = new Game(800, 600, "WarGame!");
		game.runGame();
	}

}
