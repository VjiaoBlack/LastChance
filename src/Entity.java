import java.awt.Color;
import java.awt.geom.Ellipse2D;


public class Entity {
	private int _id;
	private Color _color;
	private Ellipse2D.Float _circle;
	
	public Entity() {
		
	}
	
	public Entity(int id, float x, float y) {
		_id = id;
		_color = Color.WHITE;
		_circle = new Ellipse2D.Float((float)x, (float)y, (float)4.0, (float)4.0);
	}
	
	public void update() {
		
	}
}
