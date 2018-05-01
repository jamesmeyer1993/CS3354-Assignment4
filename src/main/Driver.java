/* Any cell with 3 live neighbors comes to life
 * 
 * */

package main;

/*
 * LOCAL IMPORTS
 * */
import java.util.concurrent.TimeUnit;

import importer.*;
import cellmap.*;

public class Driver {

	/* Final variables */
	public static final char VALID_CHARS[] = {'X','x','O','o','\n','\r','0','1','2','3','4','5','6','7','8','9'};
	public static final int MAX_FILE_SIZE = 1024;
	
	/*	*	*	*	*	*	*	*	*	*	*	*	*	*	
	 * 	*	*	*	*	*	*	MAIN	*	*	*	*	*
	 * 	*	*	*	*	*	*	*	*	*	*	*	*	*
	 */
	public static void main(String[] args) {
	
		/* DEBUG */System.out.print("Driver:\tBegin main...\n");
		Importer imp = new Importer();
		ImporterGUI impui = new ImporterGUI(imp);
		imp.setGUI(impui);
	
		try {
			int sec = 0;
			while(imp.getSelectedFile() == null){
				/* DEBUG *///System.out.print("Driver:\timp.getSelectedFile() == null.\n");
				TimeUnit.SECONDS.sleep(1);
				sec++;
				if(sec > 300)
					throw new RuntimeException();
			}
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		catch (RuntimeException e)
		{
			e.printStackTrace();
		}
		
		/* DEBUG */System.out.print("Driver:\tReady to import.\n");
		Map<Cell> cmap = imp.importFile();
		
		System.out.print(cmap.toString()+"\n\n");
	}	

}
