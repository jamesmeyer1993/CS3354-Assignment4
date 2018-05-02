/* Any cell with 3 live neighbors comes to life
 * 
 * */

package main;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit.*;
/*
 * LOCAL IMPORTS
 * */
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
		
		
		System.out.print("\n\n" + cmap.toString()+"\n\n");
		
		Thread t[] = new Thread[cmap.getNumberofElements()];
		int position = 0;
		
		for(int i = 0; i < cmap.getWidth(); i++){
			for(int j = 0; j < cmap.getHeight(); j++){
				t[position] = new Thread(cmap.getAt(i, j), "Cell"+position);
				position++;
			}
		}
		
		for(Thread k : t)
			k.start();
		
		for(Thread k : t){
			try {
				k.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.print("\n\n" + cmap.toString()+"\n\n");
		
		/* DEBUG */System.out.print("Driver:\tDone.");
	}	

}
