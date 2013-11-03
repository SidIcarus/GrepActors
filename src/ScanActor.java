import akka.actor.UntypedActor;
import akka.actor.ActorRef;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ScanActor is used to scan a given file for a given pattern
*
 * A ScanActor expects to receive exactly one immutable Configure message containing 
 * (a) a String with the name of the file to scan (or null for the standard input), and 
 * (b) an ActorRef to a CollectionActor, which collects and prints scan results. 
 * 		The actual format of the Configure objects is up to you.
 * Each ScanActor will construct an immutable Found object containing a String with the name of the file, 
 * 		and a List<String> with one entry in the list for each matching line. Each string in the list 
 * 		consists of the line number, a space, and the text of the line itself. The list must be ordered by 
 * 		location of the line in the file (i.e., first matching line at position 0, second matching line at 
 * 		position 1, etc.). The Found object is sent as the one and only message from the ScanActor to the 
 * 		CollectionActor.
 * @author Group 6
 */
public class ScanActor extends UntypedActor {
	private String fileName;
	private String patternString;
	private ActorRef cActor;
	private Pattern pattern;
	
	/*
	 * Unused Constructor
	 */
	public ScanActor() {}
	
	/*
	 * (non-Javadoc)
	 * @see akka.actor.UntypedActor#onReceive(java.lang.Object)
	 */
	@Override
	public void onReceive(Object arg0) throws Exception {
		if (arg0.getClass() == Configure.class){
			this.fileName = ((Configure) arg0).getName();
			this.patternString = ((Configure) arg0).getPattern();
			this.pattern = Pattern.compile(this.patternString);
			this.cActor = ((Configure) arg0).getcActor();
			
			//read files from stdin or the file provided
			if(this.fileName != null) {
				this.scanFile();
			} else {
				this.fileName = "stdin";
				this.scanStdIn();
			}
			
		}
	}
	
	/*
	 * Creates a BufferedReader for stdin and gives it to ScanActor#read() 
	 * to find pattern matches, then sends the Found message to the given 
	 * CollectionActor.
	 */
	private void scanStdIn(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		cActor.tell((new Found(this.fileName, this.read(br))), this.getSelf());
	}
	
	/*
     * Creates a BufferedReader for the given file and gives it to 
     * ScanActor.read() to find pattern matches in the file. Then, it 
     * sends the results in a Found message to the CollectionActor.
	 */
	private void scanFile(){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(this.fileName));
			cActor.tell((new Found(this.fileName, this.read(br))), this.getSelf());
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*
     * Reads using the given BufferedReader and checks for 
     * pattern matches line by line, storing them in an ArrayList.
     * Strings in the list have the format: "<line number> <line text>".
     * 
	 * @param buffRead The BufferedReader sued for reading.
	 * @return A list of the text's lines with their number.
	 */
	private ArrayList<String> read(BufferedReader buffRead){
		ArrayList<String> text = new ArrayList<String>();
		String textLine;
		int textLineNumber = 0;
		
		try {
			while((textLine = buffRead.readLine()) != null) {
				Matcher matcher = pattern.matcher(textLine);
				if(matcher.find())
					text.add(textLineNumber + " " + textLine);
				textLineNumber++;
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				buffRead.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return text;
	}
}
