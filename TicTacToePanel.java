//Christiana Nardi
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Arrays;

@SuppressWarnings("serial")
class TicTacToePanel extends GamePanel {

	public enum Player {
		NONE, X, O
	}

	private Player[][] gameBoard; 

	private Player currentPlayer = Player.X;
	private int rowNum = 3;
	private int colNum = 3;
	private int xTurns = 5;
	private int oTurns = 5;

	/** Creates an empty Tic-Tac-Toe board ready for play */
	public TicTacToePanel() {
		super(600, 600); 
		gameBoard = new Player[3][3];
		resetGame();

	}

	//set spaces blank and current player to x
	private void resetGame() {
		currentPlayer = Player.X;
		xTurns = 5;
		oTurns = 5;
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				gameBoard[row][col] = Player.NONE;
			}
		}
	}

	@Override
	protected void handleMouseRelease(int x, int y) {
		int spaceWidth = getWidth() / colNum;
		int spaceHeight = getHeight() / rowNum;
		int row = 3*y / getHeight();
		int col = 3*x / getWidth();

		// return if player clicks on occupied space
		if (gameBoard[row][col] != Player.NONE) {
		    return;
		}
		// check if space is blank
		if (gameBoard[row][col] == Player.NONE) {
			Graphics g = getGraphics();
			// where to start drawing/make smaller and centered
			int drawX =  (2*col + 1)*getWidth()/6;
			int drawY =  (2*row + 1)*getHeight()/6;

			// check who is playing
			if (currentPlayer == Player.X) {
				// draw two lines for last X
				if (currentPlayer == Player.X) {
					g.setColor(Color.BLUE);
					int centerX = (2 * col + 1) * spaceWidth / 2;
					int centerY = (2 * row + 1) * spaceHeight / 2;
					int size = spaceWidth / 4;
					g.drawLine(centerX - size, centerY - size, centerX + size, centerY + size);
					g.drawLine(centerX - size, centerY + size, centerX + size, centerY - size);
					xTurns--;
					gameBoard[row][col] = currentPlayer;
				}
				} else if (currentPlayer == Player.O) {
					g.setColor(Color.RED);
					int centerX = (2 * col + 1) * spaceWidth / 2;
					int centerY = (2 * row + 1) * spaceHeight / 2;
					int size = spaceWidth / 2;
					g.drawOval(centerX - size / 2, centerY - size / 2, size, size);
					oTurns--;
					gameBoard[row][col] = currentPlayer;
				}
			}
		
			
			// change players after turn
			if (currentPlayer == Player.X) {
				currentPlayer = Player.O;
			} else {
				currentPlayer = Player.X;
			}
			System.out.println(Arrays.deepToString(gameBoard));
		}
	

	@Override
	public final void repaintPanel(Graphics g) {
		//fill board
		g.setColor(Color.WHITE);
		int width = getWidth();
		int height = getHeight();
		g.fillRect(0, 0, width, height);

		// Paint grid lines
		int spaceWidth = width / colNum;
		int spaceHeight = height / rowNum;
		g.setColor(Color.BLACK);

		for (int i = 0; i <= colNum; i++) {
			int x = i * spaceWidth;
			g.drawLine(x, 0, x, height);
		}

		for (int i = 0; i <= rowNum; i++) {
			int y = i * spaceHeight;
			g.drawLine(0, y, width, y);
		}

		// ScoreBoard
		g.setFont(new Font("Arial", Font.BOLD, 12));
		g.setColor(Color.BLUE);
		g.drawString("TURNS LEFT: " + xTurns, 500, 570);
		g.setColor(Color.RED);
		g.drawString("TURNS LEFT: " + oTurns, 500, 590);

		// Redraw board with X's and O's
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(5));
		for (int row = 0; row < rowNum; row++) {
			for (int col = 0; col < colNum; col++) {
				Player nextPlayer = gameBoard[row][col];
				if (nextPlayer == Player.X) {
					g.setColor(Color.BLUE);
					int centerX = (2 * col + 1) * spaceWidth / 2;
					int centerY = (2 * row + 1) * spaceHeight / 2;
					int size = spaceWidth / 4;
					g.drawLine(centerX - size, centerY - size, centerX + size, centerY + size);
					g.drawLine(centerX - size, centerY + size, centerX + size, centerY - size);
					
				} else if (nextPlayer == Player.O) {
					g.setColor(Color.RED);
					int centerX = (2 * col + 1) * spaceWidth / 2;
					int centerY = (2 * row + 1) * spaceHeight / 2;
					int size = spaceWidth / 2;
					g.drawOval(centerX - size / 2, centerY - size / 2, size, size);

				}
			}
		}

		// check for win

		// strike through horizontal
		for (int row = 0; row < rowNum; row++) {
			if (gameBoard[row][0] == gameBoard[row][1] && gameBoard[row][1] == gameBoard[row][2]
					&& gameBoard[row][0] != Player.NONE) {
				int lineStart = spaceWidth / 2;
				int lineEnd = spaceHeight * 3 - lineStart;
				int lineY = (row * spaceHeight) + spaceHeight / 2;
				if (gameBoard[row][0] == Player.X) {
					g.setColor(Color.BLUE);
					g.drawString("Player X wins!", 10, 590);
					g.setColor(Color.BLACK);
					g.drawLine(lineStart, lineY, lineEnd, lineY);
				} else {
					g.setColor(Color.RED);
					g.drawString("Player O wins!", 10, 590);
					g.setColor(Color.BLACK);
					g.drawLine(lineStart, lineY, lineEnd, lineY);
				}
				resetGame();
			}
		}
		// strike through vertical
		for (int col = 0; col < colNum; col++) {
			if (gameBoard[0][col] == gameBoard[1][col] && gameBoard[1][col] == gameBoard[2][col]
					&& gameBoard[0][col] != Player.NONE) {
				int lineStart = spaceHeight / 2;
				int lineEnd = spaceHeight * 3 - spaceHeight / 2;
				int lineX = (col * spaceWidth) + spaceWidth / 2;
				if (gameBoard[0][col] == Player.X) {
					g.setColor(Color.BLUE);
					g.drawString("Player X wins!", 10, 590);
					g.setColor(Color.BLACK);
					g.drawLine(lineX, lineStart, lineX, lineEnd);
				} else {
					g.setColor(Color.RED);
					g.drawString("Player O wins!", 10, 590);
					g.setColor(Color.BLACK);
					g.drawLine(lineX, lineStart, lineX, lineEnd);
				}
				resetGame();
			}
		}
		// strike through diagonal left to right
		if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2]
				&& gameBoard[0][0] != Player.NONE) {
			int lineStartX = spaceWidth / 2;
			int lineEndX = spaceWidth * 3 - spaceWidth / 2;
			int lineStartY = spaceHeight / 2;
			int lineEndY = spaceHeight * 3 - spaceHeight / 2;
			if (gameBoard[0][0] == Player.X) {
				g.setColor(Color.BLUE);
				g.drawString("Player X wins!", 10, 590);
				g.setColor(Color.BLACK);
				g.drawLine(lineStartX, lineStartY, lineEndX, lineEndY);
			} else {
				g.setColor(Color.RED);
				g.drawString("Player O wins!", 10, 590);
				g.setColor(Color.BLACK);
				g.drawLine(lineStartX, lineStartY, lineEndX, lineEndY);
			}
			resetGame();
		}

		// strike through diagonal right to left
		if (gameBoard[0][2] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][0]
				&& gameBoard[0][2] != Player.NONE) {
			int lineStartX = spaceWidth * 3 - spaceWidth / 2;
			int lineEndX = spaceWidth / 2;
			int lineStartY = spaceHeight / 2;
			int lineEndY = spaceHeight * 3 - spaceHeight / 2;
			if (gameBoard[0][2] == Player.X) {
				g.setColor(Color.BLUE);
				g.drawString("Player X wins!", 10, 590);
				g.setColor(Color.BLACK);
				g.drawLine(lineStartX, lineStartY, lineEndX, lineEndY);
			} else {
				g.setColor(Color.RED);
				g.drawString("Player O wins!", 10, 590);
				g.setColor(Color.BLACK);
				g.drawLine(lineStartX, lineStartY, lineEndX, lineEndY);
			}
			resetGame();
		}
		
		// if tied
		if (xTurns == 0 && oTurns == 1) {
			g.setColor(Color.BLACK);
			g.drawString("Player X and Player O are tied!", 10, 590);
			resetGame();
		}
		
	}

}