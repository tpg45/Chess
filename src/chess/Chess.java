package chess;

import java.util.Scanner;

public class Chess {
	static Piece[][] board = new Piece[8][8];
	
	
	/**
	 * checks to see if a space is being threatened by an opposing piece
	 * <p>
	 * "Threatened" here means that an opposing piece can capture a piece 
	 * in the current player's possession on the next turn.
	 * @param x - 
	 * @param y - 
	 * @param color - 
	 * @return boolean, true if space is being threatened, false if not.
	 */
	public static boolean threatened(int x, int y, char color){
		for(int i = 0; i<=7; i++){
			for (int j = 0; j<=7; j++){
				if(board[i][j].canMove(x, y) && board[i][j].color == color)
					return true;
			}
		}
		return false;
	}
	
	
	public static boolean isLegal(String input){
		Piece cur = board[input.charAt(1)-48][input.charAt(0)-97];
		if(cur.canMove(input.charAt(3)-97, input.charAt(4)-48))
			return true;
		return false;
	}
	
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
		
		for(int i = 2;i<=5;i++){
			for(int j = 0;j<=7;j++){
				if(i%2==j%2)
					board[i][j] = new Piece(i,j,'b');
				else
					board[i][j] = new Piece(i,j,'w');
			}
		}
		
		for(int i = 0;i<=7;i++){
			board[6][i] = new Pawn(6,i,'b');
		}
		
		board[7][0] = new Rook(7,0,'b');
		board[7][1] = new Knight(7,1,'b');
		board[7][2] = new Bishop(7,2,'b');
		board[7][3] = new Queen(7,3,'b');
		board[7][4] = new King(7,4,'b');
		board[7][5] = new Bishop(7,5,'b');
		board[7][6] = new Knight(7,6,'b');
		board[7][7] = new Rook(7,7,'b');
	}
	
	
	public static void printBoard(){
		for(int i = 7;i>=0;i--){
			for(int j = 0;j<=7;j++){
				System.out.print(board[i][j].toString()+" ");
			}
			
			System.out.println(i+1);
		}
		System.out.println(" a  b  c  d  e  f  g  h\n");
	}
	
	
	public static void main(String[] args) {
		
		initBoard();
		
		boolean checkmate = false;
		boolean stalemate = false;
		boolean currentPlayer = true;		//true=white
		
		Scanner scanner = new Scanner(System.in);
		
		while(true){
			printBoard();
			
			if(currentPlayer)
				System.out.print("White's move: ");
			else
				System.out.print("Black's move: ");
			
			while(true){
				String input = scanner.next();
				if(isLegal(input))
					break;
			}
			
			System.out.println('\n');
			if(checkmate || stalemate)
				break;
			currentPlayer = !currentPlayer;
		}
		scanner.close();
		
		
	}
}
