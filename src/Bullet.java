import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;


public class Bullet extends Entity{
	private int _xcor;
	private int _ycor;
	private float _xvel;
	private float _yvel;
	
	
	public Bullet() {
		this(0,0,(float)5.0,(float)5.0);
	}
	
	public Bullet(int x, int y, float xv, float yv) {
		_xcor = x;
		_ycor = y;
		_xvel = xv;
		_yvel = yv;
	}
	
	public void draw(Graphics2D g2d, int xoff, int yoff) {
		g2d.setColor(Color.RED);
		Ellipse2D ball = new Ellipse2D.Float((float)_xcor - 8 + xoff, (float)_ycor - 8  + yoff, 16,16);
		
		
	}
}
