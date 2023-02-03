package connectFour;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Piece implements Serializable{

		private String color;

		public String getColor() {
			return color;
		}
		
		public void setColor(String color) {
			this.color = color;
		}
		
}
