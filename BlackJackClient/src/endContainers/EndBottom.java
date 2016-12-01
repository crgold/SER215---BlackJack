package endContainers;

import java.awt.Dimension;

import mainPackage.PanelContainer;

public class EndBottom extends PanelContainer {
	//fixes weird error
	private static final long serialVersionUID = 1L;

	public EndBottom() {
		initializeOne();
		initializeTwo();
		initializeThree();
	}
	
	public void initializeOne() {
		one.setPreferredSize(new Dimension(800, 0));
	}
	
	public void initializeTwo() {
		two.setPreferredSize(new Dimension(800, 0));
	}

	public void initializeThree() {
		three.setPreferredSize(new Dimension(800, 0));
	}
}
