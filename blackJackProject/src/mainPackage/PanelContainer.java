package mainPackage;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

public class PanelContainer extends JPanel {
	//fixes weird error
	private static final long serialVersionUID = 1L;
	
	public JPanel one, two, three;
	
	public PanelContainer() {
		one = new JPanel();
		two = new JPanel();
		three = new JPanel();
		
		one.setBackground(new Color(10, 115, 10));
		two.setBackground(new Color(10, 115, 10));
		three.setBackground(new Color(10, 115, 10));
		
		setLayout(new BorderLayout());
		
		add(one, BorderLayout.NORTH);
		add(two, BorderLayout.CENTER);
		add(three, BorderLayout.SOUTH);
	}
}
