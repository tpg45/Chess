package chess;

public class Bishop extends Piece{

	public Bishop(int x, int y, char color) {
		super(x, y, color);
	}
	
	@Override
	public boolean canMove(int x, int y){
		if(x<0 || x>7 || y<0 || y>7)
			return false;
		boolean notBlocked=true;
		boolean UR_DL_Diagonal = (x>this.x && y>this.y) || (x<this.x && y<this.y);
		if(UR_DL_Diagonal){
			for(int i = Math.min(x, this.x+1), j = Math.min(y, this.y+1); i<Math.max(x, this.x-1) && j<Math.max(y, this.y-1); i++, j++){
				if(!Chess.board[j][i].isBlank()){
					if(i!=x || Chess.board[j][i].color==color){
						notBlocked=false;
						break;
					}
				}
			}
		}
		else{
			for(int i = Math.min(x, this.x+1), j = Math.max(y, this.y-1); i<Math.max(x, this.x-1) && j<Math.min(y, this.y+1); i++, j--){
				if(!Chess.board[j][i].isBlank()){
					if(i!=x || Chess.board[j][i].color==color){
						notBlocked=false;
						break;
					}
				}
			}
		}
		return (this.x!=x || this.y!=y) && notBlocked;
	}
	
	@Override
	public String toString(){
		return color+"B";
	}
}
