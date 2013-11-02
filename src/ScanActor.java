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
 * @author Group 6
 */
public class ScanActor extends UntypedActor {

	/*
	 * Unused Constructor
	 */
	public ScanActor() {}
	
	@Override
	public void onReceive(Object arg0) throws Exception {
		
	}
	
	/*
	 * 
	 * 
	 * @param buffRead The BufferedReader sued for reading.
	 * @return A list of the text's lines with their number.
	 */
	private ArrayList<String> read(BufferedReader buffRead){
		ArrayList<String> text = new ArrayList<String>();
		
		return text;
	}
}
