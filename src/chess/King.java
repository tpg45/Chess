package chess;

public class King extends Piece{
	
	public King(int x, int y, char color) {
		super(x, y, color);
	}
	
	@Override
	public boolean canMove(int x, int y){
		boolean normalMove = (this.x!=x || this.y!=y) && (this.x-x<=1 && this.x-x>=-1) && (this.y-y<=1 && this.y-y>=-1);
		boolean castle = !hasMoved && y==0 && Math.abs(this.x-x)==2;
		return normalMove || castle;
	}
	
	@Override
	public String toString(){
		return color+"K";
	}
}
