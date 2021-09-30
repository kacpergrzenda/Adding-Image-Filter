package ie.gmit.sw;

import java.io.File;

/**
 * 
 * @author Kacper Grzenda
 * @version 1.0
 * @since 1.14
 * 
 *  ImageDirectoryReader reads in a String directory equals the String Directory into
 *  a File DirectoryPath which than gets set into a File Array
 * 
 * see Runner
 *
 */

public class ImageDirectoryReader extends Thread{

	//Variables
	private String directory;
	private File filesList[];
	
	
	/**
	 * Constructor that receives a String
	 * 
	 * @param directory
	 */
	//Constructor
	public ImageDirectoryReader(String directory) {
		this.directory = directory;
	}

	/**
	 *  returns the file list array
	 * 
	 * @return fileList array
	 */
	public File[] getList(){//Getter
		return this.filesList;
	}

	/**
	 *  setList() sets the list array to list in class
	 * 
	 * @param list
	 */
	public void setList(File list[]) {//Setter
		this.filesList = list;
	}

	
	public void run() {
		//Creating a File object for directory
		File directoryPath = new File(directory);
		//List of all files and directories
		setList(directoryPath.listFiles());//set the List
		
		
	}



}
