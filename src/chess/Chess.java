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
		//System.out.println(input.charAt(1)-49);
		//System.out.println(input.charAt(0)-97);
		Piece cur = board[input.charAt(1)-49][input.charAt(0)-97];					//correct?
		//System.out.println(cur.toString());
		if(cur.canMove(input.charAt(3)-97 , input.charAt(4)-49))					//HELLLLLPPPPPP
			return true;
		return false;
	}
	
	public static void move(Piece p1, Piece p2){
		System.out.println(p1.toString());
		System.out.println(p2.toString());
		if(p1 instanceof Pawn){
			board[p2.y][p2.x] = new Pawn(p2.x, p2.y, p1.color);
			board[p2.y][p2.x].hasMoved=true;
		}
		else if(p1 instanceof Rook){
			board[p2.y][p2.x] = new Rook(p2.x, p2.y, p1.color);
			board[p2.y][p2.x].hasMoved=true;
		}
		else if(p1 instanceof Knight){
			board[p2.y][p2.x] = new Knight(p2.x, p2.y, p1.color);
			board[p2.y][p2.x].hasMoved=true;
		}
		else if(p1 instanceof Bishop){
			board[p2.y][p2.x] = new Bishop(p2.x, p2.y, p1.color);
			board[p2.y][p2.x].hasMoved=true;
		}
		else if(p1 instanceof Queen){
			board[p2.y][p2.x] = new Queen(p2.x, p2.y, p1.color);
			board[p2.y][p2.x].hasMoved=true;
		}
		else if(p1 instanceof King){
			board[p2.y][p2.x] = new King(p2.x, p2.y, p1.color);
			board[p2.y][p2.x].hasMoved=true;
		}
		System.out.println(p1.x);
		System.out.println(p1.y);
		board[p1.y][p1.x] = (p1.x)%2==(p1.y)%2? new Piece(p1.x,p1.y,'b'):new Piece(p1.x,p1.y,'w');
		
	}
	
	
	public static void initBoard(){
		
		board[0][0] = new Rook(0,0,'w');
		board[0][1] = new Knight(1,0,'w');
		board[0][2] = new Bishop(2,0,'w');
		board[0][3] = new Queen(3,0,'w');
		board[0][4] = new King(4,0,'w');
		board[0][5] = new Bishop(5,0,'w');
		board[0][6] = new Knight(6,0,'w');
		board[0][7] = new Rook(7,0,'w');
		
		for(int i = 0;i<=7;i++){
			board[1][i] = new Pawn(i,1,'w');
		}
		
		for(int i = 0;i<=7;i++){
			for(int j = 2;j<=5;j++){
				if(i%2==j%2)
					board[j][i] = new Piece(i,j,'b');
				else
					board[j][i] = new Piece(i,j,'w');
			}
		}
		
		for(int i = 0;i<=7;i++){
			board[6][i] = new Pawn(i,6,'b');
		}
		
		board[7][0] = new Rook(0,7,'b');
		board[7][1] = new Knight(1,7,'b');
		board[7][2] = new Bishop(2,7,'b');
		board[7][3] = new Queen(3,7,'b');
		board[7][4] = new King(4,7,'b');
		board[7][5] = new Bishop(5,7,'b');
		board[7][6] = new Knight(6,7,'b');
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
		boolean drawRequested = false;
		
		Scanner scanner = new Scanner(System.in);
		
		while(true){
			printBoard();
			
			if(currentPlayer)
				System.out.print("White's move: ");
			else
				System.out.print("Black's move: ");
			
			String input;
			
			while(true){
				input = scanner.nextLine();
				if(input.equals("resign")){
					System.out.println((currentPlayer==false? "White":"Black") + " wins");
					return;
				}
				if(drawRequested && input.equals("draw")){
					System.out.println("Draw");
					return;
				}
				if(input.contains("draw?"))
					drawRequested = true;
				if(isLegal(input))
					break;
				else
					System.out.println("Illegal move, try again");
			}
			
			move(board[input.charAt(1)-49][input.charAt(0)-97], board[input.charAt(4)-49][input.charAt(3)-97]);
			
			if(checkmate || stalemate){
				break;
			}
			System.out.println('\n');
			
			currentPlayer = !currentPlayer;
		}
		scanner.close();
		
		
	}
}
