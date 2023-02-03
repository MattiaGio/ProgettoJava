package connectFour;



@SuppressWarnings("serial")
public class ConnectLogic implements java.io.Serializable {
	public final String COLOR1 = "R";
	public final String COLOR2 = "Y";
	public Board board;
	public String player1;
	public String player2;
	
	
	
	private boolean player1Turn = true;
	
	public ConnectLogic(String player1, String player2) {
		this.board = new Board();
		this.player1 = player1;
		this.player2 = player2;
		
	}
	
	public void reset() {
		this.board = new Board();
	}
	
	/**
	 * gets if it is player 1 turn
	 * @return if it is player 1 turn
	 */
	public boolean getPlayer1Turn(){
		return player1Turn;
	}
	/**
	 * adds color in a certain column and row
	 * @param col
	 * @return the row where the checker was added
	 */
	public int round(int col) {
		int row = -1;
		
		String color;
		
		if(player1Turn) {
			color = COLOR1;
		} else {
			color = COLOR2;
		}
		
		row = board.addPiece(col, color);
		
		if(row != -1) {
			player1Turn = !player1Turn;
		}
		return row;
	}
/**
 * check if someone won in GUI
 * @param column
 * @return if someone won in the logic
 */
	public boolean checkWinnerGUI(int column) {
		String winningColor;
		
		if(!player1Turn) {
			winningColor = COLOR1;
		}else {
			winningColor = COLOR2;
		}
		return board.checkForWinner(column, winningColor);
	}
	
}
