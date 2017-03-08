package chess;

public class Queen extends Piece{

	public Queen(int x, int y, char color) {
		super(x, y, color);
	}
	
	@Override
	public boolean canMove(int x, int y){
		if(x<0 || x>7 || y<0 || y>7)
			return false;
		boolean horizontal = this.y==y;
		boolean vertical = this.x==x;
		boolean diagonal = (Math.abs(this.x-x)==Math.abs(this.y-y));
		boolean notBlocked = true;
		if(horizontal){
			for(int i = Math.min(this.x+1,x); i<=Math.max(this.x-1, x); i++){
				if(!Chess.board[y][i].isBlank()){
					if(i!=x || Chess.board[y][i].color==color){
						notBlocked=false;
						break;
					}
				}
			}
		}
		else if(vertical){
			for(int i = Math.min(this.y+1,y); i<=Math.max(this.y-1, y); i++){
				if(!Chess.board[i][x].isBlank()){
					if(i!=y || Chess.board[i][x].color==color){
						notBlocked=false;
						break;
					}
				}
			}
		}
		else if(diagonal){
			boolean UR_DL_Diagonal = (x>this.x && y>this.y) || (x<this.x && y<this.y);
			if(UR_DL_Diagonal){
				for(int i = Math.min(x, this.x+1), j = Math.min(y, this.y+1); i<=Math.max(x, this.x-1) && j<=Math.max(y, this.y-1); i++, j++){
					if(!Chess.board[j][i].isBlank()){
						if(i!=x || Chess.board[j][i].color==color){
							notBlocked=false;
							break;
						}
					}
				}
			}
			else{
				for(int i = Math.min(x, this.x+1), j = Math.max(y, this.y-1); i<=Math.max(x, this.x-1) && j<=Math.min(y, this.y+1); i++, j--){
					if(!Chess.board[j][i].isBlank()){
						if(i!=x || Chess.board[j][i].color==color){
							notBlocked=false;
							break;
						}
					}
				}
			}
		}
		else
			return false;
		return (this.x!=x || this.y!=y) && notBlocked;
	}
	
	@Override
	public String toString(){
		return color+"Q";
	}
}
