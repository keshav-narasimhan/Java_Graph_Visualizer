package graph_visuals;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class Main extends Application {
	static final int NUM_ROWS = 72;
	static final int NUM_COLS = 53;
	static final int BOX_SIZE = 10;
	private int numClicked = 0;
	private static Node startNode;
	private static Node targetNode;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
		startNode = null;
		targetNode = null;
	}

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
					System.out.println(x + " " + y);
					Rectangle curr_node = (Rectangle) clickedNode;
					if (numClicked < 1) {
						curr_node.setFill(Color.GREEN);
						startNode = curr_node;
						numClicked++;
					} else if (numClicked < 2) {
						curr_node.setFill(Color.BLUE);
						targetNode = curr_node;
						numClicked++;
						BFS bfs = new BFS(startNode, targetNode, pane, NUM_ROWS, NUM_COLS);
					}
				} catch(NullPointerException e) {
					System.out.println("Pick another point! Hint: Try an edge");
				}
			}
			
		});
		
		primaryStage.setScene(new Scene(pane, 800, 600));
		primaryStage.show();
	}
	
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
