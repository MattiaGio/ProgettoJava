package connectFour;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.SystemColor;


public class StartingWindow implements ActionListener{

	protected JFrame frmConnect;
	protected JTextField namePlayer1;
	protected JTextField namePlayer2;
	protected static String namePla1;
	protected static String namePla2;
	protected JButton startGameBtn;
	protected JButton loadGameBtn;
	protected JMenuItem aboutItem;
	protected ConnectLogic game;
	protected GUI myGUI;
	
	private final String IMGBACKGROUND = "images/sfondo.jpeg";
	
	private ImageIcon iconBackGround = null;
	
	/**
	 * Create the application.
	 */
	public StartingWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmConnect = new JFrame();
		frmConnect.setBackground(SystemColor.text);
		
		URL imgURL = getClass().getClassLoader().getResource(IMGBACKGROUND);
		if (imgURL != null) {
			iconBackGround = new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file" + IMGBACKGROUND);
		}
		frmConnect.setIconImage(new ImageIcon(imgURL).getImage());
		frmConnect.setTitle("Connect4");
		frmConnect.setBounds(100, 100, 560, 706);
		frmConnect.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmConnect.setResizable(false);
		frmConnect.getContentPane().setLayout(null);
		frmConnect.setLocationRelativeTo( null );
		frmConnect.setResizable(false);
		

		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		
		startGameBtn = new JButton("New Game");
		startGameBtn.setBounds(100, 489, 152, 45);
		panel.add(startGameBtn);
		startGameBtn.setFocusable(false);
		startGameBtn.addActionListener(this);
		
		loadGameBtn = new JButton("Load Game");
		loadGameBtn.setBounds(285, 489, 152, 45);
		panel.add(loadGameBtn);
		loadGameBtn.setFocusable(false);
		loadGameBtn.addActionListener(this);
		
		
		JLabel Player1Label = new JLabel("Player 1:");
		Player1Label.setBounds(173, 145, 59, 14);
		panel.add(Player1Label);
		Player1Label.setFont(new Font("Tahoma", Font.BOLD, 13));
		Player1Label.setForeground(new Color(255, 0, 0));
				
				
		JLabel Player2Label = new JLabel("Player 2:");
		Player2Label.setBounds(173, 193, 59, 14);
		panel.add(Player2Label);
		Player2Label.setForeground(new Color(255, 255, 0));
		Player2Label.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		namePlayer1 = new JTextField();
		namePlayer1.setBounds(242, 143, 139, 20);
		panel.add(namePlayer1);
		namePlayer1.setColumns(10);
		
		
		namePlayer2 = new JTextField();
		namePlayer2.setBounds(242, 191, 139, 20);
		panel.add(namePlayer2);
		namePlayer2.setColumns(10);		
		
		
		JLabel label = new JLabel();
		label.setIcon(iconBackGround);
		
		label.setBounds(0, 0, 544, 644);
		panel.add(label);
		panel.setBounds(0, 0, 544, 644);
		frmConnect.getContentPane().add(panel);
		
		
		//menu bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.menu);
		menuBar.setForeground(new Color(255, 255, 255));
		JMenu help = new JMenu("Help");
		aboutItem = new JMenuItem("About");
		
		menuBar.add(help);
		help.add(aboutItem);
		
		aboutItem.addActionListener(this);
		
		frmConnect.setJMenuBar(menuBar);
		
	}
	
	/**
	 * gets player 2 name
	 * @return the name of the second player
	 */
	public String getnamePla2() {
		return namePla2;
	}
	/**
	 * gets player 1 name
	 * @return the name the first player
	 */
	public String getnamePla1() {
		return namePla1;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==loadGameBtn) {
			myGUI = new GUI();
			try {
				FileInputStream fis = new FileInputStream("GUI.ser");
				ObjectInputStream ois = new ObjectInputStream(fis);
				GUI newGUI = (GUI) ois.readObject();
				ois.close();
				fis.close();
				newGUI.setVisible(true);
				//dispose();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (ClassNotFoundException c) {
				System.out.println("Class not found");
				c.printStackTrace();
			}
			namePla1 = namePlayer1.getText();
			
			namePla2 = namePlayer2.getText();
			frmConnect.dispose();
		}
		
		if(e.getSource()==aboutItem) {
			JOptionPane.showMessageDialog(null, "Connect4 Game: v.1.0 \nMade by: JvckDL & MattiaGio", "About", JOptionPane.INFORMATION_MESSAGE);
		}
		
		if(e.getSource() == startGameBtn) {
			if(namePlayer1.getText().isEmpty() || namePlayer2.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please enter both player's names", "Error", JOptionPane.ERROR_MESSAGE);
				}else {
					namePla1 = namePlayer1.getText();
					
					namePla2 = namePlayer2.getText();
					if(e.getSource()==startGameBtn) {
						myGUI = new GUI();
						frmConnect.dispose();
					}
				}
		}else {
			
			}
	}
	
	
}
