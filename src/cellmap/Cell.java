package cellmap;


public class Cell implements Runnable {

	static protected int of_this_type_count;
	
	private Map<Cell> container;
	
	private Cell neighbor[];
	private int neighbors;
	private boolean curstate, prevstate;
	private int x, y, generation;
	
	public Cell(boolean a, Map<Cell> m, int x, int y){
		of_this_type_count++;
		curstate = a;
		prevstate = false;
		container = m;
		
		this.x = x;
		this.y = y;
		
		/* DEBUG */
		if(of_this_type_count%8 == 0) 
			System.out.print("\n\t");
		
		System.out.print("Cell_" + of_this_type_count + ": "
			+ "instantiated at (" + this.x + "," + this.y + ")\t");
		/* END DEBUG */
	}
	
	public void initNeighbors(){
		
		neighbor = new Cell[8];
		for(int i = 0; i < 8; i++)
			neighbor[i] = null;
		neighbors = 0;
		
		if( x+1 < container.getWidth()-1 ){
			neighbor[0] = container.getAt(x+1, y);
			
			if(	y+1 < container.getHeight()-1 ){
				neighbor[1] = container.getAt(x+1, y+1); neighbors++; }
			
			if( y-1 > -1 ){
				neighbor[2] = container.getAt(x+1, y-1); neighbors++; }
				
		} else { neighbor[0] = neighbor[1] = neighbor[2] = null;}
		
		if( x-1 > -1 ){
			neighbor[3] = container.getAt(x+1, y);
			
			if(	y+1 < container.getHeight() ){
				neighbor[4] = container.getAt(x+1, y+1); neighbors++; }
			
			if( y-1 > -1 ){
				neighbor[5] = container.getAt(x+1, y-1);  neighbors++; }
			
		} else { neighbor[3] = neighbor[4] = neighbor[5] = null;}
		
		if(	y+1 < container.getHeight()-1 ){
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
