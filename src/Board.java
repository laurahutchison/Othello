import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class Board{
	
	public char[][] board = new char[8][8];
	int whiteScore;
	int blackScore;
	int pointsToGo;
	
	char white = 'W';
	char black = 'B';
	char clear = '_';
	
	char[] list = new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
	
	public class XY{
		int let;
		int num;
		
	        XY(int let, int num){
	            this.let = let;
	            this.num = num;
	        }
	        
	        @Override
	        public String toString(){
	            return "["+let+", "+num+"]";
	        }
	        
	        @Override
	        public boolean equals(Object o){
	            return o.hashCode()==this.hashCode();
	        }

	        @Override
	        public int hashCode() {
	            return Integer.parseInt(let+""+num);
	        }
	}
	
	public Board() {
		

		for(int i = 0; i < 8; i++){
		
			for(int k = 0; k < 8; k++){
				if(i == 3 && k == 3 || i == 4 && k == 4) {
					board[i][k] = white;
				}
				else if(i == 3 && k == 4 || i == 4 && k == 3) {
					board[i][k] = black;
				}
				else {
					board[i][k] = clear;
				}
			}
		}
	}
	
	public void validSpaces(char player, char opponent, HashSet<XY> Spots) {
		
		for(int i=0;i<8;++i){
            for(int k=0;k<8;++k){
                if(board[i][k] == opponent){
                    int I = i, K = k;  
                    if(i-1>=0 && k-1>=0 && board[i-1][k-1] == '_'){ 
                        i = i+1; k = k+1;
                        while(i<7 && k<7 && board[i][k] == opponent){i++;k++;}
                        if(i<=7 && k<=7 && board[i][k] == player) Spots.add(new XY(I-1, K-1));
                    } 
                    i=I;k=K;
                    if(i-1>=0 && board[i-1][k] == '_'){
                        i = i+1;
                        while(i<7 && board[i][k] == opponent) i++;
                        if(i<=7 && board[i][k] == player) Spots.add(new XY(I-1, K));
                    } 
                    i=I;
                    if(i-1>=0 && k+1<=7 && board[i-1][k+1] == '_'){
                        i = i+1; k = k-1;
                        while(i<7 && k>0 && board[i][k] == opponent){i++;k--;}
                        if(i<=7 && k>=0 && board[i][k] == player) Spots.add(new XY(I-1, K+1));
                    }  
                    i=I;k=K;
                    if(k-1>=0 && board[i][k-1] == '_'){
                        k = k+1;
                        while(k<7 && board[i][k] == opponent)k++;
                        if(k<=7 && board[i][k] == player) Spots.add(new XY(I, K-1));
                    }
                    k=K;
                    if(k+1<=7 && board[i][k+1] == '_'){
                        k=k-1;
                        while(k>0 && board[i][k] == opponent)k--;
                        if(k>=0 && board[i][k] == player) Spots.add(new XY(I, K+1));
                    }
                    k=K;
                    if(i+1<=7 && k-1>=0 && board[i+1][k-1] == '_'){
                        i=i-1;k=k+1;
                        while(i>0 && k<7 && board[i][k] == opponent){i--;k++;}
                        if(i>=0 && k<=7 && board[i][k] == player) Spots.add(new XY(I+1, K-1));
                    }
                    i=I;k=K;
                    if(i+1 <= 7 && board[i+1][k] == '_'){
                        i=i-1;
                        while(i>0 && board[i][k] == opponent) i--;
                        if(i>=0 && board[i][k] == player) Spots.add(new XY(I+1, K));
                    }
                    i=I;
                    if(i+1 <= 7 && k+1 <=7 && board[i+1][k+1] == '_'){
                        i=i-1;k=k-1;
                        while(i>0 && k>0 && board[i][k] == opponent){i--;k--;}
                        if(i>=0 && k>=0 && board[i][k] == player)Spots.add(new XY(I+1, K+1));
                    }
                    i=I;k=K;
                    }
                } 
        } 
	}


public void showBoard(char[][] board) {
	
	//check back to this for board
	System.out.print("\n  ");
    for(int i=0;i<8;++i)System.out.print(list[i]+" ");
    System.out.println();
    for(int i=0;i<8;++i){
        System.out.print((i+1)+" ");
        for(int k = 0; k < 8; k++)
            System.out.print(board[i][k]+" ");
        System.out.println();
    }
    System.out.println(); 
}

public int endGame(Set<XY> whiteLocation, Set <XY> blackLocation) {

	scoreUpdate();
	int hold = 3;
	
	if(pointsToGo == 0) {
		if(whiteScore > blackScore) {
			hold = 1;
		}
		else if(blackScore > whiteScore) {
			hold = 2;
		}
		else hold = 0;
	}
	
	if(whiteScore == 0 || blackScore == 0) {
		if(whiteScore > 0) {
			hold = 1;
		}
		
		else if(blackScore > 0) {
			hold = 2;
		}
		else { hold = 0;}
	}
	
	return hold;
}

public HashSet<XY> validSpots(char player, char opponent){ 
    HashSet<XY> Spots = new HashSet<>();
    validSpaces(player, opponent, Spots);
    return Spots;
}

public void displayValidMoves(HashSet<XY> locations, char player, char opponent) {

	for(XY p:locations)
		board[p.let][p.num] = ' ';
	showBoard(board);
	for(XY p:locations)
		board[p.let][p.num] = clear;
}

public void moveSet(XY p, char player, char opponent) {
	
	int i = p.let; 
	int k = p.num;
	
    board[i][k] = player; 
    
    //upper case I K to hold original variables through move setting
    int I = i; 
    int K = k;  
    
    
    if(i - 1 >= 0 && k - 1 >= 0 && board[--i][--k] == opponent){ 
        i--;
        k--;
	        while(i>0 && k>0 && board[i][k] == opponent){
	        	i--;
	        	k--;}
	        if(i>=0 && k>=0 && board[i][k] == player) {
	        	while(i!=I-1 && k!=K-1) {
	        		board[++i][++k]=player;
	        	}
	        }
    }
    i=I;
    k=K; 
    
    if(--i >= 0 && board[--i][k] == opponent){
        i = i-1;
        while(i>0 && board[i][k] == opponent) i--;
        if(i>=0 && board[i][k] == player) {while(i!=I-1)board[++i][k]=player;}
    } 
    i=I; 
    if(i-1>=0 && k+1<=7 && board[i-1][k+1] == opponent){
        i = i-1; k = k+1;
        while(i>0 && k<7 && board[i][k] == opponent){i--;k++;}
        if(i>=0 && k<=7 && board[i][k] == player) {while(i!=I-1 && k!=K+1)board[++i][--k] = player;}
    }   
    i=I;k=K;
    if(k-1>=0 && board[i][k-1] == opponent){
        k = k-1;
        while(k>0 && board[i][k] == opponent)k--;
        if(k>=0 && board[i][k] == player) {while(k!=K-1)board[i][++k] = player;}
    }
    k=K; 
    if(k+1<=7 && board[i][k+1] == opponent){
        k=k+1;
        while(k<7 && board[i][k] == opponent)k++;
        if(k<=7 && board[i][k] == player) {while(k!=K+1)board[i][--k] = player;}
    }
    k=K; 
    if(i+1<=7 && k-1>=0 && board[i+1][k-1] == opponent){ 
        i=i+1;k=k-1;
        while(i<7 && k>0 && board[i][k] == opponent){i++;k--;}
        if(i<=7 && k>=0 && board[i][k] == player) {while(i!=I+1 && k!=K-1)board[--i][++k] = player;}
    }
    i=I;k=K; 
    if(i+1 <= 7 && board[i+1][k] == opponent){ 
        i=i+1;
        while(i<7 && board[i][k] == opponent) i++;
        if(i<=7 && board[i][k] == player) {while(i!=I+1)board[--i][k] = player;}
    }
    i=I;

    if(i+1 <= 7 && k+1 <=7 && board[i+1][k+1] == opponent){
        i=i+1;k=k+1;
        while(i<7 && k<7 && board[i][k] == opponent){i++;k++;}
        if(i<=7 && k<=7 && board[i][k] == player)while(i!=I+1 && k!=K+1)board[--i][--k] = player;}
}  

public void scoreUpdate() {
	
	pointsToGo = 0;
	blackScore = 0;
	whiteScore = 0;
	
	for(int i = 0; i < list.length; i++) {
		for(int k = 0; k < list.length; k++) {
			
			if(board[i][k] == white) {
				whiteScore++;
			}
			
			if(board[i][k] == black) {
				blackScore++;
			}
			else {
				pointsToGo++;
			}
		}
	}
}



public int changeX(char x) {
	for(int i=0;i<8;++i)if(list[i]==Character.toLowerCase(x)||list[i]==Character.toUpperCase(x))return i;
    return -1; // Illegal move received
}

public static char[][] loadGame() {
	Scanner input = new Scanner(System.in);
	
	FileReader readText = null;
	BufferedReader bufferedReader = null;
	String holdChoice = new String();
	
	System.out.println("Welcome to load menu.");
	System.out.println("Enter a save 1, 2, or 3 to be read. ");
	holdChoice = input.nextLine();
	
	char board[][] = new char [8][8];

	 File file = new File("save" + holdChoice + ".txt");
	 boolean test;
     System.out.println("File = " + file.getAbsolutePath());

     test = file.exists();
	
	if(test == true) {
	
	//try the file and writing to console in case it is corrupt in some way
	try {
		readText = new FileReader("save" + holdChoice + ".txt");
		bufferedReader = new BufferedReader(readText);
		
		String nextLine = bufferedReader.readLine();
		
		for(int i = 0; i < 8; i++) {
			for(int k = 0; k < 8; k++) {
		while (nextLine !=null) {
			
			board[i][k] = nextLine.charAt(0);
			nextLine = bufferedReader.readLine();
				}}
		
		bufferedReader.close();
		}
	}
	//catch exception so our program doesn't crash
	catch(IOException e) {
		System.out.println("There is no save game in that slot!");
	}
	}
	
	//if test = false then restart from menu
	if(test == false) {
		
		System.out.println("Please try again. File not found.");
		}
		
	return board;
}
	
}





