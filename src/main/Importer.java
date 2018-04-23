package main;

import java.awt.Dimension;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Importer {

	/* Singleton variables */
	protected int instanceOf;
	
	/* IO objects */
	File fi;
	FileReader fr;

	/* UI objects */
	
	private Importer() throws SingletonException{
		instanceOf++;
		if(instanceOf > 1)
			throw new SingletonException();
	}
	
	public Map importFile(String path) throws IOException{
		
		fi = new File(path);
		if(!fi.exists())
			throw new IOException();
		
		/* Read file in */
		String smap = new String(Files.readAllBytes(
				Paths.get(fi.getPath())), StandardCharsets.UTF_8);
		
		/* Determine longest line and number of lines */
		int longest = 0;
		int lines = 0;
		int cur = 0;
		for(int i = 0; i < smap.length(); i++){
			
			if(smap.charAt(i) != '\n')
				cur++;
			else{
				if(cur > longest){
					longest = cur;
					cur = 0;
				}
				lines++;
			}	
		}
		
		/* Now we have the data we need to instantiate a map */
		Map<Cell> m = new Map<Cell>(new Dimension(longest, lines));		
		
		int p = 0;
		for(int i = 0; i < m.getHeight(); i++){
			for(int j = 0; j < m.getWidth(); j++){
				if( ( smap.charAt(p) == 'x' ) || ( smap.charAt(p) == 'X') )
					m.setAt(new Cell(true), i, j);
				else
					m.setAt(new Cell(false), i, j);
				p++;
			}
		}
		
		return m;
	}
}
