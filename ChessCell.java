
public class ChessCell {
	
	String piece = new String("");
	public ChessCell() {
		piece = "";
	}
	
	public ChessCell(String piece) {
		this.piece = piece;
	}
	
	public String getPiece() {
		return piece;
	}
	
	public void setPiece(String piece) {
		this.piece = piece;
	}
}
