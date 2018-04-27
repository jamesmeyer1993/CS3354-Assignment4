package main;

import java.awt.Dimension;

public class Map<T>{

	private Dimension dim;
	private T data[][];
	
	public Map(Dimension d){
		dim = d;
		data = (T[][]) ( new Object[dim.width][dim.height] );
	}
	
	public T getAt(int x, int y){ return data[x][y]; }
	
	public Dimension getDimension(){ return dim; }
	
	public int getWidth(){ return dim.width; }
	
	public int getHeight(){ return dim.height; }
	
	public void setAt(T elem, int x, int y){ data[x][y] = elem; }
	
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
