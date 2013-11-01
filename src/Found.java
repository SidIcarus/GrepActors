import java.util.List;


/**
 * Class file for Found object, an object used to hold all found items in a file
 */
public class Found {
	
	private String name;
	private List<String> list;
	
	/**
	 * @param name, name of the file
	 * @param list, a list of entries for each matching line in the file. Each string in
	 * this list contains the line number, a space, and the text of the line
	 * 
	 * Creates a found object based on the above two parameters
	 */
	public Found(String name, List<String> list){
		this.name = name;
		this.list = list;
	}
	
	/**
	 * @return the name of the file
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * @return the list of strings for any matching line
	 */
	public List<String> getList(){
		return this.list;
	}
	
	/**
	 *@return returns a string of the file name and the entries in the list 
	 */
	public String toString(){
		String output = name +" .";
		for(int i = 0; i < list.size(); i++){
			output = output + list.get(i) + " .";
		}
		return output;
		
	}

}
