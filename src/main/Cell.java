package main;

public class Cell implements Runnable {

	boolean alive;
	int x, y;
	
	public Cell(boolean a){
		alive = a;
	}
	
	public void run() {
		
	}
	
	public int[] getPosition(){
		int pos[] = {x, y};
		return pos;
	}
	
	public boolean isAlive(){ return alive; }
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setAlive(boolean a){ alive = a; }
	
}
