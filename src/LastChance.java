import java.io.IOException;
import javax.swing.JFrame;

public class LastChance {

	private static JFrame _frame;
	private static Menu _menu;

	public static void main(String[] args) {
		_frame = new JFrame();
		_menu = new Menu(); 
		
		_frame.setTitle("Last Chance");
		_frame.setSize(1080, 800);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setResizable(false);
		_frame.add(_menu);
		_frame.setVisible(true);
		_menu.run();
		_frame.setVisible(false);
		_frame.dispose();
		return;
	}

}
