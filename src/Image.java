import java.util.ArrayList;

public class Image {
	private int height;
	private int width;
	private Pixel [][] imageArray;
	
	public Image() {
		height = 0;
		width = 0;
	}
	public void setImageArraySize(int h, int w) {
		imageArray = new Pixel[h][w];
		height = h;
		width = w;
	}
	public void setPixel(Pixel newPix, int x, int y) {
		imageArray[x][y] = newPix;
	}
	public void doGrayscale() {
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				imageArray[i][j].setToGrayscale();
			}
		}
	}
	public void doInvert() {
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				imageArray[i][j].invert();
			}
		}
	}
	
	public Image doEmboss() {
		
		Image embossed = new Image();
		embossed.setImageArraySize(height,  width);
		
		for (int i = 0; i < height; ++i) { 
			for (int j = 0; j < width; ++j) {
				Pixel current = imageArray[i][j];
				Pixel embPixel = null;
				
				//if at top or left side
				if (i == 0 || j == 0) {
					embPixel = new Pixel(128, 128, 128);
				}
				else {
					
					Pixel topLeft = imageArray[i - 1][j - 1];
					
					//get the differences between current and top left pixels
					int redDiff = current.getData()[0] - topLeft.getData()[0];
					int greenDiff = current.getData()[1] - topLeft.getData()[1];
					int blueDiff = current.getData()[2] - topLeft.getData()[2];
					
					//get the absolute values for comparison
					int redMaxDiff = Math.abs(redDiff);
					int greenMaxDiff = Math.abs(greenDiff);
					int blueMaxDiff = Math.abs(blueDiff);
					
					//get which color has the maximum difference
					int maxDiffColor = getLargestDifference(redMaxDiff, greenMaxDiff, blueMaxDiff);
					
					//add 128 to the color with the largest difference and make that the final value
					int finalValue = 0;
					if (maxDiffColor == 0) {
						finalValue = redDiff + 128;
					}
					else if (maxDiffColor == 1) {
						finalValue = greenDiff + 128;
					}
					else {
						finalValue = blueDiff + 128;
					}
					
					//make sure the final value is in proper bounds 
					if (finalValue < 0) {
						finalValue = 0;
					}
					if (finalValue > 255) {
						finalValue = 255;
					}
					
					embPixel = new Pixel(finalValue, finalValue, finalValue);
				}
				embossed.setPixel(embPixel, i, j);
			}
		}
		return embossed;
	}
	public int getLargestDifference(int r, int g, int b) {
		//this should do the proper favoring
		if ((r >= g) && r >= b) {
			return 0;
		}
		else if ((g >= r) && (g >= b)) {
			return 1;
		}
		else if ((b >= r) && (b >= g)) {
			return 2;
		}
		return 0;
	}
	
	public Image doBlur(int b) {
		Image blurred = new Image();
		blurred.setImageArraySize(height,  width);
		
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
			
				Pixel newPixel = getAverageValue(i, j, b);
				blurred.setPixel(newPixel,  i,  j);
			
			}
		}
		
		return blurred;
		
	}
	
	public Pixel getAverageValue(int x, int y, int b) {
		Pixel returnPixel = new Pixel();
		int redAvg = 0;
		int greenAvg = 0;
		int blueAvg = 0;
		
		//make sure blur is in bounds
		if((y + b) >= width) { //somehow this returns b = 0
			b = width - y;
		}
		
		//get the average values
		for (int i = y; i < y + b; ++i) {
			int[] pixelVals = imageArray[x][i].getData();
			redAvg += pixelVals[0];
			greenAvg += pixelVals[1];
			blueAvg += pixelVals[2];
		}
		redAvg = redAvg/b; 
		greenAvg = greenAvg/b;
		blueAvg = blueAvg/b;
		returnPixel.setRGB(redAvg,  greenAvg, blueAvg);
		return returnPixel;
	}
	
	public String toString() {
		String r = "";
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				r = r + imageArray[i][j].toString();
			}
			r = r + "\n";
		}
		return r;
	}
	
	public ArrayList<Integer> outputData() { 
		ArrayList<Integer> r = new ArrayList<Integer>(1);

		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				int[] pixInfo = imageArray[i][j].getData();
				for (int k = 0; k < pixInfo.length; ++k) {
					r.add(pixInfo[k]);
				}
			}
		}
		
		return r;
	}
	
	public void Test() {
		imageArray[0][3] = new Pixel(255, 0, 0);
		imageArray[3][0] = new Pixel(0, 255, 0);
		imageArray[2][1] = new Pixel (0, 0, 255);
	}
} 

/*
 * TO DO:
 * Embossed totally freakin works my guy!
 * divide by 0 exception in my get average value
 */