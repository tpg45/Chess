package chess;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Tim Gassaway, Nick Prezioso
 *
 */
public class Chess {
	static Piece[][] board = new Piece[8][8];
	static int turnCounter = 0;
	static String input;
	
	/**
	 * Checks to see if a space is being threatened by an opposing piece.
	 * <p>
	 * "Threatened" here means that an opposing piece can capture a piece 
	 * at this location in the current player's possession on the next turn.
	 * @param x - x-coordinate of space to be checked.
	 * @param y - y-coordinate of space to be checked.
	 * @param color - color of the opposing pieces that can threaten the space.
	 * @return true if space is being threatened, false if not.
	 */
	public static boolean threatened(int x, int y, char color){
		for(int i = 0; i<=7; i++){
			for (int j = 0; j<=7; j++){
				Piece test = board[i][j];
				if(!board[y][x].isBlank() && test.color != color){
					continue;
				}
				else if(test instanceof King){
					if(test.color==color && Math.abs(test.x-x)<=1 && Math.abs(test.y-y)<=1)
						return true;
				}
				else if(test.color==color && test.canMove(x, y)){
					if(test instanceof Pawn){
						if(((Pawn) test).x == x){
							continue;
						}
					}
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks to see if a space is being threatened by an opposing piece that isn't a king.
	 * <p>
	 * "Threatened" here means that an opposing piece can capture a piece 
	 * at this location in the current player's possession on the next turn.
	 * @param x - x-coordinate of space to be checked.
	 * @param y - y-coordinate of space to be checked.
	 * @param color - color of the opposing pieces that can threaten the space.
	 * @return true if space is being threatened, false if not.
	 */
	public static boolean threatenedByNonKings(int x, int y, char color){
		for(int i = 0; i<=7; i++){
			for (int j = 0; j<=7; j++){
				Piece test = board[i][j];
				if(!board[y][x].isBlank() && test.color != color){
					continue;
				}
				else if(test instanceof King){
					continue;
				}
				else if(test.color==color && test.canMove(x, y))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks to see if the input move is legal.
	 * <p>
	 * Calls canMove on the Piece object located at the position indicated.
	 * @param player - current player
	 * @return if move is legal, returns true.
	 */
	public static boolean isLegal(boolean player){
		if(input.equals("draw"))
			return false;
		Piece cur = board[input.charAt(1)-49][input.charAt(0)-97];
		if((player && cur.color == 'b') || (!player && cur.color == 'w'))
			return false;
		if(cur.canMove(input.charAt(3)-97 , input.charAt(4)-49))
			return true;
		return false;
	}
	
	/**
	 * Checks if a move by the player put the opponent in checkmate
	 * @param player - the player who moved the piece in question
	 * @return whether the player has put the opponent in checkmate
	 */
	public static boolean isCheckmate(boolean player){
		if(!isCheck(player))
			return false;
		//king cannot move
		char enemyColor = player ? 'w':'b';
		char color = player ? 'b':'w' ;
		Piece k = new Piece(0, 0, 'w');	//temporary value
		boolean b = false;
		for (Piece[] row : board){
			for (Piece p : row){
				if(p.color == color && p instanceof King){
					k = (King)p;
					boolean canMove =
							k.canMove(k.x, k.y+1)	||
							k.canMove(k.x+1, k.y)	||
							k.canMove(k.x+1, k.y+1)	||
							k.canMove(k.x, k.y-1)	||
							k.canMove(k.x-1, k.y)	||
							k.canMove(k.x-1, k.y-1)	||
							k.canMove(k.x-1, k.y+1)	||
							k.canMove(k.x+1, k.y-1);
					if(canMove){
						return false;
					}
					b=true;
					break;
				}
			}
			if(b)
				break;
		}
		//cannot capture or block
		//get threatening piece
		b = false;
		for (Piece[] row : board){
			for (Piece p : row){
				if(p.color == enemyColor && p.canMove(k.x, k.y)){
					//get all spaces through which it threatens king (including its own space)
					ArrayList<Integer> xList = new ArrayList<Integer>();
					ArrayList<Integer> yList = new ArrayList<Integer>();
					if(p instanceof Queen){
						//cardinal
						if((p.x!=k.x && p.y==k.y) || (p.x==k.x && p.y!=k.y)){
							//horizontal
							if(p.x!=k.x){
								//on left
								if(p.x<k.x){
									for(int i=p.x; i<k.x; i++){
										xList.add(i);
										yList.add(p.y);
									}
								}
								//on right
								else{
									for(int i=p.x; i>k.x; i--){
										xList.add(i);
										yList.add(p.y);
									}
								}
							}
							//vertical
							else{
								//below
								if(p.y<k.y){
									for(int i=p.y; i<k.y; i++){
										xList.add(p.x);
										yList.add(i);
									}
								}
								//above
								else{
									for(int i=p.y; i>k.y; i--){
										xList.add(p.x);
										yList.add(i);
									}
								}
							}
						}
						//diagonal
						else{
							//above on left
							if(p.x<k.x && p.y>k.y){
								for(int x=p.x, y=p.y; x<k.x && y>k.y; ){
									xList.add(x);
									yList.add(y);
									x++;
									y--;
								}
							}
							//above on right
							else if(p.x>k.x && p.y>k.y){
								for(int x=p.x, y=p.y; x>k.x && y>k.y; ){
									xList.add(x);
									yList.add(y);
									x--;
									y--;
								}
							}
							//below on left
							else if(p.x<k.x && p.y<k.y){
								for(int x=p.x, y=p.y; x<k.x && y<k.y; ){
									xList.add(x);
									yList.add(y);
									x++;
									y++;
								}
							}
							//below on right
							else{
								for(int x=p.x, y=p.y; x>k.x && y<k.y; ){
									xList.add(x);
									yList.add(y);
									x--;
									y++;
								}
							}
						}
					}
					else if(p instanceof Bishop){
						//above on left
						if(p.x<k.x && p.y>k.y){
							for(int x=p.x, y=p.y; x<k.x && y>k.y; ){
								xList.add(x);
								yList.add(y);
								x++;
								y--;
							}
						}
						//above on right
						else if(p.x>k.x && p.y>k.y){
							for(int x=p.x, y=p.y; x>k.x && y>k.y; ){
								xList.add(x);
								yList.add(y);
								x--;
								y--;
							}
						}
						//below on left
						else if(p.x<k.x && p.y<k.y){
							for(int x=p.x, y=p.y; x<k.x && y<k.y; ){
								xList.add(x);
								yList.add(y);
								x++;
								y++;
							}
						}
						//below on right
						else{
							for(int x=p.x, y=p.y; x>k.x && y<k.y; ){
								xList.add(x);
								yList.add(y);
								x--;
								y++;
							}
						}
					}
					else if(p instanceof Rook){
						//horizontal
						if(p.x!=k.x){
							//on left
							if(p.x<k.x){
								for(int i=p.x; i<k.x; i++){
									xList.add(i);
									yList.add(p.y);
								}
							}
							//on right
							else{
								for(int i=p.x; i>k.x; i--){
									xList.add(i);
									yList.add(p.y);
								}
							}
						}
						//vertical
						else{
							//below
							if(p.y<k.y){
								for(int i=p.y; i<k.y; i++){
									xList.add(p.x);
									yList.add(i);
								}
							}
							//above
							else{
								for(int i=p.y; i>k.y; i--){
									xList.add(p.x);
									yList.add(i);
								}
							}
						}
					}
					else if(p instanceof Knight){
						xList.add(p.x);
						yList.add(p.y);
					}
					else if(p instanceof Pawn){
						xList.add(p.x);
						yList.add(p.y);
					}
					//see if there is a piece of our color that threatens any of those spaces
					for(int i=0; i<xList.size(); i++){
						if(threatenedByNonKings(xList.get(i), yList.get(i), color))
							return false;
					}
					b=true;
					break;
				}
			}
			if(b)
				break;
		}
		return true;
	}
	
	/**
	 * Checks if a move by the player put the opponent in check
	 * @param player - the player who moved the piece in question
	 * @return whether the player has put the opponent in check
	 */
	public static boolean isCheck(boolean player){
		char color = player? 'b':'w';
		for (Piece[] row : board){
			for (Piece p : row){
				if(p.color == color && p instanceof King){
					return ((King) p).isChecked();
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks for stalemate.
	 * @param player - the current player
	 * @return whether the given player is in stalemate
	 */
	public static boolean isStalemate(boolean player){
		char color = player? 'b':'w';
		for (Piece[] row : board){
			for (Piece p : row){
				if(p.isBlank() || p.color!=color)
					continue;
				for(int i=0; i<7; i++){
					for(int j=0; j<7; j++){
						Piece test = board[i][j];
						if(p.canMove(test.x, test.y))
							return false;
					}
				}
			}
		}
		return true;
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
			if ((p1.color == 'w' && p2.y == 7) || (p1.color == 'b' && p2.y == 0))
				promote(board[p2.y][p2.x]);
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
	 * Promotes a pawn to the chosen piece based on input.
	 * <p>
	 * Defaults to queen if unspecified.
	 * @param p the piece to be promoted
	 */
	public static void promote(Piece p){
		char choice;
		if(input.length() == 7){
			choice = input.charAt(6);
			if(choice == 'N')
				board[p.y][p.x] = new Knight(p.x, p.y, p.color);
			if(choice == 'R')
				board[p.y][p.x] = new Rook(p.x, p.y, p.color);
			if(choice == 'B')
				board[p.y][p.x] = new Bishop(p.x, p.y, p.color);
			if(choice == 'Q')
				board[p.y][p.x] = new Queen(p.x, p.y, p.color);
		}
		else{
			board[p.y][p.x] = new Queen(p.x, p.y, p.color);
		}
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
	 * Prints the current state of the board in ASCII art.
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
	
	/**
	 * Creates and displays an ASCII game of chess
	 * @param args - command line arguments
	 */
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
				drawRequested=false;
				if(input.contains("draw?"))
					drawRequested = true;
				if(isLegal(currentPlayer))
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
			
			check = isCheck(currentPlayer);
			checkmate = isCheckmate(currentPlayer);
			stalemate = isStalemate(currentPlayer);
			if(checkmate){
				System.out.println("Checkmate");
				String winner = currentPlayer ? "White":"Black";
				System.out.println(winner+" wins");
				break;
			}
			else if(stalemate){
				System.out.println("Stalemate");
				System.out.println("Draw");
				break;
			}
			System.out.println('\n');
			
			currentPlayer = !currentPlayer;
		}
		scanner.close();
	}
}
