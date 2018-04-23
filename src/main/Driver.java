/* Any cell with 3 live neighbors comes to life
 * 
 * */

package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

public class Driver {

	public static void select(){
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		
		//File fi = fc.getSelectedFile();
		//System.out.print("Path of file = " + fi.getAbsolutePath());
	}
	
	public static void main(String[] args) {
		select();
	}

}
