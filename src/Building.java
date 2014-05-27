import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class Building {
	private int _id;
	private Color _color; // placeholder for sprite
	
	public void draw(Graphics2D g2d,int x, int y) {
		Rectangle rect = new Rectangle(x,y,64,64);
		g2d.fill(rect);
	}
	
	public int getId(){
		return _id;
	}
	
	
	
		
	public static class Residential extends Building {
		public Residential() {
			
		}
	}
	public static class Industrial extends Building {
		public Industrial() {
			
		}
	}
	public static class Utilities extends Building {
		public Utilities() {
			
		}
	}
	public static class Commercial extends Building {
		public Commercial() {
			
		}
	}
	public static class Transit extends Building {
		public Transit() {
			
		}
	}
	public static class Defense extends Building {
		public static final int RANGE = 384;
		public static final int DAMAGE = 10;
		public static final int TYPE = 1;
		
		
		private boolean _isFiring;
		private boolean _showLaser; // determines whether the laser should be shown or not.
		
		
		private int _id;
		private int _cooldown;
		private Color _color;
		
		private Enemy _target;
		
		
		public Defense() {
			this(Color.BLUE);
		}
		
		public Defense(Color c) {
			_color = c;
			_id = 1;
			_cooldown = 0;
		}	
		
		public int getId() {
			return _id;
		}
		
		public void draw(Graphics2D g2d,int x, int y, int xoff, int yoff) {
			Rectangle rect = new Rectangle(x,y,64,64);
			g2d.setColor(_color);
			g2d.fill(rect);
			
			if (_showLaser) {
				g2d.setColor(Color.WHITE);
				g2d.drawLine(x+32, y+32, _target.getXcor() +xoff, _target.getYcor()+yoff);
			}
		}
		
		public void update() {
			if (_isFiring) {
				if (_cooldown == 0) {
					_cooldown = 20;
				} else {
					_cooldown--;
				}
				if (_cooldown > 16) {
					_showLaser = true;
				} else {
					_showLaser = false;
				}
				
			}
		}
		
		public Building target(Enemy e) {
			// draw a line or something...
			_target = e;
			_isFiring = true; // totally doesn't work
			return this;
		}
		
		public Enemy getTarget() {
			return _target;
		}
		
		public Building unTarget() {
			_target = null;
			_isFiring = false;
			_showLaser = false;
			return this;
		}
		
		public boolean getShowLaser() {
			return _showLaser;
		}
	}
} 
