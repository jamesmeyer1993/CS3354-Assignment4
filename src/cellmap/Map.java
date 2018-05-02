package cellmap;

import java.awt.Dimension;


public class Map<T>{

	private Dimension dim;
	private T data[][];
	private int numOfElem;
	
	public Map(Dimension d){
		dim = d;
		data = (T[][]) ( new Object[dim.width][dim.height] );
	}
	
	public T getAt(int x, int y){ 
		
		try{
			if(data[x][y] == null)
				throw new NullPointerException();
		}	
		catch(NullPointerException e){
			System.err.print("Map:\tElement at ( " + x + " , " 
					+ y + " ) does not exist.\n" + e.getStackTrace());
		}
				
		return data[x][y];
	}
	
	public Dimension getDimension(){ return dim; }
	
	public int getWidth(){ return dim.width; }
	
	public int getHeight(){ return dim.height; }
	
	public int getNumberofElements(){ return numOfElem; }
	
	public void setAt(T elem, int x, int y){ 
		try{
			data[x][y] = elem;
			numOfElem++;
		}
		catch (NullPointerException e){
			System.err.print("Map:\tElement at ( " + x + " , " 
					+ y + " ) does not exist.\n" + e.getStackTrace());		}
	}
	
	public String toString(){
		String str = "";
		for(int i = 0; i < dim.height; i++){
			for(int j = 0; j < dim.width; j++){
				if( ((Cell) data[i][j]).getCurstate() )
					str = str + "X";
				else
					str = str + "O";
			}
			str = str + "\n";
		}
		return str;
	}
	
}
