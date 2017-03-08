package chess;

public class Rook extends Piece{

	public Rook(int x, int y, char color) {
		super(x, y, color);
	}
	
	@Override
	public boolean canMove(int x, int y){
		return (this.x!=x || this.y!=y) && (this.x==x || this.y==y);
	}
	
	@Override
	public String toString(){
		return color+"R";
	}
}
