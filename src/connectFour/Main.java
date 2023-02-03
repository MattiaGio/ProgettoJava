package connectFour;

import java.awt.EventQueue;
import java.io.Serializable;


@SuppressWarnings("serial")
public class Main implements Serializable{
	/**
	 * starts the game
	 * @param args
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartingWindow window = new StartingWindow();
					window.frmConnect.setVisible(true);					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

}
