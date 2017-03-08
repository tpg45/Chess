package chess;

public class Pawn extends Piece{

	public Pawn(int x, int y, char color) {
		super(x, y, color);
	}
	
	@Override
	public boolean canMove(int x, int y){
		if(x<0 || x>7 || y<0 || y>7)
			return false;
		if((color=='w' && y<this.y) || (color=='b' && y>this.y))
			return false;
		Piece target = Chess.board[y][x];
		boolean normalForward = this.x==x && Math.abs(this.y-y)==1 && target.isBlank();
		boolean doubleForward = this.x==x && !hasMoved && Math.abs(this.y-y)==2 && target.isBlank();
		boolean simpleCapture = Math.abs(this.x-x)==1 && Math.abs(this.y-y)==1 && !target.isBlank() && target.color!=color;
		boolean enPassant = false;
		return normalForward || doubleForward || simpleCapture || enPassant;
	}
	
	@Override
	public String toString(){
		return color+"p";
	}

}
