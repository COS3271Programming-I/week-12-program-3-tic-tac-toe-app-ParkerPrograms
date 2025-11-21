package Week12;
import java.util.Scanner;

class TTT{
	static Scanner userinput = new Scanner(System.in);
	char[][] board = {{'.','.','.'},{'.','.','.'},{'.','.','.'}};
	int turn = 1;
	char player = 'X';

	public void printBoard (){
		int i,j;
		System.out.println("");
		for (i=0;i<=2;i++)
		{
			for (j=0;j<=2;j++)
			{
				System.out.print(board[i][j] + " ");
			}
			System.out.println("");	
		}
	}
	public void move(int i, int j){
		board[i][j] = player;
		turn++;
	}
	public void unDoMove(int i, int j){
		board[i][j] = '.';
		turn--;
	}
	public void switchPlayers (){
		if (player == 'X') {player = 'O';}
		else player = 'X';
	}
	
	public boolean isLegal(int i, int j){
		if (board[i][j] == '.') return true;
		else return false;
	}
	
	public boolean winner(){
		int i;
		boolean test = false;
		for (i = 0; i<=2;i++)
		{
			if ((board[i][0]==board[i][1]) && (board[i][1]==board[i][2]) &&
					(board[i][0]!='.'))
			{test = true;}
			if ((board[0][i]==board[1][i]) && (board[1][i]==board[2][i]) &&
				(board[0][i]!='.'))
			{test = true;}
		}
		if ((board[0][0]==board[1][1]) && (board[1][1]==board[2][2]) &&
				(board[0][0]!='.'))
		    {test = true;}
		
		if ((board[2][0]==board[1][1]) && (board[1][1]==board[0][2]) &&
				(board[2][0]!='.'))
		    {test = true;}
		return test;
	}
	
	public void human() {
		int i,j;

		boolean test = false;  //have I found a place to go
		while (test == false)
		{
			System.out.println("\nEnter Coordinates Where To Go Separated By A Space...");
			i = userinput.nextInt();
			j = userinput.nextInt();
			userinput.nextLine();
			if (isLegal(i-1,j-1) == true) {test = true; move(i-1,j-1);} 
		}
	}
	public void ai(int turn){
		int i,j,row,col;
		boolean test = false;  //have I found a place to go
		while (test == false) {
			//center
			if (board[1][1]=='.') {row=1; col=1;}
			//corners
			else if (board[0][0]=='.') {row=0; col=0;}
			else if (board[0][2]=='.') {row=0; col=2;}
			else if (board[2][0]=='.') {row=2; col=0;}
			else if (board[2][2]=='.') {row=2; col=2;}
			//sides
			else if (board[0][1]=='.') {row=0; col=1;}
			else if (board[1][2]=='.') {row=1; col=2;}
			else if (board[2][1]=='.') {row=2; col=1;}
			else {row=1; col=0;}
			
			//this extra for loop is so that the X's are tested before the O's
			//so that the AI will prefer winning over blocking
			char k;
			for (j=0; j<2; j++) {
				if (j==0) {k = 'X';}
				else {k = 'O';}
				
				//if someone will win
				for (i=0; i<=2; i++) {
					//diagonals
					if ((board[0][0]==board[1][1]) && (board[2][2]=='.') &&
							(board[0][0]==k)) {row=2; col=2;}
					if ((board[0][0]==board[2][2]) && (board[1][1]=='.') &&
							(board[0][0]==k)) {row=1; col=1;}
					if ((board[1][1]==board[2][2]) && (board[0][0]=='.') &&
							(board[1][1]==k)) {row=0; col=0;}
					
					if ((board[2][0]==board[1][1]) && (board[0][2]=='.') &&
							(board[2][0]==k)) {row=0; col=2;}
					if ((board[2][0]==board[0][2]) && (board[1][1]=='.') &&
							(board[2][0]==k)) {row=1; col=1;}
					if ((board[1][1]==board[0][2]) && (board[2][0]=='.') &&
							(board[1][1]==k)) {row=2; col=0;}
					//rows
					if ((board[i][0]==board[i][1]) && (board[i][2]=='.') &&
							(board[i][0]==k)) {row=i; col=2;}
					if ((board[i][0]==board[i][2]) && (board[i][1]=='.') &&
							(board[i][0]==k)) {row=i; col=1;}
					if ((board[i][1]==board[i][2]) && (board[i][0]=='.') &&
							(board[i][1]==k)) {row=i; col=0;}
					//columns
					if ((board[0][i]==board[1][i]) && (board[2][i]=='.') &&
							(board[0][i]==k)) {row=2; col=i;}
					if ((board[0][i]==board[2][i]) && (board[1][i]=='.') &&
							(board[0][i]==k)) {row=1; col=i;}
					if ((board[1][i]==board[2][i]) && (board[0][i]=='.') &&
							(board[1][i]==k)) {row=0; col=i;}	
				}
			}	
			//special case to not get trapped
			//if corner, go middle
			if ((turn == 1)&&((board[0][0]=='X')||(board[0][2]=='X')||(board[2][2]=='X')||(board[2][0]=='X'))) {row=1; col=1;}
			//if X:corner O:middle X:opposite_corner, then go side
			if ((turn == 2)&&(((board[0][0]==board[2][2])&&(board[0][0]=='X'))||((board[0][2]==board[2][0])&&(board[0][2]=='X')))) {row=1; col=0;}
			
			if (isLegal(row,col) == true) {test = true; move(row,col);} 
		}
		System.out.println("AI is moving ... ");
	
	}
}
public class TicTacToe_App {
	static Scanner userinput = new Scanner(System.in);
	public static void main (String[] args)
	{
		TTT game = new TTT();
		game.printBoard();
		for (int i = 1;i<=5; i++)
		{
            game.human();
			game.printBoard();
			if ((game.winner() == true) || (i == 5)) {break;}
			game.switchPlayers();
			game.ai(i);
			game.printBoard();
			if ((game.winner() == true) || (i == 5)) {break;}
			game.switchPlayers();
		}
		
		if (game.winner() == true) {System.out.println("\nThe winner is " + game.player);}
		else {System.out.println("\nCat Game.");}
	} //end main line
} //end class
