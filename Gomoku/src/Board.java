class Board {
	// Board is an array of pieces
	private Piece[][] board;
	private Boolean turn = true; // true = white, false = Black

	// new game board
	public Board() {
		board = new Piece[19][19];
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				board[i][j] = new Piece();
			}
		}
	}
	
	

	// gets piece at index x,y. 0 indexed
	public Piece getPiece(int x, int y) {
		return board[y][x];
	}
	
	// Returns toPlay
	
	public String whosTurn() {
		if(turn) {
			return "white";
		} else {
			return "black";
		}
	}

	// plays a white piece
	public void playWhite(int x, int y) {
		Piece toPlay = new WhitePiece(x, y);
		board[toPlay.getX()][toPlay.getY()] = toPlay;
		turn = !turn;

	}

	// plays a white piece
	public void playBlack(int x, int y) {
		Piece toPlay = new BlackPiece(x, y);
		board[toPlay.getX()][toPlay.getY()] = toPlay;
		turn = !turn;

	}

	// prints board, denoting black with b and white with white
	public void printBoard() {
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				char c = '-';
				if (board[i][j] instanceof BlackPiece) {
					c = 'B';
				}
				if (board[i][j] instanceof WhitePiece) {
					c = 'W';
				}
				System.out.printf("%c", c);
			}
			System.out.printf("\n");
		}
	}

	// Checks ray for victory, returns BlackPiece for b win, WhitePiece for w
	// win and Piece for no win
	// Starting from startx,starty, a 'ray' is projected in the dirx,diry
	// direction, where 0 represents the -ve direction and 1 the +ve direction
	// Method checks for 5 in a rows along this ray.
	public Piece checkWin(int startx, int starty, int dirx, int diry) {
		int count = 0, i = starty, j = startx;
		Piece ret = new Piece();
		Class cs = ret.getClass();

		while (i < 19 && j < 19 && i >= 0 && j >= 0) {
			Piece curr = board[i][j];
			if (count == 0) {
				ret = curr;
				cs = ret.getClass();
				count++;
			} else {
				if (cs.isInstance(curr) && (ret instanceof BlackPiece || ret instanceof WhitePiece)) {
					count++;
				} else {
					ret = curr;
					cs = ret.getClass();
					count = 1;
				}
			}
			if (count == 5) {
				return ret;
			}
			i += diry;
			j += dirx;
		}
		return new Piece();
	}
	
	// checks every ray on the gameboard for victory. Returns the winning piece
	// Can be optimised
	public Piece checkGame() {
		Piece win;
		//Check Horizontal
		for(int i = 0; i < 19; i++) {
			win = checkWin(0,i,1,0);
			if(win instanceof BlackPiece || win instanceof WhitePiece) {
				return win;
			}
		}
		//Check Verticals
		for(int i = 0; i < 19; i++) {
			win = checkWin(i,0,0,1);
			if(win instanceof BlackPiece || win instanceof WhitePiece) {
				return win;
			}
		}
		//Check Diagonal SE
		for(int i = 0; i < 19; i++) {
			win = checkWin(i,0,1,1);
			if(win instanceof BlackPiece || win instanceof WhitePiece) {
				return win;
			}
		}
		for(int i = 1; i < 19; i++) {
			win = checkWin(0,i,1,1);
			if(win instanceof BlackPiece || win instanceof WhitePiece) {
				return win;
			}
		}
		//Check Diagonal NE
		for(int i = 0; i < 19; i++) {
			win = checkWin(i,0,-1,1);
			if(win instanceof BlackPiece || win instanceof WhitePiece) {
				return win;
			}
		}
		for(int i = 1; i < 19; i++) {
			win = checkWin(18,i,-1,1);
			if(win instanceof BlackPiece || win instanceof WhitePiece) {
				return win;
			}
		}
		return new Piece();
	}
	
	// tells you if the game is over
	public Boolean gameOver() {
		return checkGame().inPlay(); //No win returns a new piece which is not in play
	}
	// This is Here to test the class 
	/*
	public static void main(String[] args) {
		Board test = new Board();
		test.playWhite(1, 9);
		test.playWhite(2, 8);
		test.playWhite(3, 7);
		test.playWhite(4, 6);s
		test.playWhite(5, 6);
		test.printBoard();
		Piece p = test.checkGame();
		System.out.println(test.gameOver());

	}
	*/
}

class Piece {
	// Co-ordinates
	private Position xy;
	// In play check. 1 = in play, 0 = not in play
	private int played;

	// Gets x and y
	public int getX() {
		return xy.getX();
	}

	public int getY() {
		return xy.getY();
	}

	// Standard, unplayed piece
	public Piece() {
		this.played = 0;
		this.xy = new Position(-1, -1);
	}

	// Overload
	// Play a piece at a specific location
	public Piece(int yc, int xc) {
		this.played = 1;
		this.xy = new Position(xc, yc);
	}

	public Boolean inPlay() {
		return played == 1;
	}
	
	public void printPiece() {
		System.out.println("I am a  piece that is not in play at" + getX() + getY());
	}
}

class BlackPiece extends Piece {
	
	public BlackPiece(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void printPiece() {
		System.out.println("I am a black piece that is in play at" + getX() + getY());
	}
}

class WhitePiece extends Piece {
	public WhitePiece(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void printPiece() {
		System.out.println("I am a white piece that is in play at" + getX() + getY());
	}
}

class Position {
	private int x;
	private int y;

	public Position(int xs, int ys) {
		this.x = xs;
		this.y = ys;
	}

	// Gets x and y
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}