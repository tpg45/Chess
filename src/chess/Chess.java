package chess;

public class Chess {
	static Piece[][] board = new Piece[8][8];
	
	public static void initBoard(){
		
		board[0][0] = new Rook(0,0,'w');
		board[0][1] = new Knight(0,1,'w');
		board[0][2] = new Bishop(0,2,'w');
		board[0][3] = new Queen(0,3,'w');
		board[0][4] = new King(0,4,'w');
		board[0][5] = new Bishop(0,5,'w');
		board[0][6] = new Knight(0,6,'w');
		board[0][7] = new Rook(0,7,'w');
		
		for(int i = 0;i<=7;i++){
			board[1][i] = new Pawn(1,i,'w');
		}
		
		for(int i = 0;i<=7;i++){
			for(int j = 2;j<=5;j++){
				if(i%2==j%2)
					board[i][j] = new Piece(i,j,'b');
				else
					board[i][j] = new Piece(i,j,'w');
			}
		}
		
		for(int i = 0;i<=7;i++){
			board[6][i] = new Pawn(6,i,'b');
		}
		
		board[7][0] = new Rook(7,0,'w');
		board[7][1] = new Knight(7,1,'w');
		board[7][2] = new Bishop(7,2,'w');
		board[7][3] = new Queen(7,3,'w');
		board[7][4] = new King(7,4,'w');
		board[7][5] = new Bishop(7,5,'w');
		board[7][6] = new Knight(7,6,'w');
		board[7][7] = new Rook(7,7,'w');
	}
	
	public static void main(String[] args) {
		
		initBoard();
		
		
		
		return;
	}
}
