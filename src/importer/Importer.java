package importer;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
/* LOCAL IMPORTS */
import cellmap.*;
import exceptions.*;
import main.*;

public class Importer {

	private ImporterGUI gui;
	
	/* Singleton variables */
	protected int instanceOf;
	
	/* IO objects */
	private File fi;
	private Map<Cell> map;
	
	public Importer(){
		instanceOf++;
		if(instanceOf > 1)
			throw new SingletonException();
		
		fi = null;
	}
	
	/*
	 * Data Processing Methods
	 * */
	public Map<Cell> importFile(){
		
		if( fi == null)
			throw new NullPointerException();	/* This exception should never happen */
		
		String smap = null;
		
		try{
			this.checkFileVerify();
			smap = new String(Files.readAllBytes(Paths.get(fi.getPath())), StandardCharsets.UTF_8);
			this.checkCharsVerify(smap);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		/* DEBUG */System.out.print("Importer:\tfile read into 'smap'\t:\n\n" + smap.toString() + "\n\n");
		
		smap = smap.toLowerCase();
		
		/* Determine longest line and number of lines */
		int lines = 0;
		String tmap = "";
		String strnum = "";
		
		for(int i = 0; i < smap.length(); i++){
			
			if(smap.charAt(i) == '\n')
				lines++;
			
			else if( smap.charAt(i) == 'x' || smap.charAt(i) == 'o' )
				tmap = tmap + smap.charAt(i);
			
			else if( smap.charAt(i) <= '0' || smap.charAt(i) >= '9' )
				strnum = strnum + smap.charAt(i);
		}
		
		/* Now we have the data we need to instantiate a map */
		map = new Map<Cell>(new Dimension(smap.indexOf("\n"), lines));		
		
		int p = 0;
		for(int i = 0; i < map.getHeight(); i++){
			for(int j = 0; j < map.getWidth(); j++){
				if( ( tmap.charAt(p) == 'x' ) || ( smap.charAt(p) == 'X') )
					map.setAt(new Cell(true, map, i, j, Integer.getInteger(strnum) ), i, j);
				else if( ( tmap.charAt(p) == 'o' ) || ( smap.charAt(p) == 'O' ) )
					map.setAt(new Cell(false, map, i, j, Integer.getInteger(strnum) ), i, j);
				p++;
			}
		}
		
		
		for(int i = 0; i < map.getHeight()-1; i++){
			for(int j = 0; j < map.getWidth()-1; j++)
				map.getAt(i, j).initNeighbors();
		}
		
		return map;
	}
	
	/* Method Overload */
	public Map<Cell> importFile(File f) {
		fi = f;	
		return this.importFile();
	}
	
	private void checkFileVerify() throws IOException{
		
		/* DEBUG */System.out.print("Importer:\tChecking file...\n");
		
		if( ( !fi.exists() ) || ( fi.isDirectory() ) )
			throw new IOException("Valid file does not exist.");
		
		if(fi.length() > Driver.MAX_FILE_SIZE)
			throw new IOException("File is too large. Choose a file less than 1024 bytes.");
		
		/* DEBUG */System.out.print("Importer:\tFile verified.\n");
	}
	
	private void checkCharsVerify(String s) throws IOException{
		
		/* DEBUG */System.out.print("Importer:\tChecking characters...\n");
		
		for(int i = 0; i < s.length(); i++){
			boolean valid = false;
			int j = 0;
			while(j < Driver.VALID_CHARS.length){
				if(s.charAt(i) == Driver.VALID_CHARS[j])
					valid = true;
				j++;
			}
			if(!valid)
				throw new IOException("Invalid character: " + s.charAt(i) + "\n"
						+ "Valid characters are:\n"
						+ Driver.VALID_CHARS.toString());
		}
		
		/* DEBUG */System.out.print("Importer:\tCharacters verified.\n");
	}
	
	/*
	 * Setters
	 * */
	public void setSelectedFile(File f){ fi = f; }
	
	public void setGUI(ImporterGUI ui){ gui = ui; }
	
	/*
	 * Getters
	 * */
	public File getSelectedFile(){ return fi; }
	
	public Map<Cell> getImportedMap(){
		if(map == null)
			throw new NullPointerException();
		return map;
	}

}
