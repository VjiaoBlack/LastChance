import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;



public class Board {
	public static final int COLS = 20;
	public static final int ROWS = 20;
	
	private int _xoffsetvel;
	private int _yoffsetvel;
	private int _xoffset;
	private int _yoffset;
	
	private Color _highlight;
	
	private ArrayList<Tile> _tiles; // r * COLS + c

	
	public Board () {
		_tiles = new ArrayList<Tile>();
		for (int c = 0; c < 20; c++) {
			for (int r = 0; r < 20; r++) {
//				System.out.println(r*COLS + c);
				_tiles.add(new Tile(c,r));
			}
		}
		_highlight = new Color(255,255,255,20);
	}
	
	public void highlight(int r, int c) {
		setTile(r,c,getTile(r,c).highlight(_highlight));
	}
	public void unHighlight(int r, int c) {
		setTile(r,c,getTile(r,c).unHighlight());
	}
	public void build(int r, int c, Building b) {
		setTile(r,c,getTile(r,c).build(b));
	}
	public void destroy(int r, int c) {
		setTile(r,c,getTile(r,c).destroy());
	}
	
	
	
	public void setHighlight(Color c) {
		_highlight = c;
	}
	
	public Tile getTile(int r, int c) {
		return _tiles.get(c * COLS + r);
		
	}
	
	public Tile setTile(int r, int c, Tile tile) {
		Tile holder = _tiles.get(c*COLS + r);
		_tiles.set(c*COLS + r,tile);
		return holder;
	}
	
	public int getXOffset() {
		return _xoffset;
	}
	public int setXOffset(int x) {
		int hold = _xoffset;
		_xoffset = x;
		return hold;
	}
	public int getYOffset() {
		return _yoffset;
	}
	public int setYOffset(int y) {
		int hold = _yoffset;
		_yoffset = y;
		return hold;
	}
	
	public ArrayList<Tile> update() { // somehow make this return an arraylist of all the significant tiles. TODO
		boolean changedx, changedy;
		
		ArrayList<Tile> sigtiles = new ArrayList<Tile>();
		
		_xoffset += _xoffsetvel;
		_yoffset += _yoffsetvel;
		
		changedy = (_yoffsetvel != 0);
		changedx = (_xoffsetvel != 0);
		
		for (Tile tile: _tiles) {
			tile.update();
			if (changedx) {
				tile.setXOffset(_xoffset+_xoffsetvel);
			}
			if (changedy) {
				tile.setYOffset(_yoffset+_yoffsetvel);
				
			}
			if (tile.hasBuilding()) {
				sigtiles.add(tile);
			}
		}
		return sigtiles;
		
	}
	
	public void updateCamera(KeyEvent e) {

		if (e.getID() == KeyEvent.KEY_PRESSED) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_W:
					_yoffsetvel = 2;
					break;
				case KeyEvent.VK_A:
					_xoffsetvel = 2;
					break;
				case KeyEvent.VK_S:
					_yoffsetvel = -2;
					break;
				case KeyEvent.VK_D:
					_xoffsetvel = -2;
					break;
			}
		}
		
		if (e.getID() == KeyEvent.KEY_RELEASED) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_W:
					if (_yoffsetvel > 0) 
						_yoffsetvel = 0;
					break;
				case KeyEvent.VK_A:
					if (_xoffsetvel > 0)
						_xoffsetvel = 0;
					break;
				case KeyEvent.VK_S:
					if (_yoffsetvel < 0)
						_yoffsetvel = 0;
					break;
				case KeyEvent.VK_D:
					if (_xoffsetvel < 0)
						_xoffsetvel = 0;
					break;
			}
		}
		
	}
	
	public void draw(Graphics2D g2d) {
		for (Tile tile: _tiles) {
			tile.draw(g2d);
		}
	}
}
