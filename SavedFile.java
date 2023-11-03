/**
 * This is the SavedFile Class which allows the user to save models *
 */
public class SavedFile {
	/** count is an integer that counts the number of models saved.*/
	private int count = 0;
	/** countTemp is an integer that determines the ones digit of the number of the saved models.*/
	private int countTemp;
	/** savedGraphs is Formula[] that stores 10 graphs.*/
	private Formula[] savedGraphs = new Formula[10];
	
	/**
	 * This is the constructor for the SavedFile class
	 */
	public SavedFile() {
		
	}
	
	/**
	 * This method adds Formula data into the saved section 
	 * @param data is a Formula
	 */
	public void add(Formula data) {
		countTemp = count%10;
		savedGraphs[countTemp] = data;
		count++;
	}
	
	/**
	 * This method gets the data of a specific model from an array named savedGraphs
	 * @param dataNum is an integer
	 * @return savedGraph[dataNum] is a Formula array
	 */
	public Formula getData(int dataNum) {
		return savedGraphs[dataNum];
	}
	
	/**
	 * This method returns the number of data saved in the system
	 * @return count is an integer
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * This method checks whether there is another model name in the history with the same model name as the current model.  
	 * @param name is a String
	 * @return boolean
	 */
	public boolean isFound(String name) {
		if (count < 11) { //when there is less than 11 items saved
			for (int i = 0; i < count; i++) {
				if (name.equals(savedGraphs[i].getName())) {
					return true;
				}
			}
			return false;
		}else { //when there is more than 10 items saved, thus, only return the last 10
			for (int i = 0; i < 10; i++) {
				if (name.equals(savedGraphs[i].getName())) {
					return true;
				}
			}
			return false;
		}
		
	}
	
	
}
