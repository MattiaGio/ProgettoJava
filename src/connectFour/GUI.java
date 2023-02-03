package connectFour;

import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

import javax.swing.*;

@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener, java.io.Serializable{
	
	final int ROWS = 7;
	final int COLUMNS = 6;
	final int WINDOWWIDTH = 616;
	final int WINDOWHEIGHT = 760;

	
	private final String IMGEMPTYFILENAME = "images/empty.png";
	private final String IMGREDFILENAME = "images/red.jpeg";
	private final String IMGYELLOWFILENAME = "images/yellow.jpeg";
	private final String IMGBACKGROUND = "images/sfondo.jpeg";
	
	@SuppressWarnings("unused")
	private ImageIcon iconBackground = null;
	private ImageIcon iconEmpty = null;
	private ImageIcon iconRed = null;
	private ImageIcon iconYellow = null;
	
	private final String TITLE = "Connect Four - ";
	
	protected Container cp;
	
	protected JMenuItem aboutItem;
	protected JMenuItem loadItem;
	protected JMenuItem saveItem;
	protected JMenuItem exitItem;

	protected int row;
	protected int col;
	
	protected String player1 = StartingWindow.namePla1;
	
	protected String player2 = StartingWindow.namePla2;
	
	protected ConnectLogic game;
	
	protected Board board;
	
	protected JButton button;
	
	public GUI(){
		/**
		 * creates the game GUI
		 */
		game = new ConnectLogic(player1, player2);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.menu);
		menuBar.setForeground(new Color(255, 255, 255));
		JMenu help = new JMenu("Help");
		JMenu gameSettings = new JMenu("Game Settings");
		aboutItem = new JMenuItem("About");
		loadItem = new JMenuItem("Load");
		saveItem = new JMenuItem("Save");
		exitItem = new JMenuItem("Exit");
		
		menuBar.add(gameSettings);
		menuBar.add(help);
		gameSettings.add(loadItem);
		gameSettings.add(saveItem);
		gameSettings.add(exitItem);
		help.add(aboutItem);
		
		
		aboutItem.addActionListener(this);
		exitItem.addActionListener(this);
		saveItem.addActionListener(this);
		loadItem.addActionListener(this);
		
		
		setJMenuBar(menuBar);
		
		//setting up images
		URL imgURLIcon = getClass().getClassLoader().getResource(IMGBACKGROUND);
		if (imgURLIcon != null) {
			iconBackground = new ImageIcon(imgURLIcon);
		} else {
			System.err.println("Couldn't find file" + IMGBACKGROUND);
		}
		
		URL imgURL = getClass().getClassLoader().getResource(IMGEMPTYFILENAME);
		if (imgURL != null) {
			iconEmpty = new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file" + IMGEMPTYFILENAME);
		}
		//RED
		imgURL = getClass().getClassLoader().getResource(IMGREDFILENAME);
		if (imgURL != null) {
			iconRed = new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file" + IMGREDFILENAME);
		}
		//YELLOW
		imgURL = getClass().getClassLoader().getResource(IMGYELLOWFILENAME);
		if (imgURL != null) {
			iconYellow = new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file" + IMGYELLOWFILENAME);
		}
		
		cp = getContentPane();
		cp.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));

		//creating button array grid
		for (row = 0; row < ROWS; row ++) {
			for (col = 0; col < COLUMNS; col ++) {
				button = new JButton();
				button.setIcon(iconEmpty);
				button.setPreferredSize(new Dimension(100, 100));
				button.setName(Integer.toString(row * 10 + col));


				button.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						updateOnButton(((JButton)(e.getSource())));
						
						
					}
				});
				cp.add(button);
			}
		}

		
		
		
		setIconImage(new ImageIcon(imgURLIcon).getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		boolean player1turn = game.getPlayer1Turn();
		if(!player1turn) {
			setTitle(TITLE + player2 + ": Yellow");
		}else {
			setTitle(TITLE + player1 + ": Red");
		}


		setSize(WINDOWWIDTH, WINDOWHEIGHT);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		

		boolean playersTurn = game.getPlayer1Turn();
		if(!playersTurn) {

			setTitle(TITLE + player2+ ": Yellow");
		}else{
			setTitle(TITLE + player1+": Red");
		}
	}
	//updating image on button
	/**
	 * Updates the checkers on the button
	 * @param button
	 */
	private void updateOnButton(JButton button) {
		int row10plusCol = Integer.parseInt(button.getName());
		int col = row10plusCol % 10;
		String winnerPlayer;
		

		boolean playersTurn = game.getPlayer1Turn();
		if(playersTurn) {
			setTitle(TITLE + player2 + ": Yellow");
		}else{
			setTitle(TITLE + player1 + ": Red");
		}

		int addedRow = game.round(col);
		
		if(addedRow != -1) {
			

			JButton buttonToUpdate = ((JButton)(cp.getComponent(COLUMNS * addedRow + col)));
			
			if(game.getPlayer1Turn()) {
				buttonToUpdate.setIcon(iconYellow);
			}else {
				buttonToUpdate.setIcon(iconRed);
			}
			if(game.checkWinnerGUI(col)) {
				if(playersTurn == false) {
					winnerPlayer = player2;
				}else {
					winnerPlayer = player1;
				}
				int input = JOptionPane.showOptionDialog(null, winnerPlayer + " has won! Do you want to play again?", "GAME ENDED", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
				if(input == JOptionPane.OK_OPTION)
				{
					this.dispose();
					Main.main(null);
				}else {
					System.exit(0);
				}
			}
		}else {
			getToolkit().beep();
			JOptionPane.showMessageDialog(null, "Please enter a valid position", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
		
	



	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==saveItem) { 
			if (JOptionPane.showOptionDialog(this, "Click yes to save and close the game.\nPress no to return to the game", "Save Game", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null) == JOptionPane.YES_OPTION) {
				save();
				this.dispose();
			} else {
			
			}
		
		}
		
		if(e.getSource() == loadItem){
			loadGUI();
		}else {
			
		   }
		
		if(e.getSource()==aboutItem) {
			JOptionPane.showMessageDialog(null, "Connect4 Game: v.1.0 \nMade by: JvckDL & MattiaGio", "About", JOptionPane.INFORMATION_MESSAGE);
		}
    	if(e.getSource()==exitItem) {
			if (JOptionPane.showOptionDialog(this, "You will exit the game without saving\nDo you want to continue?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null) == JOptionPane.YES_OPTION) {
				this.dispose();
			} else {
			
			}	
    	}


	}
	
	public void save(){
		try {
			FileOutputStream fos = new FileOutputStream("GUI.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(GUI.this);
			oos.close();
			fos.close();
			System.out.println("Saved");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void loadGUI() {
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
	}
		
}
