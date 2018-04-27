package cellmap;


public class Cell implements Runnable {

	private Map<Cell> container;
	
	private Cell neighbor[];
	private int neighbors;
	private boolean curstate, prevstate;
	private int x, y, generation;
	
	public Cell(boolean a, Map<Cell> m, int x, int y){
		curstate = a;
		prevstate = false;
		container = m;
		
		this.x = x;
		this.y = y;
		this.initNeighbors();
	}
	
	private void initNeighbors(){
		
		for(int i = 0; i < neighbor.length; i++)
			neighbor[i] = null;
		neighbors = 0;
		
		if( x+1 < 20 ){
			neighbor[0] = container.getAt(x+1, y);
			
			if(	y+1 < 20 ){
				neighbor[1] = container.getAt(x+1, y+1); neighbors++; }
			
			if( y-1 > -1 ){
				neighbor[2] = container.getAt(x+1, y-1); neighbors++; }
				
		} else { neighbor[0] = neighbor[1] = neighbor[2] = null;}
		
		if( x-1 > -1 ){
			neighbor[3] = container.getAt(x+1, y);
			
			if(	y+1 < 20 ){
				neighbor[4] = container.getAt(x+1, y+1); neighbors++; }
			
			if( y-1 > -1 ){
				neighbor[5] = container.getAt(x+1, y-1);  neighbors++; }
			
		} else { neighbor[3] = neighbor[4] = neighbor[5] = null;}
		
		if(	y+1 < 20){
			neighbor[6] = container.getAt(x, y+1); neighbors++; }
		
		if( y-1 > -1){
			neighbor[7] = container.getAt(x, y-1); neighbors++; }
		
		return;
	}
	
	public void run() {
		
	}
	
	public int[] getPosition(){ int pos[] = {x, y}; return pos; }
	
	public boolean getCurstate(){ return curstate; }
	
	public boolean getPrevstate(){ return prevstate; }
	
	public int getGeneration(){ return generation; }
	
	public int getNeighbors(){ return neighbors; }
	
	/* For the run algorithm */
	private void setCurstate(boolean status){
		prevstate = curstate;
		curstate = status;
	}
	
}
