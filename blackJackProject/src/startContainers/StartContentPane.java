package startContainers;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class StartContentPane extends JPanel {
		//fixes weird error
		private static final long serialVersionUID = 1L;
		
		public StartContainerTop panelContainerOne;
		public StartContainerMiddle panelContainerTwo;
		public StartContainerBottom panelContainerThree;
		
		public StartContentPane() {
			setUpPanels();
		}
		
		public void setUpPanels() {
			panelContainerOne = new StartContainerTop();
			panelContainerTwo = new StartContainerMiddle();
			panelContainerThree = new StartContainerBottom();
		
			setLayout(new BorderLayout());
		
			add(panelContainerOne, BorderLayout.NORTH);
			add(panelContainerTwo, BorderLayout.CENTER);
			add(panelContainerThree, BorderLayout.SOUTH);
		}
	}

