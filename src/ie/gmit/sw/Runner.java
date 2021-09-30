package ie.gmit.sw;

import java.util.Scanner;

/**
 * 
 * @author Kacper Grzenda
 * @version 1.0
 * @since 1.8
 * 
 * The Runner Class is the main class that links all the other classes together.
 * Also links the Thread operators in this class and creates a thread in the go()
 * that runs all the methods and creates other Threads that run the other classes
 * also allows the user to input which options he wants to do and want filter he 
 * wants to apply to his filters
 * 
 * see ImageConverter
 * see ImageDirectoryReader
 *
 */




public class Runner{

	//Variables
	private int option;
	private int filterPicker;
	private volatile int threadCount =0;

	

	@SuppressWarnings("deprecation")
	public void go () throws Exception{
		Thread runThread = new Thread();
		runThread.setName("MainThread");
		runThread.start();
		do {

			menu();//call method
			Scanner sc = new Scanner(System.in);
			option = sc.nextInt();//block Until User Input

			threadCount++;//incrementati


			switch (option) {
			case 1:
				System.out.println("Please Enter File Directory.");
				String imageDirectory = sc.next();//User Input

				ImageDirectoryReader T = new ImageDirectoryReader(imageDirectory);//Create a threaded Object
				T.setName("T-" +threadCount);//set Thread Name
				T.start();//start thread
				T.join();//wait  until the thread is dead to Continue

				filterMenu();//call method
				filterPicker = sc.nextInt();//block Until User Input
				ImageConverter C = new ImageConverter(T.getList(), imageDirectory, filterPicker);//Create a threaded Object
				C.setName("T-" +threadCount);//set Thread Name
				C.start();//start thread


				break;
			case 2:
				System.out.println("Please Enter Single Image Path.");
				String imagePath = sc.next();//User input

				ImageDirectoryReader T2 = new ImageDirectoryReader(imagePath);//Create a threaded Object
				T2.setName("T-" +threadCount);//set Thread Name
				T2.start();//start thread
				T2.join();//wait  until the thread is dead

				filterMenu();//call Method
				filterPicker = sc.nextInt();//block Until User Input

				ImageConverter d = new ImageConverter(T2.getList(),imagePath, filterPicker);//Create a threaded Object
				d.setName("T-" +threadCount);//set thread name
				d.start();//start thread
				d.join();//wait until thread dies to continue

				break;
			case 4:

				break;
			default:

				break;
			}

		}while(option != 4);
		System.out.println("Thanks for Using The Program.");
		runThread.stop();
	}
	
	/**
	 * When menu() is called it prints out the menu options
	 * 
	 */
	public void menu(){
		System.out.println("***** Image Filtering System *****");
		System.out.println("1) Enter Image Directory");
		System.out.println("2) Select Single Image");
		System.out.println("4) Quit");
	}

	
	/**
	 * When filterMenu() is called it prints out the filter menu options
	 * 
	 */
	public void filterMenu(){
		System.out.println("What Filter would You like to apply to your images? ");
		System.out.println("1) Sobel Horizontal? ");
		System.out.println("2) Sobel? ");
		System.out.println("3) Diagonal 45 Lines?");
		System.out.println("4) Laplacian? ");
	}

	
	public static void main(String[] args) throws Exception {
		new Runner().go();//call go
	}

}
