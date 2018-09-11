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
		imageArray = new Pixel[w][h];
		height = h;
		width = w;
	}
	public void setPixel(Pixel newPix, int x, int y) {
		imageArray[x][y] = newPix;
	}
	public void doGrayscale() {
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				imageArray[i][j].setToGrayscale();
			}
		}
	}
	public void doInvert() {
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				imageArray[i][j].invert();
			}
		}
	}
	
	public Image doEmboss() {
		
		Image embossed = new Image();
		embossed.setImageArraySize(height,  width);
		
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				Pixel current = imageArray[i][j];
				Pixel topLeft;
				if (i == 0 || j == 0) {
					topLeft = new Pixel(128, 128, 128);
				}
				else {
					topLeft = imageArray[i - 1][j - 1];
				}
				int[] cArray = current.getData();
				int[] tlArray = topLeft.getData();
				
				int redDiff = cArray[0] - tlArray[0];
				int greenDiff = cArray[1] - tlArray[1];
				int blueDiff = cArray[2] - tlArray[2];
				
				int redDiffAbs = Math.abs(redDiff);
				int greenDiffAbs = Math.abs(greenDiff);
				int blueDiffAbs = Math.abs(blueDiff);
				
				int maxDiffColor = getLargestDifference(redDiffAbs, greenDiffAbs, blueDiffAbs);
				int maxVal = 0;
				
				if (maxDiffColor == 0) {
					maxVal = redDiff;
				}
				else if (maxDiffColor == 1) {
					maxVal = greenDiff;
				}
				else if (maxDiffColor == 2) {
					maxVal = blueDiff;
				}
				
				int v = 128 + maxVal;
				
				if (v < 0) {
					v = 0;
				}
				if (v > 255) {
					v = 255;
				}
				Pixel newPixel = new Pixel(v, v, v);
				embossed.setPixel(newPixel,  i,  j);
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
			return r;
		}
		return 10;
	}
	
	public Image doBlur(int b) {
		Image blurred = new Image();
		blurred.setImageArraySize(height,  width);
		
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				int redAvg = getAverageValue(0, i, j, b);
				int greenAvg = getAverageValue(1, i, j, b);
				int blueAvg = getAverageValue(2, i, j, b);
				
				Pixel newPixel = new Pixel(redAvg, greenAvg, blueAvg);
				blurred.setPixel(newPixel,  i,  j);
			
			}
		}
		
		return blurred;
		
	}
	
	public int getAverageValue(int color, int x, int y, int b) {
		int v = 0;
		
		if ((y + (b - 1)) > width) {
			b = width - y;
		}
		for (int i = y; i < y + b - 1; ++i) {
			int[] pixelVals = imageArray[x][i].getData(); //issue here- accessing out of bounds elements I think?
			if (color == 0) {
				v = v + pixelVals[0];
			}
			else if (color == 1) {
				v = v + pixelVals[1];
				
			}
			else if (color == 2) {
				v = v + pixelVals[2];
			}
		}
		
		v = v/b;
		
		
		return v;
	}
	public String toString() {
		String r = "";
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				r = r + imageArray[i][j].toString();
			}
			r = r + "\n";
		}
		return r;
	}
	
	public ArrayList<Integer> outputData() { //not working
		ArrayList<Integer> r = new ArrayList<Integer>(1);

		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				int[] pixInfo = imageArray[i][j].getData();
				for (int k = 0; k < pixInfo.length; ++k) {
					r.add(pixInfo[k]);
				}
			}
		}
		
		return r;
	}
}
