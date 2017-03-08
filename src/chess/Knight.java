package chess;

public class Knight extends Piece{

	public Knight(int x, int y, char color) {
		super(x, y, color);
	}
	
	@Override
	public boolean canMove(int x, int y){
		if(x<0 || x>7 || y<0 || y>7)
			return false;
		boolean onPath = (Math.abs(this.x-x)==2 || Math.abs(this.y-y)==2) && (Math.abs(this.x-x)==1 || Math.abs(this.y-y)==1);
		Piece target = Chess.board[y][x];
		boolean notBlocked = target.isBlank() || target.color!=color;
		return onPath && notBlocked;
	}
	
	@Override
	public String toString(){
		return color+"N";
	}
}
