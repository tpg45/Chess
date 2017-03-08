package chess;

public class Queen extends Piece{

	public Queen(int x, int y, char color) {
		super(x, y, color);
	}
	
	@Override
	public boolean canMove(int x, int y){
		return (this.x!=x || this.y!=y) &&
				(
					//straight lines
					(this.x==x || this.y==y) ||
					//diagonals
					(Math.abs(this.x-x)==Math.abs(this.y-y))
				);
	}
	
	@Override
	public String toString(){
		return color+"Q";
	}
}
