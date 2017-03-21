package chess;

import java.util.Scanner;

/**
 * 
 * @author Tim Gassaway, Nick Prezioso
 *
 */
public class Chess {
	static Piece[][] board = new Piece[8][8];
	static int turnCounter = 0;
	
	/**
	 * Checks to see if a space is being threatened by an opposing piece.
	 * <p>
	 * "Threatened" here means that an opposing piece can capture a piece 
	 * at this location in the current player's possession on the next turn.
	 * @param x - x-coordinate of space to be checked.
	 * @param y - y-coordinate of space to be checked.
	 * @param color - color of the opposing pieces that can threaten the space.
	 * @return True if space is being threatened, false if not.
	 */
	public static boolean threatened(int x, int y, char color){
		for(int i = 0; i<=7; i++){
			for (int j = 0; j<=7; j++){
				Piece test = board[i][j];
				if(test.color != color){
					continue;
				}
				else if(test instanceof King){
					if(Math.abs(test.x-x)<=1 && Math.abs(test.y-y)<=1)
						return true;
				}
				else if(test.canMove(x, y))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks to see if the input move is legal.
	 * <p>
	 * Calls canMove on the Piece object located at the position indicated.
	 * @param input - input buffer from user (contains the move information).
	 * @return if move is legal, returns true.
	 */
	public static boolean isLegal(String input, boolean player){
		Piece cur = board[input.charAt(1)-49][input.charAt(0)-97];
		if((player && cur.color == 'b') || (!player && cur.color == 'w'))
			return false;
		if(cur.canMove(input.charAt(3)-97 , input.charAt(4)-49))
			return true;
		return false;
	}
	
	public static Piece[][] cloneBoard(Piece[][] board){
		Piece[][] clone = new Piece[8][8];
		for(int i=0;i<=7;i++){
			for (int j=0;j<=7;j++){
				if(board[i][j] instanceof Pawn){
					clone[i][j] = new Pawn(j, i, board[i][j].color);
					((Pawn)clone[i][j]).lastMovedTurn = ((Pawn)board[i][j]).lastMovedTurn;
					((Pawn)clone[i][j]).lastMoveWasDouble = ((Pawn)board[i][j]).lastMoveWasDouble;
				}
				else if(board[i][j] instanceof Rook)
					clone[i][j] = new Rook(j, i, board[i][j].color);
				else if(board[i][j] instanceof Knight)
					clone[i][j] = new Knight(j, i, board[i][j].color);
				else if(board[i][j] instanceof Bishop)
					clone[i][j] = new Bishop(j, i, board[i][j].color);
				else if(board[i][j] instanceof Queen)
					clone[i][j] = new Queen(j, i, board[i][j].color);
				else if(board[i][j] instanceof King)
					clone[i][j] = new King(j, i, board[i][j].color);
				else
					clone[i][j] = new Piece(j, i, board[i][j].color);
			}
		}
		return clone;
	}
	
	public static boolean isCheckmate(boolean player, Piece[][] currentBoard){
		Piece[][] testBoard = cloneBoard(currentBoard);
		for (Piece[] row : testBoard){
			for (Piece p : row){
				for (int i=0;i<=7;i++){
					for (int j=0;j<=7;j++){
						if(p.canMove(j, i)){
							move(p, testBoard[j][i]);
							if(!isCheck(player, testBoard))
								return false;
						}
						testBoard = cloneBoard(currentBoard);
					}
				}
			}
		}
		
		return true;
	}
	
	public static boolean isCheck(boolean player, Piece[][] currentBoard){
		char color = 'b';
		if(player)
			color = 'w';

		for (Piece[] row : currentBoard){
			for (Piece p : row){
				if(p.color != color && p instanceof King){
					return ((King) p).isChecked();
				}
			}
		}
		return false;
	}
	
	/**
	 * Moves a piece according to user input, assumes legality.
	 * @param p1 - Piece to be moved.
	 * @param p2 - Piece being moved to.
	 */
	public static void move(Piece p1, Piece p2){
		if(p1 instanceof Pawn){
			boolean isEnPassant = ((Pawn) p1).isLegalEnPassant(p2.x, p2.y);
			int oldY = p1.y;
			board[p2.y][p2.x] = new Pawn(p2.x, p2.y, p1.color);
			((Pawn)board[p2.y][p2.x]).lastMovedTurn = turnCounter;
			((Pawn)board[p2.y][p2.x]).lastMoveWasDouble = Math.abs(p1.y-p2.y)==2 && p1.x==p2.x;
			if(isEnPassant){
				char color = oldY%2 == p2.x%2 ? 'b':'w';
				board[oldY][p2.x] = new Piece(oldY, p2.x, color);
			}
		}
		else if(p1 instanceof Rook)
			board[p2.y][p2.x] = new Rook(p2.x, p2.y, p1.color);
		else if(p1 instanceof Knight)
			board[p2.y][p2.x] = new Knight(p2.x, p2.y, p1.color);
		else if(p1 instanceof Bishop)
			board[p2.y][p2.x] = new Bishop(p2.x, p2.y, p1.color);
		else if(p1 instanceof Queen)
			board[p2.y][p2.x] = new Queen(p2.x, p2.y, p1.color);
		else if(p1 instanceof King){
			boolean castle = (p1.y==0 || p1.y==7) && p1.y==p2.y && Math.abs(p1.x-p2.x)==2;
			if(castle){
				boolean queenSide = p2.x<p1.x;
				if(queenSide){
					move(board[p1.y][0], board[p1.y][p1.x-1]);
				}
				else{
					move(board[p1.y][7], board[p1.y][p1.x+1]);
				}
			}
			board[p2.y][p2.x] = new King(p2.x, p2.y, p1.color);
		}
		board[p2.y][p2.x].hasMoved=true;
		board[p1.y][p1.x] = (p1.x)%2==(p1.y)%2? new Piece(p1.x,p1.y,'b'):new Piece(p1.x,p1.y,'w');
		turnCounter++;
	}
	
	/**
	 * Initializes the board state to the start of a new game.
	 */
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
	
	/**
	 * Prints the current state of the board in ascii art.
	 */
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
		
		boolean check = false;
		boolean checkmate = false;
		boolean stalemate = false;
		boolean currentPlayer = true;		//true=white
		boolean drawRequested = false;
		
		Scanner scanner = new Scanner(System.in);
		
		while(true){
			printBoard();
			
			if(check)
				System.out.println("Check");
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
				if(isLegal(input,currentPlayer))
					break;
				else{
					System.out.println("Illegal move, try again");
					if(currentPlayer)
						System.out.print("White's move: ");
					else
						System.out.print("Black's move: ");
				}
				
			}
			
			move(board[input.charAt(1)-49][input.charAt(0)-97], board[input.charAt(4)-49][input.charAt(3)-97]);
			
			check = isCheck(currentPlayer, board);
			//checkmate = isCheckmate(currentPlayer, board);
			/*if(checkmate){
				System.out.println(currentPlayer+" win's");
			}*/
			if(checkmate || stalemate){
				System.out.println("Checkmate");
				break;
			}
			System.out.println('\n');
			
			currentPlayer = !currentPlayer;
		}
		scanner.close();
		
		
	}
}
