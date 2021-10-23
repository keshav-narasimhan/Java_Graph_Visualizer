package graph_visuals;

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
	Node startNode;
	Node targetNode;
	GridPane pane;
	int cols;
	int rows;
	
	int startX;
	int startY;
	int targetX;
	int targetY;
	
	int currX;
	int currY;
	
	Queue<Node> all_rects;
	HashSet<Node> visitedNodes;
	AnimationTimer timer;
	
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
		
		all_rects = new LinkedList<>();
		all_rects.add(startNode);
		visitedNodes.add(startNode);
		currX = startX;
		currY = startY;
		
		timer = new AnimateTimer(this);
		timer.start();
		
		// runBFS();
	}
	
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
						}
					}
				}
			}
			
			Rectangle curr_rect = (Rectangle) curr;
			if (currX != targetX || currY != targetY) {
				curr_rect.setFill(Color.YELLOW);
			}
			
		} else {
			timer.stop();
		}
		
	} 
	
}

class AnimateTimer extends AnimationTimer {
	BFS bfs;
	
	public AnimateTimer(BFS bfs) {
		this.bfs = bfs;
	}

	@Override
	public void handle(long now) {
		// TODO Auto-generated method stub
		
		bfs.runBFS();
	}
	
}
