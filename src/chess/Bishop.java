package chess;

public class Bishop extends Piece{

	public Bishop(int x, int y, char color) {
		super(x, y, color);
	}
	
	@Override
	public boolean canMove(int x, int y){
		return (this.x!=x || this.y!=y) && (Math.abs(this.x-x)==Math.abs(this.y-y));
	}
	
	@Override
	public String toString(){
		return color+"B";
	}
}
