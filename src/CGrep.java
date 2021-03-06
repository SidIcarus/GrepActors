import java.util.ArrayList;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @author Group 6
 *
 * Main file for GrepActors. Takes in command line arguments for 
 * the pattern and file names that creates a Collection Actor with a Filecount, 
 * and a ScanActor for each file.
 */
public class CGrep{
	/**
	 * @param args, args[0] = pattern to be searched
	 * 				args[1-n] = file names to search for the pattern
	 */
	public static void main(String[] args){
		//stores the filecount from the command line argument
		int filecount = 0;		
		
		if (args.length == 0){
			System.out.println("Invalid input. Correct Format: java CGrep pattern [file . . .]");
			System.exit(0);
		}
		
		String pattern = args[0];	//the pattern to be searched
		ArrayList<String> files = new ArrayList<String>();
		
		if (args.length > 1){		//adds the file names to the array list
			for (int i = 1; i < args.length; i++)
				files.add(args[i]);
			
			filecount = files.size();
		}
		
		// Creates the Collection Actor and tells it the filecount
		ActorSystem system = ActorSystem.create("CGrep");
		ActorRef cActor = system.actorOf(Props.create(CollectionActor.class));
		cActor.tell(new FileCount(filecount), cActor);
		
		// Creates the ScanActor for each file. If no files, sends a null
		if(!files.isEmpty()){
			for(int i = 0; i < files.size(); i++){
				ActorRef sActor = system.actorOf(Props.create(ScanActor.class));
				sActor.tell(new Configure(files.get(i),pattern,cActor), cActor);
			}
		} else {
			ActorRef sActor = system.actorOf(Props.create(ScanActor.class));
			sActor.tell(new Configure(null,pattern,cActor), cActor);
		}
	}
}
