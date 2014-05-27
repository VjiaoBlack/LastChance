import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;




public class Tile {
	private int _xcor, _ycor;
	private Building _building;
	private int _xoffset, _yoffset;
	private boolean _isHighlighted;
	private Color _highlight;
	
	public Tile() {
		this(0,0);
	}
	public Tile(int x, int y) {
		_xcor = x;
		_ycor = y;
		_xoffset = 0;
		_yoffset = 0;
		_highlight = new Color(255,255,255,20);
	}
	
	public int getXCor() {
		return _xcor;
	}
	public int getYCor() {
		return _ycor;
	}
	
	public Tile highlight(Color highlight) {
		_isHighlighted = true;
		_highlight = highlight;
		return this;
		
	}
	public Tile unHighlight() {
		_isHighlighted = false;
		return this;
	}
	
	public Tile build(Building bldn) {
		_building = bldn;
		return this;
	}
	public Building getBuilding() {
		return _building;
	}
	
	public boolean hasBuilding() {
		return _building != null;
	}
	public Tile destroy() {
		_building = null;
		return this;
	}
	
	public void update() {
		if (_building != null) {
			switch (_building.getId()) {
				case 1:
					((Building.Defense)_building).update();
			}
		}
	}
	
	
	
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.drawRect(_xcor * 64 + _xoffset, _ycor*64 + _yoffset, 64,64);
		
		if (_isHighlighted) {
			g2d.setColor(_highlight);
			g2d.fillRect(_xcor * 64 + _xoffset, _ycor*64 + _yoffset, 64,64);
		}
		
		if (_building != null) {
			switch (_building.getId()) {
				case 1:
					g2d.setColor(Color.BLUE);
					((Building.Defense)_building).draw(g2d, _xcor*64 +_xoffset, _ycor*64 + _yoffset, _xoffset, _yoffset);
					break;
				default:
					_building.draw(g2d, _xcor*64 +_xoffset, _ycor*64 + _yoffset);
					break;
			}
			
			switch(_building.getId()) {
				case 1:
					g2d.setColor(Color.WHITE);
					Ellipse2D arc = new Ellipse2D.Float(_xcor * 64 + _xoffset - 160, _ycor*64 + _yoffset - 160, 384, 384);
					g2d.draw(arc);
					break;
					
			}
		}
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
}
