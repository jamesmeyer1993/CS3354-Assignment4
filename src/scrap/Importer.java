package scrap;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cellmap.Cell;
import cellmap.Map;
import exceptions.SingletonException;

public class Importer {

	/* Singleton variables */
	protected int instanceOf;
	
	/* IO objects */
	private File fi;
	private Map<Cell> map;
	
	/* UI objects */
	private JFrame frm;
	private JLabel lblheader;
	private JLabel lblstatus;
	private JPanel pnlcontrol;
	
	public Importer() throws SingletonException, InterruptedException{
		instanceOf++;
		if(instanceOf > 1)
			throw new SingletonException();
		
		/* GUI Operations */
		beginGUI();
		showFileChooser();
		
		/* Store the file and the map.
		 * First, we wait for the user to switch the status of fi from null to some selected object. */
		int seconds = 0;
		while(fi == null){
			System.out.print("\tImporter\t: Step 1\t: File == null\n");
			TimeUnit.SECONDS.sleep(1);
			
			/* This loop will iterate for a max of 5 minutes */
			seconds++;
			if(seconds > 300)
				throw new InterruptedException("Importer\t: Program has been idle for 5 minutes.\n\n+" +
						"Importer\t: Exiting...\n");
		}
		
		System.out.print("\tImporter\t: Step 2\t:\n");
		try 
		{ 
			map = this.importFile();
			System.out.print("\tImporter\t: Step 3\t:\n");
			System.out.print(map.toString()+"\n\n");
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

	/* GUI Methods 
	 *	TODO: break GUI and data portions into separate classes. This class is doing too much. 
	 */
	
	private void beginGUI(){
		frm = new JFrame("Game of Life : File Selection");
		frm.setSize(400,400);
		frm.setLayout(new GridLayout(3,1));
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lblheader = new JLabel("", JLabel.CENTER);
		lblstatus = new JLabel("", JLabel.CENTER);
		lblstatus.setSize(350,100);
		pnlcontrol = new JPanel();
		pnlcontrol.setLayout(new FlowLayout());
		frm.add(lblheader);
		frm.add(pnlcontrol);
		frm.add(lblstatus);
		frm.pack();
		frm.setVisible(true);
	}
	
	private void showFileChooser(){
		lblheader.setText("Select a file.");
		
		final JFileChooser fc = new JFileChooser();
		JButton btnchoose = new JButton("Open File");
		
		/* Begin ActionListener */
		btnchoose.addActionListener(new ActionListener()	
		{
			/* This method occurs on a different thread */
			public void actionPerformed(ActionEvent event) {
				
				int status = fc.showOpenDialog(frm);
				
				if(status == JFileChooser.APPROVE_OPTION){
					fi = fc.getSelectedFile();
					lblstatus.setText("File Selected : " + fi.getName());
					frm.setVisible(false);
					frm.dispose();
				} else {
					lblstatus.setText("Open command cancelled.");
				}
			}
		});
		/* End ActionListener*/
		
		pnlcontrol.add(btnchoose);
		frm.setVisible(true);
	}
	
	/* Getters */
	
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
	
	private Map<Cell> importFile() throws IOException{
		
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
}
