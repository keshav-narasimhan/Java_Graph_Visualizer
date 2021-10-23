package graph_visuals;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class Main extends Application {
	/**
	 * Private fields of Main
	 */
	static final int NUM_ROWS = 72;
	static final int NUM_COLS = 53;
	static final int BOX_SIZE = 10;
	private int numClicked = 0;
	private static Node startNode;
	private static Node targetNode;
	private static boolean isDFS;
	private static boolean isBFS;

	/**
	 * main method
	 * initializes startNode & targetNode
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
		startNode = null;
		targetNode = null;
		isDFS = false;
		isBFS = false;
	}

	/**
	 * Overwritten method for Application
	 * Will draw grid lines on created grid
	 * allows user to select start node & target node on mouse click
	 * @param primaryStage -- Stage we will use to hold Scenes & Panes
	 */
	@Override
	public void start(Stage primaryStage) {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Graph Visualizer");
		
		GridPane pane = new GridPane();
		drawGridLines(pane);
		
		pane.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				Node clickedNode = event.getPickResult().getIntersectedNode();
				int x;
				int y;
				
				try {
					x = pane.getColumnIndex(clickedNode);
					y = pane.getRowIndex(clickedNode);
					Rectangle curr_node = (Rectangle) clickedNode;
					if (numClicked < 1) {
						curr_node.setFill(Color.GREEN);
						startNode = curr_node;
						numClicked++;
					} else if (numClicked < 2) {
						// numClicked++;
						if (targetNode == null) {
							targetNode = curr_node;
							curr_node.setFill(Color.BLUE);
						}
						
						if (isBFS) {
							BFS bfs = new BFS(startNode, targetNode, pane, NUM_ROWS, NUM_COLS);
						}
						
					}
				} catch(NullPointerException e) {
					System.out.println("Pick another point! Hint: Try an edge");
				}
			}
			
		});
		
		Stage secondaryStage = new Stage();
		secondaryStage.setX(10);
		secondaryStage.setY(40);
		secondaryStage.setTitle("Choose Graph Algorithm"); 
		StackPane secondaryPane = new StackPane();
		Button chooseBFS = new Button("BFS");
		chooseBFS.setTranslateY(10);
		Button chooseDFS = new Button("DFS");
		chooseDFS.setTranslateY(-40);
		
		chooseBFS.setOnAction(new EventHandler<ActionEvent>() { // what to do when butt is pressed
			
			@Override
			public void handle(ActionEvent event) {
				isBFS = true;
				if (targetNode != null) {
					BFS bfs = new BFS(startNode, targetNode, pane, NUM_ROWS, NUM_COLS);
				}
			}
			
		});
		
		chooseDFS.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				isDFS = true;
			}
			
		});
		
		secondaryPane.getChildren().add(chooseBFS);
		secondaryPane.getChildren().add(chooseDFS);
		Scene secondaryScene = new Scene(secondaryPane, 200, 200); // creates a second scene object with the Stackpane
		secondaryStage.setScene(secondaryScene); // puts the scene onto the second stage 
		secondaryStage.show(); // display the stage with the scene
		
		primaryStage.setScene(new Scene(pane, 800, 600));
		primaryStage.show();
	}
	
	/**
	 * will draw grid lines on the GridPane passed
	 * NUM_ROWS x NUM_COLS
	 * @param pane -- pane on which we draw the grid lines
	 */
	private void drawGridLines(GridPane pane) {
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				Shape grid = new Rectangle(BOX_SIZE, BOX_SIZE);
				grid.setFill(null);
				grid.setStroke(Color.BLACK);
				pane.add(grid, i, j);
			}
		}
	}
}
