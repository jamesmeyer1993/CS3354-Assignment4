/* Any cell with 3 live neighbors comes to life
 * 
 * */

package main;

/*
 * LOCAL IMPORTS
 * */
import importer.*;
import cellmap.*;

public class Driver {

	/* Final variables */
	public static final char VALID_CHARS[] = {'X','x','O','o','\n',(char)10,'0','1','2','3','4','5','6','7','8','9'};
	public static final int MAX_FILE_SIZE = 1024;
	
	/* Shared variables that ought to be integrated into some design pattern for better protection/access */
	public static int importer_constructor_status;
	
	static Map<Cell> cmap;
	
	/*	*	*	*	*	*	*	*	*	*	*	*	*	*	
	 * 	*	*	*	*	*	*	MAIN	*	*	*	*	*
	 * 	*	*	*	*	*	*	*	*	*	*	*	*	*
	 */
	public static void main(String[] args) {
	
		Importer imp = new Importer();
		ImporterGUI impui = new ImporterGUI(imp);
		
	}

}
