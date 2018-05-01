package cellmap;


public class Cell implements Runnable {

	static protected int of_this_type_count;
	
	private Map<Cell> container;
	
	private Cell neighbor[];
	private int neighbors;
	private boolean curstate, prevstate;
	private int x, y, generation, range;
	
	public Cell(boolean a, Map<Cell> m, int x, int y, int r){
		of_this_type_count++;
		curstate = a;
		prevstate = false;
		container = m;
		range = r;
		
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
		
		if( x+1 < container.getWidth() ){
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
		
		if(	y+1 < container.getHeight() ){
			neighbor[6] = container.getAt(x, y+1); neighbors++; }
		
		if( y-1 > -1){
			neighbor[7] = container.getAt(x, y-1); neighbors++; }
		
		return;
	}
	
	public void run() {
		/*
		 * Any live cell with fewer than two live neighbors dies, as if caused by under-population.
		 * Any live cell with two or three live neighbors lives on to the next generation.
		 * Any live cell with more than three live neighbors dies, as if by overpopulation.
		 * Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
		 * 
		 * */
		
		while(generation < range){
		
			int alive = 0;
			
			for(int i = 0; i < neighbors; i++){
				
				if( neighbor[i].getGeneration() == this.generation && neighbor[i].getCurstate() == true )
					alive++;
				
				if( neighbor[i].getGeneration() > this.generation ){
					
					if( neighbor[i].getGeneration() - this.generation > 1 )
						throw new RuntimeException("Threads out of sync by greater than one generation.");
					
					if( neighbor[i].getPrevstate() == true )
						alive++;
				}
				
				if( neighbor[i].getGeneration() < this.generation ){
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			if( alive == 2 || alive == 3 )
				this.setCurstate(true);
			else
				this.setCurstate(false);
				
			generation++;
			
			try{ Thread.sleep(400); } catch(InterruptedException e){ e.printStackTrace(); }
		}
		
		return;
	}
	
	public int[] getPosition(){ int pos[] = {x, y}; return pos; }
	
	public boolean getCurstate(){ return curstate; }
	
	public boolean getPrevstate(){ return prevstate; }
	
	public int getGeneration(){ return generation; }
	
	public int getNeighbors(){ return neighbors; }
	
	/* For the run algorithm */
	private void setCurstate(boolean status){
		setPrevstate(curstate);
		curstate = status;
	}
	
	private void setPrevstate(boolean status){
		prevstate = status;
	}
	
}
