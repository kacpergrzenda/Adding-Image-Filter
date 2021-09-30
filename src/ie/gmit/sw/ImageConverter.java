package ie.gmit.sw;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * 
 * @author Kacper Grzenda
 * @version 1.0
 * @since 1.14
 * 
 *  ImageConverter does all the filtering of the images. 
 *  It takes any image or images in loops through them gets their RGB values
 *  and than filters them once filtering the Rgb values are restored into one values and
 *  it is set in the pixel at the x,y position you retrieved the pixel from
 * 
 * see Runner
 *
 */

public class ImageConverter extends Thread{
	//variables
	private int option ;
	private int counter = 0;
	private File list[];
	private File newlist[];
	private BufferedImage temp;
	private String directory;

	
	/**
	 * 
	 * Constructor that recieves a File String and Option
	 * 
	 * @param list
	 * @param directory
	 * @param option
	 */
	public ImageConverter(File list[], String directory, int option) {//constructor
		this.list = list;
		this.directory = directory;
		this.option = option;
	}

	/**
	 * Returns BufferedImage
	 * 
	 * @return BufferedImage
	 */
	public BufferedImage getImage(){//Getter
		return this.temp;
	}

	/**
	 * setImage sets the BufferedImage into the class BufferedImage
	 * 
	 * @param temp
	 */
	public void setImage(BufferedImage temp) {//Setter
		this.temp = temp;
	}

	/**
	 * Return File array
	 * 
	 * @return File array
	 */
	public File[] getNewConvertedList(){//Getter
		return this.newlist;
	}


	/**
	 * Run() loop through each image in the directory and then loops through each pixel
	 * of the image which is filtered and put back into the same x,y position and than the image
	 * is read back to the file
	 */
	public void run() {

		for(File file : list) {	//loop through each file in the array
			counter++;//increment
			try {
				BufferedImage image = ImageIO.read(new File(file.getAbsolutePath()));//get the image file path 
				setImage(image);//set it

				for (int y = 0; y < image.getHeight(); y++) { //Loop over the 2D image pixel-by-pixel
					for (int x = 0; x < image.getWidth(); x++) {
						int pixel = image.getRGB(x, y); //Get the pixel at an (x, y) coordinate
						pixel = apply(pixel,x,y);//call the apply method


						image.setRGB(x, y, pixel); //Set the pixel colour at (x, y) to the new pixel
					}
				}

				ImageIO.write(image, "png", new File( directory + "\\" +  "ConvertedImage" + counter + ".png"));//write the filtered image to file
			} catch (IOException e) {
				// TODO Auto-generated catch block 
				e.printStackTrace();
			}

		}
		System.out.println("Image Filtered and Saved to Directory with your Images! ");//sysout to inform user that the images have been filtered
	}



	/**
	 * 
	 * apply() receives a int pixel the pixel value and int x x position of pixel and int y y position of pixel 
	 * The filter the user chose previously will be applied by looping through the filter the pixel is split 
	 * into three rgb values and multiplied by the kernel after the loop it sets all negative rgb values to positive 
	 * and makes sure if the value is below 0 or above 255 it sets it back. The three rgb values are then
	 * put back into one single single pixel value 
	 * 
	 * @param pixel
	 * @param x
	 * @param y
	 * @return rgb which is a pixel value
	 */
	public int apply(int pixel, int x, int y) {
		//variables
		int[][] loop;
		int element = pixel;
		int r= 0,g= 0, b = 0;

		if(option == 1) {
			int[][] filter = {  {-1, -2, -1}, {0, 0, 0}, {1, 2, 1} };//SobelHorizontal
			loop = filter;
		}
		else if(option == 2){
			int[][] filter = {  {-1, 0, -1}, {-2, 0, 2}, {-1, 0, 1} };//Sobel
			loop = filter;
		}
		else if (option == 3) {
			int[][] filter = {  {-1, -1, 2},{-1, 2, -1},{2, -1, -1} };//Diagonal 45
			loop = filter;
		}
		else if (option == 4) {
			int[][] filter = { {0, -1, 0},{-1, 4, -1}, {0, -1, 0}};//laplacian
			loop = filter;
		}
		else {
			int[][] filter = { {1, 1, 1},{1, 1, 1}, {1, 1, 1}};//default
			loop = filter;
		}

		for (int row = 0; row < loop.length; row++) {//Loop through the 2d filter array
			for (int col = 0; col < loop[row].length; col++) {
				try {


					element = getImage().getRGB(x + col, y + row);

					//gettting the rgb values out of the pixel 
					int red = (element >> 16) & 0xff;
					int green = (element >> 8) & 0xff;
					int blue = element & 0xff;


					//multiplying each of the 3 rgb value by the cournel
					r += (loop[row][col] * red);
					g += (loop[row][col] *  green);
					b += (loop[row][col] * blue);

					//C:\Users\kacper\Desktop\images
				} catch (Exception e) {
					continue; //Ignore any exception and keep going. It’s good enough J
				}
			}
		}

		//If the values are a minus setting them as a plus 
		r = r*-1;
		g = g*-1;
		b = b*-1;

		//If the values are over 255 or below 0 set them back to 0 or 255 
		r = Math.min(Math.max((int)(r * 24),0),255);
		g = Math.min(Math.max((int)(g * 24),0),255);
		b = Math.min(Math.max((int)(b * 24),0),255);

		//Setting the single rgb values into a new RGB Full value
		int rgb = 0;
		rgb = rgb | (r << 16);
		rgb = rgb | (g << 8);
		rgb = rgb | b;

		return  rgb;

	}
}
