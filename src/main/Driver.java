/* Any cell with 3 live neighbors comes to life
 * 
 * */

package main;

import java.io.IOException;

public class Driver {

	public static final char VALID_CHARS[] = {'X','x','O','o','\n',(char)10,'0','1','2','3','4','5','6','7','8','9'};
	public static final int MAX_FILE_SIZE = 1024;
	
	static Map<Cell> cmap;
	
	public static void main(String[] args) {

		Importer imp = new Importer();
		try {
			cmap = imp.importFile(imp.getSelectedFile().getAbsolutePath());
			System.out.print(cmap.toString());
		} catch (IOException e) {e.printStackTrace();}
		
	}

}
