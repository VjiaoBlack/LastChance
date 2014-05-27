import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.awt.RenderingHints;

public class Menu extends Canvas implements MouseListener, KeyListener,
		MouseMotionListener {
	public static final int MAX_FPS = 60;
	public static final int BUILD_TOWER = 1;
	private boolean _menu, _game;
	private Button _buttons[];
	private int _next;
	private boolean _skipIntro;
	private Board _board;

	private long lastTick;

	private HashMap<Integer, Boolean> _keys;

	private BufferStrategy _strategy;
	private Graphics2D _g2d;

	// game variables
	private LinkedList<Enemy> _enemies;

	private float _iron;
	private float _food;
	private int _cursor; // wtf ur doing.

	private int _oldxhighlight;
	private int _oldyhighlight;

	private int r, c;

	// end

	public Menu() {
		_menu = true;
		_game = false;

		_buttons = new Button[3];

		_buttons[0] = new Button(400, 350, 280, 100, "New Game", 1);
		_buttons[1] = new Button(400, 475, 280, 100, "Continue", 2);
		_buttons[2] = new Button(400, 600, 280, 100, "Exit", -1);

		_next = 0;

		_skipIntro = true;
		_board = new Board();

		// stuff with enemy
		_enemies = new LinkedList<Enemy>();
	}

	public void slideshow(Graphics2D g2d, BufferStrategy strategy, String disp,
			int x, int y) {
		g2d.drawString(disp, x, y);
		strategy.show();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void intro(Graphics2D g2d, BufferStrategy strategy) {
		g2d.setColor(Color.BLACK);
		g2d.fill(new Rectangle(0, 0, 1080, 800));
		g2d.setColor(Color.WHITE);

		slideshow(g2d, strategy, "Three days ago,", 100, 100);
		slideshow(g2d, strategy, "the Earth exploded,", 100, 200);
		slideshow(g2d, strategy, "and almost all of humanity", 100, 300);
		slideshow(g2d, strategy, "has perished.", 100, 400);
		try {
			Thread.sleep(500); // sleeps extra 500 ms after the last sentence.
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g2d.setColor(Color.BLACK);
		g2d.fill(new Rectangle(0, 0, 1080, 800));
		g2d.setColor(Color.WHITE);

		try {
			Thread.sleep(1000); // sleeps extra 500 ms after the last sentence.
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		slideshow(g2d, strategy, "However,", 100, 100);
		slideshow(g2d, strategy, "just 100 people have survived,", 100, 200);
		slideshow(g2d, strategy, "and now it is up to them", 100, 300);
		slideshow(g2d, strategy, "to rebuild humanity.", 100, 400);

		g2d.setColor(Color.BLACK);
		g2d.fill(new Rectangle(0, 0, 1080, 800));
		g2d.setColor(Color.WHITE);
		try {
			Thread.sleep(1000); // sleeps extra 500 ms after the last sentence.
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		slideshow(g2d, strategy, "As the leader of the survivors,", 100, 100);
		slideshow(g2d, strategy, "you must guide them", 100, 200);
		slideshow(g2d, strategy, "through any future problems.", 100, 300);
		slideshow(g2d, strategy, "It is truly the very Last Chance.", 100, 400);

		try {
			Thread.sleep(1000); // sleeps extra 500 ms after the last sentence.
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void tick() {
		long since = System.currentTimeMillis() - lastTick;
		if (since < 1000 / MAX_FPS) {
			try {
				Thread.sleep(1000 / MAX_FPS - since);
			} catch (InterruptedException e) {
				return;
			}
		}
		lastTick = System.currentTimeMillis();
	}

	public void run() {
		long nextTick = System.currentTimeMillis();
		long sleepTime = 0;
		_menu = true;

		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		createBufferStrategy(2);
		_strategy = getBufferStrategy();
		_g2d = (Graphics2D) _strategy.getDrawGraphics();
		_g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Font font = new Font("Serif", Font.PLAIN, 64);
		_g2d.setFont(font);

		while (_menu) {
			updateMenu();
			drawMenu(_g2d);
			_strategy.show();
			tick();
		}
	}

	public void updateMenu() {

		// System.out.println(_next);

		switch (_next) {
		case -1:
			_next = 0;
			_menu = false;
			break;
		case 1:
			_next = 0;
			runGame();
			break;
		case 2:
			// TODO: load game
			break;
		default:
			_next = 0;
			break;
		}
	}

	public void runGame() {
		_game = true;
		_iron = 100;
		_food = 100;
		_cursor = 0; // not doing nething
		while (_game) {
			updateGame();
			drawGame(_g2d);
			_strategy.show();
			tick();
		}
	}

	public void drawMenu(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.fill(new Rectangle2D.Float(0, 0, 1080, 800));

		for (Button button : _buttons) {
			button.draw(g2d);
		}
	}

	public void updateGame() {
		ArrayList<Tile> sigtiles = _board.update();

		// for (Enemy enemy : _enemies) {
		for (int i = _enemies.size() - 1; i >= 0; i--) {
			Enemy enemy = _enemies.get(i);
			enemy.update();

			// for each enemy, check to see if its in range with a building
			// (kinda legit? idk)
			for (Tile tile : sigtiles) { // all sigtiles have buildings
				switch (tile.getBuilding().getId()) {
				case 1:

					// if there is an enemy within range, target it
					if (dist((float) tile.getXCor() * 64,
							(float) tile.getYCor() * 64,
							(float) enemy.getXcor() - 32,
							(float) enemy.getYcor() - 32) < Building.Defense.RANGE / 2) {
						tile.build(((Building.Defense) tile.getBuilding())
								.target(enemy));
					} else if (((Building.Defense) tile.getBuilding())
							.getTarget() == enemy) {
						// if it was targeting this enemy but it's no longer
						// within range, disengage from that enemy.
						tile.build(((Building.Defense) tile.getBuilding())
								.unTarget());
					}

					// if the enemy is targeted by this defense, decrease
					// its hp.
					if (((Building.Defense) tile.getBuilding()).getTarget() == enemy) {
						if (((Building.Defense) tile.getBuilding())
								.getShowLaser()) {
							enemy.setHP(enemy.getHP() - 5); // 5 hp for each
															// frame of
															// pewpew laser.
						}
					}

					break;
				}
			}

		}

		// this unique backward moving iteration style gets rid of concurrent modification errors.
		for (int i = _enemies.size() - 1; i >= 0; i--) {
			Enemy enemy = _enemies.get(i);
			if (enemy.getHP() <= 0) {
				for (Tile tile : sigtiles) {
					if (((Building.Defense) tile.getBuilding()).getTarget() == enemy) {

						if (enemy.getHP() <= 0) {
							tile.build(((Building.Defense) tile
									.getBuilding()).unTarget());
						}

					}
				}
				_enemies.remove(enemy);
			}
			
			
			int x = enemy.getXcor();
			int y = enemy.getYcor();
			if (x < -200 || x > 25 * 64 || y < -200 || y > 25 * 64) 
				_enemies.remove(enemy);
		}
		
	}

	public float dist(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	public void drawGame(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.fill(new Rectangle(0, 0, 1080, 800));
		_board.draw(g2d);

		g2d.setColor(Color.WHITE);
		g2d.drawString("iron:" + (int) _iron, 30, 50);
		g2d.drawString("food:" + (int) _food, 30, 120);

		for (int i = _enemies.size() - 1; i >= 0; i--) {
			Enemy enemy = _enemies.get(i);
			enemy.draw(g2d, _board.getXOffset(), _board.getYOffset());
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		for (Button button : _buttons) {
			_next += button.update(e);
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (Button button : _buttons) {
			_next += button.update(e);
		}
		r = (e.getY() - _board.getYOffset()) / 64;
		c = (e.getX() - _board.getXOffset()) / 64;

		if (r >= 0 && c >= 0 && r < 20 && c < 20) {
			_board.highlight(r, c);

			if (_oldxhighlight != c || _oldyhighlight != r) {
				_board.unHighlight(_oldyhighlight, _oldxhighlight);
				_oldxhighlight = c;
				_oldyhighlight = r;
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		_board.updateCamera(e);
		switch (e.getKeyCode()) {
		case KeyEvent.VK_T:
			_cursor = BUILD_TOWER;
			_board.setHighlight(new Color(0, 0, 255, 80));
			break;
		case KeyEvent.VK_ESCAPE:
			_cursor = 0;
			_board.setHighlight(new Color(255, 255, 255, 20));
			break;
		case KeyEvent.VK_E:
			_enemies.add(new Enemy());
			break;

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		_board.updateCamera(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (Button button : _buttons) {
			_next += button.update(e);
		}

		switch (_cursor) {
		case 0:
			break;
		case BUILD_TOWER:
			Building.Defense tower = new Building.Defense();
			_board.build(r, c, tower);
			break;
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (Button button : _buttons) {
			_next += button.update(e);
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
