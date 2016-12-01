package startContainers;

import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.JPanel;

public class StartContentPane extends JPanel {
		//fixes weird error
		private static final long serialVersionUID = 1L;
		
		public StartContainerTop panelContainerOne;
		public StartContainerMiddle panelContainerTwo;
		public StartContainerBottom panelContainerThree;
		
		public StartContentPane(DataOutputStream toServer, DataInputStream fromServer, Socket clientSocket) {
			setUpPanels(toServer, fromServer, clientSocket);
		}
		
		public void setUpPanels(DataOutputStream toServer, DataInputStream fromServer, Socket clientSocket) {
			panelContainerOne = new StartContainerTop();
			panelContainerTwo = new StartContainerMiddle(toServer, fromServer, clientSocket);
			panelContainerThree = new StartContainerBottom();
		
			setLayout(new BorderLayout());
		
			add(panelContainerOne, BorderLayout.NORTH);
			add(panelContainerTwo, BorderLayout.CENTER);
			add(panelContainerThree, BorderLayout.SOUTH);
		}
	}

