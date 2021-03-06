import akka.actor.ActorRef;

/**
 * Class that holds the file name, the pattern, and the CollectionActor.
 * An immutable object that is sent to ScanActors so they know what they are scanning for.
 * It sends a message to CollectionActor when it has found something.
 * 
 * @author Group 6
 */
public class Configure {
	private final String name;
	private final String pattern;
	private final ActorRef cActor;

	public Configure(String name, String pattern, ActorRef cActor){
		this.name = name;
		this.pattern = pattern;
		this.cActor = cActor;	
	}
	
	public String getName() {
		return name;
	}

	public String getPattern() {
		return pattern;
	}

	public ActorRef getcActor() {
		return cActor;
	}
}
