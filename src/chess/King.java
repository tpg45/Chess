package chess;

public class King extends Piece{
	
	public King(int x, int y, char color) {
		super(x, y, color);
	}
	
	@Override
	public boolean canMove(int x, int y){
		if(x<0 || x>7 || y<0 || y>7)
			return false;
		boolean normalMove = (this.x!=x || this.y!=y) && (this.x-x<=1 && this.x-x>=-1) && (this.y-y<=1 && this.y-y>=-1);
		if(normalMove){
			boolean noBlocks = Chess.board[x][y].toString().charAt(0)!=color;
			return normalMove && noBlocks;
		}
		else{
			boolean castle = !hasMoved && (this.y==0 || this.y==7) && this.y==y && Math.abs(this.x-x)==2;
			boolean noBlocks = true;
			boolean rookNotMoved = false;
			//King's side castle
			if(x>this.x){
				for(int i = this.x; i<7; i++){
					if(Chess.board[i][y].toString().charAt(0)=='w' || Chess.board[i][y].toString().charAt(0)=='b'){
						noBlocks=false;
						break;
					}
				}
				if(!Chess.board[7][y].hasMoved)
					rookNotMoved=true;
			}
			//Queen's side castle
			else{
				for(int i = 1; i<this.x; i++){
					if(Chess.board[i][y].toString().charAt(0)=='w' || Chess.board[i][y].toString().charAt(0)=='b'){
						noBlocks=false;
						break;
					}
				}
				if(!Chess.board[0][y].hasMoved)
					rookNotMoved=true;
			}
			//fix later
			boolean notUnderAttack = true;
			return castle && rookNotMoved && noBlocks && notUnderAttack;
		}
	}
	
	@Override
	public String toString(){
		return color+"K";
	}
}
