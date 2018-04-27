package main;

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
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Importer {

	/* Singleton variables */
	protected int instanceOf;
	
	/* IO objects */
	private File fi;

	/* UI objects */
	private JFrame frm;
	private JLabel lblheader;
	private JLabel lblstatus;
	private JPanel pnlcontrol;
	
	public Importer() throws SingletonException{
		instanceOf++;
		if(instanceOf > 1)
			throw new SingletonException();
		
		beginGUI();
		showFileChooser();
	}

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
		btnchoose.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				int status = fc.showOpenDialog(frm);
				
				if(status == JFileChooser.APPROVE_OPTION){
					fi = fc.getSelectedFile();
					lblstatus.setText("File Selected : " + fi.getName());
				} else {
					lblstatus.setText("Open command cancelled.");
				}
			}
		});
		
		pnlcontrol.add(btnchoose);
		frm.setVisible(true);
	}
	
	public File getSelectedFile(){
		if(fi == null)
			throw new NullPointerException();
		return fi;
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
	
	public Map<Cell> importFile(String path) throws IOException{
		
		fi = new File(path);
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
