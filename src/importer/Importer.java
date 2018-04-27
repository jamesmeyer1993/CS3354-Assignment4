package importer;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import cellmap.Cell;
import cellmap.Map;
import exceptions.SingletonException;

import main.Driver;

public class Importer {

	/* Singleton variables */
	protected int instanceOf;
	
	/* IO objects */
	private File fi;
	private Map<Cell> map;
	
	public Importer(){
		instanceOf++;
		if(instanceOf > 1)
			throw new SingletonException();
	}
	
	public Map<Cell> importFile(File f) throws IOException{
		
		fi = f;
		
		if( fi == null)
			throw new NullPointerException();	/* This exception should never happen */
		
		if( ( !fi.exists() ) || ( fi.isDirectory() ) )
			throw new IOException("Valid file does not exist.");
		
		if(fi.length() > Driver.MAX_FILE_SIZE)
			throw new IOException("File is too large. Choose a file less than 1024 bytes.");
		
		/* Read file in */
		String smap = new String(Files.readAllBytes(
				Paths.get(fi.getPath())), StandardCharsets.UTF_8);
		
		this.checkCharsVerify(smap);
		
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
					m.setAt(new Cell(true, m), i, j);
				else
					m.setAt(new Cell(false, m), i, j);
				p++;
			}
		}
		
		return m;
	}
	
	public File getSelectedFile(){
		if(fi == null)
			throw new NullPointerException();
		return fi;
	}
	
	public Map<Cell> getImportedMap(){
		if(map == null)
			throw new NullPointerException();
		return map;
	}
	
	private void checkCharsVerify(String s) throws IOException{
		
		for(int i = 0; i < s.length(); i++){
			boolean valid = false;
			int j = 0;
			while(j < Driver.VALID_CHARS.length){
				if(s.charAt(i) == Driver.VALID_CHARS[i])
					valid = true;
			}
			if(!valid)
				throw new IOException("Invalid character: " + s.charAt(i) + "\n"
						+ "Valid characters are:\n"
						+ Driver.VALID_CHARS.toString());
		}
	}
}
