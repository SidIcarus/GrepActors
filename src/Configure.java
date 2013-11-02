import akka.actor.ActorRef;

public class Configure {
	private String name;
	private String pattern;
	private ActorRef cActor;
	public Configure(String name, String pattern, ActorRef cActor){
		this.name = name;
		this.pattern = pattern;
		this.cActor = cActor;	
	}

}
