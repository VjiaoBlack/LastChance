import java.awt.Color; 
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Button{
	private Rectangle _rect;
	private String _name;
	private boolean _pressed;
	private boolean _hover;
	private int _id;

	public Button() {
		this(400, 400, 280, 200, "Button", 0);
	}

	public Button(int x1, int y1, int x2, int y2, String name, int id) {
		_rect = new Rectangle(x1,y1,x2,y2);
		_name = name;
		_id = id;
	}
	
	public int update(MouseEvent event) {
		int mask = (event.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK);
        boolean buttonDown = mask == MouseEvent.BUTTON1_DOWN_MASK;
        
        if (_pressed && !buttonDown) {
            _pressed = false;
            return _id;
        }

        if (event.getX() >= _rect.x && event.getX() <= _rect.x + _rect.width
         && event.getY() >= _rect.y && event.getY() <= _rect.y + _rect.height)
            _hover = true;
        else
            _hover = false;

        if (_hover && buttonDown)
            _pressed = true;
        else
            _pressed = false;
        
        return 0;
	}
	
	
	public void draw(Graphics2D g2d) {
		if (_pressed) {
			g2d.setColor(Color.RED);
		} else if (_hover) {
			g2d.setColor(Color.BLUE);
		} else {
			g2d.setColor(Color.WHITE);
		}
		g2d.fill(_rect);
		
		g2d.setColor(Color.BLACK);
		g2d.drawString(_name, _rect.x, _rect.y + 60);
	}


}
