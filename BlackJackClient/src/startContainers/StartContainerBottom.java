package startContainers;

import java.awt.Dimension;

import javax.swing.JLabel;

import mainPackage.PanelContainer;

public class StartContainerBottom extends PanelContainer{
	//fixes weird error
	private static final long serialVersionUID = 1L;
	
	public JLabel dealerTitle, cardLabel, backCardLabel;
	
	public StartContainerBottom() {
		initializeTopPanel();
		initializeMiddlePanel();
		initializeBottomPanel();
	}
	
	public void initializeTopPanel() {
		one.setPreferredSize(new Dimension(0, 100));
	}
	
	public void initializeMiddlePanel() {
		two.setPreferredSize(new Dimension(0, 100));
	}
	
	public void initializeBottomPanel() {
		three.setPreferredSize(new Dimension(0, 100));
	}
}
