package chess;

public class King extends Piece{
	
	public King(int x, int y, char color) {
		super(x, y, color);
	}
	
	@Override
	public boolean canMove(int x, int y){
		return (this.x-x<=1 && this.x-x>=-1) && (this.y-y<=1 && this.y-y>=-1);
	}
	
	@Override
	public String toString(){
		return color+"K";
	}
}
