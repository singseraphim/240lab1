
public class Pixel {
	private int red;
	private int green;
	private int blue;
	private int grayAvg;
	private int xval;
	private int yval;
	private final static int maxColorVal = 255;
	
	public Pixel() {
		red = 0;
		green = 0;
		blue = 0;
		grayAvg = 0;
		xval = 0;
		yval = 0;
	}
	public Pixel(int r, int g, int b) {
		red = r;
		green = g;
		blue = b;
		
	}
	
	public void setRGB(int r, int g, int b) {
		red = r;
		green = g;
		blue = b;
	}
	public void setGrayscaleValue() {
		grayAvg = (red + green + blue)/3;
	}
	public void setToGrayscale() {
		setGrayscaleValue();
		red = grayAvg;
		green = grayAvg;
		blue = grayAvg;
	}
	public void invert() {
		red = maxColorVal - red;
		green = maxColorVal - green;
		blue = maxColorVal - blue;
	}
	
	public String toString() {
		return "R: " + red + " G: " + green + " B: " + blue;
		
	}
	public int[] getData() {
		int[] data = new int[3];
		data[0] = red;
		data[1] = green;
		data[2] = blue;
		
		return data;
	}
}
