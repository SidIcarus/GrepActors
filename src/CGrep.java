import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class CGrep {

	public CGrep(){
		System.out.println("Test");
	}
	
	public void main(String[] args){
		int filecount = 0;
		if (args.length == 0){
			System.out.println("Incorrect input. Use the following format");
			System.out.println("java CGrep pattern [file . . .]");
		}
		String pattern = args[0];
		
		ArrayList<String> files = new ArrayList<String>();
		
		if (args.length > 1){
			for (int i = 0; i < args.length; i++){
				files.add(args[i]);
			}
			
			filecount = files.size();
		}
		
		
		
		
		ActorSystem system = ActorSystem.create("CGrep");
		ActorRef cActor = system.actorOf(new Props(CollectionActor.class));
		cActor.tell(new FileCount(filecount), cActor);
		
		if(!files.isEmpty()){
			for(int i = 0; i < files.size(); i++){
				ActorRef sActor = system.actorOf(new Props(ScanActor.class));
				sActor.tell(new Configure(files.get(i),pattern,cActor));
			}
		}
		else{
				ActorRef sActor = system.actorOf(new Props(ScanActor.class));
				sActor.tell(new Configure(null,pattern,cActor), cActor);
			}
		
		
	}
}
