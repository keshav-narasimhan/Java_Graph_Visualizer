package graph_visuals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BFS {
	/**
	 * Private fields of BFS class
	 */
	private Node startNode;
	private Node targetNode;
	private GridPane pane;
	private int cols;
	private int rows;
	
	private int startX;
	private int startY;
	private int targetX;
	private int targetY;
	
	private int currX;
	private int currY;
	
	private Queue<Node> all_rects;
	private HashSet<Node> visitedNodes;
	private AnimationTimer timer;
	private ArrayList<ArrayList<Node>> path;
	
	/**
	 * Constructor that initializes fields of BFS
	 * start an AnimationTimer that will animate visuals of BFS algorithm
	 * @param one -- start Node
	 * @param two -- target Node
	 * @param pane -- current GridPane
	 * @param cols -- number of columns in grid
	 * @param rows -- number of rows in grid
	 */
	public BFS(Node one, Node two, GridPane pane, int cols, int rows) {
		startNode = one;
		targetNode = two;
		this.pane = pane;
		this.cols = cols;
		this.rows = rows;
		
		visitedNodes = new HashSet<>();
		
		startX = pane.getColumnIndex(one);
		startY = pane.getRowIndex(one);
		
		targetX = pane.getColumnIndex(two);
		targetY = pane.getRowIndex(two);
		
		path = new ArrayList<>();
		for (int i = 0; i < this.pane.getChildren().size(); i++) {
			path.add(new ArrayList<>());
		}
		
		all_rects = new LinkedList<>();
		all_rects.add(startNode);
		visitedNodes.add(startNode);
		currX = startX;
		currY = startY;
		
		timer = new AnimateTimerBFS(this);
		timer.start();
	}
	
	/**
	 * Each time this method is called from handle(), it will add all neighboring nodes to the queue
	 * will color the current node yellow once it's done processing itself
	 */
	public void runBFS() {
		
		if ((currX != targetX || currY != targetY) && !all_rects.isEmpty()) {
			Node curr = all_rects.remove();
			currX = pane.getColumnIndex(curr);
			currY = pane.getRowIndex(curr);
			
			for (Node n : pane.getChildren()) {
				if (!visitedNodes.contains(n)) {
					int colIndex = pane.getColumnIndex(n);
					int rowIndex = pane.getRowIndex(n);
					
					if (Math.abs(colIndex - currX) <= 1 && Math.abs(rowIndex - currY) <= 1) {
						if (currX < cols - 1 && currX >= 0 && currY < rows - 1 && currY >= 0) {
							all_rects.add(n);
							visitedNodes.add(n);
							int index = this.pane.getChildren().indexOf(n);
							this.path.get(index).add(curr);
						}
					}
				}
			}
			
			Rectangle curr_rect = (Rectangle) curr;
			if (currX != targetX || currY != targetY) {
				curr_rect.setFill(Color.YELLOW);
			}
			
		} 
//		} else {
//			timer.stop();
//		}
		
		if (currX == targetX && currY == targetY) {
			this.all_rects.clear();
		}
		
	} 
	
	public void highlightPath() {
		int index = this.pane.getChildren().indexOf(targetNode);
		targetNode = this.path.get(index).get(0);
		Rectangle rect = (Rectangle) targetNode;
		rect.setFill(Color.ORANGE);
	}
	
	public Queue<Node> getContains() {
		return this.all_rects;
	}
	
	public ArrayList<ArrayList<Node>> getPath() {
		return this.path;
	}
	
	public AnimationTimer getTimer() {
		return this.timer;
	}
	
	public Node getStart() {
		return this.startNode;
	}
	
	public Node getTarget() {
		return this.targetNode;
	}
	
}

class AnimateTimerBFS extends AnimationTimer {
	/**
	 * Private fields of AnimateTimer class
	 */
	private BFS bfs;
	
	/**
	 * Constructor that initializes fields of AnimateTimer class
	 * @param bfs -- record of BFS object using the timer
	 */
	public AnimateTimerBFS(BFS bfs) {
		this.bfs = bfs;
	}

	/**
	 * Overwritten method for AnimationTimer
	 * each frame will call runBFS()
	 * @param now -- determines the length of each frame
	 */
	@Override
	public void handle(long now) {
		// TODO Auto-generated method stub
		if (!this.bfs.getContains().isEmpty()) {
			bfs.runBFS();
		} else {
			if (this.bfs.getTarget() != this.bfs.getStart()) {
				this.bfs.highlightPath();
			} else {
				this.bfs.getTimer().stop();
			}
		}
	}
	
}
