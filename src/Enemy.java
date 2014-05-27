import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;


public class Enemy extends Entity {
	public static final int ENEMY_RANGE = 512;
	private int _xcor;
	private int _ycor;
	private int _xvel;
	private int _yvel;
	private int _hp;
	private int _bulletcooldown;
	
	private LinkedList<Bullet> _bullets;
	
	public Enemy() {
		_bullets = new LinkedList<Bullet>();
		
		// board is a 20x20 grid, I believe
		
		if (Math.random() > .5) {
			if (Math.random() > .5) { // top
				_ycor = -64;
				_xcor = (int) Math.random() * 20*64;
			} else { // bottom
				_ycor = 21*64;
				_xcor = (int) Math.random() * 20*64;
			}
		} else {
			if (Math.random() > .5) { // left
				_xcor = -64;
				_ycor = (int) Math.random() * 20*64;
			} else { // right
				_xcor = -64;
				_ycor = (int) Math.random() * 20*64;
			}
		}
		
		
		System.out.println(_xcor + " ; " + _ycor);

		_hp = 100;
		
		
		
		// center is at 540, 400
		
		_xvel = 0 - (int) ((_xcor - 540 + Math.random() * 200 - 100) / 200);
		
		_yvel =  0 - (int) ((_ycor - 400 + Math.random() * 200 - 100) / 200);
		
		_bulletcooldown = 0;
	}
	
	public void update() {
		if (_bulletcooldown == 0) {
			if (true) { // if you can find a target
				// spawn bullet... in bullet linked list???
				_bullets.add(new Bullet(_xcor, _ycor,(float)1, (float)1));
				_bulletcooldown = 90;
			}
		}
		_bulletcooldown--;
		
		_xcor += _xvel;
		_ycor += _yvel;
		
		for (Bullet bullet : _bullets) {
			bullet.update();
		}
	}
	
	public int getHP() {
		return _hp;
	}
	public int setHP(int hp) {
		int hold = _hp;
		_hp = hp;
		return hold;
	}
	
	public int getXcor() {
		return _xcor;
	}
	public int getYcor() {
		return _ycor;
	}
	
	public void draw(Graphics2D g2d, int xoff, int yoff) {
		Ellipse2D body = new Ellipse2D.Float(_xcor - 32 +xoff, _ycor - 32+yoff, 64, 64);
		g2d.setColor(Color.YELLOW);
		g2d.fill(body);
		
		
		
		for (Bullet bullet : _bullets) {
			bullet.draw(g2d,xoff,yoff);
		}
	}
}
