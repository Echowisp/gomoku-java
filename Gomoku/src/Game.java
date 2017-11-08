import javafx.scene.text.Font;
import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Game extends Application {
	private Board gameboard = new Board();
	private Stage window;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primarystage) {
		newGame();
		window.show();
	}

	// Returns a scene that represents a new game state
	private void newGame() {
		window = new Stage();

		window.setTitle("Gomoku");
		window.setHeight(901);
		window.setWidth(630);
		window.setMaxHeight(750);
		window.setMaxWidth(630);

		Button newgamebutton = new Button("Reset Game");

		VBox layout = new VBox();
		layout.getChildren().addAll(newgamebutton, newBoard());
		layout.setAlignment(Pos.TOP_CENTER);

		Scene newscene = new Scene(layout);

		newgamebutton.setOnMouseClicked(e -> {
			layout.getChildren().clear();
			layout.getChildren().addAll(newgamebutton, newBoard());
		}); // Set button to reset game.		
		
		window.setScene(newscene);
	}
	
	private void resetGame() {
		Button newgamebutton = new Button("Reset Game");

		VBox layout = new VBox();
		layout.getChildren().addAll(newgamebutton, newBoard());
		layout.setAlignment(Pos.TOP_CENTER);

		newgamebutton.setOnMouseClicked(e -> {
			layout.getChildren().clear();
			layout.getChildren().addAll(newgamebutton, newBoard());
		}); // Set button to reset game.		
		
		window.getScene().setRoot(layout);
	}
	
	private VBox newBoard(){
		gameboard = new Board();
		GridPane gp = new GridPane();
		VBox vb = new VBox();
		
		//Setting up the GridPane
		gp.setPadding(new Insets(10, 10, 10, 10)); // Adds a small bit of
													// padding

		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				Tile tile = new Tile(j, i, gameboard);
				GridPane.setConstraints(tile, j, i);
				gp.getChildren().addAll(tile);
			}
		}
		
		// Adding misc labels to VBox.
		Label status = new Label("Welcome to Gomoku! White plays first");
		status.setFont(new Font("Courier", 24));
		
		// Adding things to the vb.
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(gp, status);
		
		vb.setOnMouseClicked(e -> {
			if(gameboard.whosTurn().equals("white")) {
				status.setText("Its White's turn to play");
			} else {
				status.setText("Its Black's turn to play");
			}
		});
		
		return vb;
	}

	// Draws the tile, handles on clicks for placing tiles
	private class Tile extends StackPane {
		private int x;
		private int y;
		Board b; // Indicates the board that this is part of

		public Tile(int xc, int yc, Board cb) {
			this.x = xc;
			this.y = yc;
			this.b = cb;
			Rectangle box = new Rectangle(30, 30);
			box.setFill(Color.GRAY);
			box.setStroke(Color.BLACK);
			getChildren().addAll(box);

			setOnMouseClicked(e -> handleClick(x, y));

		}

		// Changes the board state when A piece is played, checks for victory
		// conditions. If game is won displays a pop up with a button that resets the game
		public void handleClick(int x, int y) {
			if (b.getPiece(x, y).inPlay()) {
				return;
			}

			Circle c = new Circle(15);
			c.setStroke(Color.BLACK);

			if (b.whosTurn().equals("white")) {
				b.playWhite(x, y);
				c.setFill(Color.WHITE);
			} else {
				b.playBlack(x, y);
				c.setFill(Color.BLACK);
			}
			getChildren().addAll(c);
			Piece p = b.checkGame();

			if (p.inPlay()) {
				Stage alert = new Stage(); 
				/* make the button needed for the alert box*/
				Button newgamebutton = new Button("New Game");
				
				newgamebutton.setOnMouseClicked(e -> {
					alert.close();
					resetGame(); 
				}); 
				/* end of reset button*/
				
				/* Making the alertbox that pops up when game is over, giving option to reset the game*/
				alert.setTitle("Game Over!");
				alert.initModality(Modality.APPLICATION_MODAL);
		        alert.initStyle(StageStyle.UNDECORATED);

				
				Label whowon = new Label();
				VBox alertlayout = new VBox();
				alertlayout.getChildren().addAll(whowon,newgamebutton);
				alertlayout.setAlignment(Pos.CENTER);
				Scene alertscene = new Scene(alertlayout,300,100);
				/* End of alert box*/

				alert.setScene(alertscene);
			
				
				if (p instanceof BlackPiece) {
					whowon.setText("Black wins!");
				} else {
					whowon.setText("White wins!");
				}
				alert.show();
			}

		}
	}
}
