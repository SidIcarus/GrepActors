import akka.actor.Actors;
import akka.actor.UntypedActor;

/**
 * The CollectionActor receives two types of messages. The first, of class FileCount (which should be received 
 * exactly once), contains a count of the number of files being scanned. The format of this immutable message 
 * object are up to you. The remaining messages are Found objects, which, upon receipt, are printed by the 
 * CollectionActor. Printout consists of the file name (or "-" for standard input) and the list of matching lines.
 *  When all the Found messages have been processed, the CollectionActor shuts down all actors using
 *   Actors.registry().shutdown() - see page 186 in PCJVM for an example.
 * @author Group 6
 */
public class CollectionActor extends UntypedActor {
	
	private int numOfFiles;
	private int founds;
	
	public CollectionActor(){
		
	}

	@Override
	public void onReceive(Object arg0) throws Exception {
		if(arg0.getClass() == FileCount.class) {
			this.numOfFiles = ((FileCount) arg0).filecount;
		} else if(arg0.getClass() == Found.class) {
			Found found = (Found)arg0;
			
			if(!found.getList().isEmpty()) {
				System.out.println("Matching lines for " + found.getName() + ":");
				for(String s : found.getList()) {
					System.out.println(s);
				}
			} else {
				System.out.println("No matching lines were found for " + found.getName() + ".");
			}
			
			if(founds++ >= numOfFiles) {
				//this.getContext().system().shutdown();
				Actors.registry().shutdownAll();
				
			}
		}
	}

	

}
