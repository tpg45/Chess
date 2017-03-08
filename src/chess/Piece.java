package chess;

public class Piece{
	int x;
	int y;
	char color;
	boolean hasMoved;
	
	public Piece(int x, int y, char color){
		this.x=x;
		this.y=y;
		this.color=color;
		hasMoved=false;
	}
	
	public boolean canMove(int x, int y){
		return false;
	}
	
	public boolean isBlank(){
		return toString().charAt(0)==' ' || toString().charAt(0)=='#';
	}
	
	public String toString(){
		return color=='w' ? "  ":"##";
	}
}
