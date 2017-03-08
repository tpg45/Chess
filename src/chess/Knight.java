package chess;

public class Knight extends Piece{

	public Knight(int x, int y, char color) {
		super(x, y, color);
	}
	
	@Override
	public boolean canMove(int x, int y){
		return (Math.abs(this.x-x)==2 || Math.abs(this.y-y)==2) && (Math.abs(this.x-x)==1 || Math.abs(this.y-y)==1);
	}
	
	@Override
	public String toString(){
		return color+"N";
	}
}
