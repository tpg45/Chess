package chess;

public class Pawn extends Piece{

	public Pawn(int x, int y, char color) {
		super(x, y, color);
	}
	
	@Override
	public boolean canMove(int x, int y){
		return this.x==x && (Math.abs(this.y-y)==1 || (!hasMoved && Math.abs(this.y-y)==2));
	}
	
	@Override
	public String toString(){
		return color+"p";
	}

}
