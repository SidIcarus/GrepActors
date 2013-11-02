/**
 * 
 * @author Stefan Neamtu
 *
 *This class holds the number of files, and is used as a message to be sent to 
 *CollectionActor. It is immutable
 */
public class FileCount {
	public final int filecount;
	
	public FileCount(int filecount){
		this.filecount = filecount;
		
	}
}
