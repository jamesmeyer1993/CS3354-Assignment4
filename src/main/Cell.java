package main;

public class Cell implements Runnable {

	private Map<Cell> container;
	
	private boolean curstate, prevstate;
	private int x, y;
	
	public Cell(boolean a, Map<Cell> m){
		curstate = a;
		prevstate = false;
		container = m;
	}
	
	public void run() {
		
	}
	
	public int[] getPosition(){
		int pos[] = {x, y};
		return pos;
	}
	
	public boolean getCurstate(){ return curstate; }
	
	public boolean getPrevstate(){ return prevstate; }
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/* For the run algorithm */
	private void setCurstate(boolean status){
		prevstate = curstate;
		curstate = status;
	}
	
}
