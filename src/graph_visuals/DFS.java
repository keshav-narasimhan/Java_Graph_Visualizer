package graph_visuals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DFS {
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

	private HashSet<Node> visitedNodes;
	private AnimationTimer timer;
	private ArrayList<Node> nodesDFS;
	private ArrayList<Node> pathDFS;
	private ArrayList<ArrayList<Node>> path;
	
	
	public DFS(Node one, Node two, GridPane pane, int cols, int rows) {
		this.startNode = one;
		this.targetNode = two;
		this.pane = pane;
		this.cols = cols;
		this.rows = rows;
		
		this.nodesDFS = new ArrayList<>();
		nodesDFS.add(startNode);
		
		this.pathDFS = new ArrayList<>();
		pathDFS.add(startNode);
		
		this.visitedNodes = new HashSet<>();
		visitedNodes.add(startNode);
		
		this.path = new ArrayList<>();
		for (int i = 0; i < this.pane.getChildren().size(); i++) {
			path.add(new ArrayList<>());
		}
		
		startX = pane.getColumnIndex(one);
		startY = pane.getRowIndex(one);
		
		targetX = pane.getColumnIndex(two);
		targetY = pane.getRowIndex(two);
		
		currX = startX;
		currY = startY;
		
		runDFS();
		
		timer = new AnimateTimerDFS(this);
		timer.start();
		
	}
	
	public void runDFS() {
		recursionDFS(startNode);
	}
	
	public void recursionDFS (Node currNode) {
		
		int currNodeX = pane.getColumnIndex(currNode);
		int currNodeY = pane.getRowIndex(currNode);
		
		if ((currNodeX == targetX && currNodeY == targetY) || visitedNodes.contains(targetNode)) {
			return;
		}
		
		System.out.println(currNodeX + " " + currNodeY);
		for (int i = 0; i < this.pane.getChildren().size(); i++) {
			Node n = this.pane.getChildren().get(i);
			if (!visitedNodes.contains(n)) {
				int nX = this.pane.getColumnIndex(n);
				int nY = this.pane.getRowIndex(n);
				
				if (Math.abs(currNodeX - nX) <= 1 && Math.abs(currNodeY - nY) <= 1) {
					if (currNodeX < cols - 1 && currNodeX >= 0 && currNodeY < rows - 1 && currNodeY >= 0) { 
						visitedNodes.add(n);
						this.nodesDFS.add(n);
						pathDFS.add(n);
						
						int index = this.pane.getChildren().indexOf(n);
						this.path.get(index).add(currNode);
						
						recursionDFS(n);
						
					}
					
					if (!visitedNodes.contains(targetNode)) {
						pathDFS.remove(n);
					}
				}
			}
		}
		
	}
	
	public void highlightDFS() {
		Node curr = this.nodesDFS.remove(0);
		Rectangle rect = (Rectangle) curr;
		if (curr != targetNode) {
			rect.setFill(Color.YELLOW);
		}
	}
	
	public void highlightPath() {
//		Node curr = this.pathDFS.remove(0);
//		Rectangle rect = (Rectangle) curr;
//		if (curr != targetNode) {
//			rect.setFill(Color.DARKORANGE);
//		}
		
//		int index = this.pane.getChildren().indexOf(targetNode);
//		Node curr = this.path.get(index).get(0);
//		while (curr != startNode) {
//			Rectangle rect = (Rectangle) curr;
//			rect.setFill(Color.ORANGE);
//			index = this.pane.getChildren().indexOf(curr);
//			curr = this.path.get(index).get(0);
//		}
		
		int index = this.pane.getChildren().indexOf(targetNode);
		targetNode = this.path.get(index).get(0);
		Rectangle rect = (Rectangle) targetNode;
		rect.setFill(Color.ORANGE);
	}
	
	public ArrayList<Node> getNodes() {
		return this.nodesDFS;
	}
	
	public ArrayList<Node> getPath() {
		return this.pathDFS;
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

class AnimateTimerDFS extends AnimationTimer {
	private DFS dfs;
	
	public AnimateTimerDFS (DFS dfs) {
		this.dfs = dfs;
	}
	
	@Override
	public void handle(long now) {
		if (this.dfs.getNodes().isEmpty()) {
//			if (this.dfs.getPath().isEmpty()) {
//				this.dfs.getTimer().stop();
//			} else {
//				this.dfs.highlightPath();
//			}
			if (this.dfs.getTarget() == this.dfs.getStart()) {
				this.dfs.getTimer().stop();
			} else {
				this.dfs.highlightPath();
			}
		} else {
			this.dfs.highlightDFS();
		}
	} 
	
}
