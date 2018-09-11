import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ImageEditor {

	public static void main(String[] args) throws IOException {
		
		File input = null;
		File output = null;
		
		if (0 < args.length) {
			input = new File(args[0]); 
	        output = new File(args[1]);
		} else {
		   System.err.println("USAGE: java ImageEditor infile outfile (mode blur-length)");
		}
		
		Scanner myScan;
		myScan = new Scanner(input).useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
		
		FileWriter myWriter = new FileWriter(output);
		String outString = "";
		StringBuilder s = new StringBuilder();
		
		
		String header = myScan.next();
		int width = Integer.parseInt(myScan.next());
		int height = Integer.parseInt(myScan.next());
		int maxVal = Integer.parseInt(myScan.next());
		
		outString = header + " " + width + " " + height + " " + maxVal + "\n";
		s.append(outString);
		
        ArrayList<Integer> inputList = new ArrayList<Integer>(1); 	
       
        while (myScan.hasNext()) { //scans data from input file and makes an int array
			String current = myScan.next();
			inputList.add(Integer.parseInt(current));
		}
       
        int i = -1;
        ArrayList<Pixel> pixelList = new ArrayList<Pixel>(); //takes data from int array and turns it into pixel array. I can cut speed by combining this with the function below. 
        while (i < inputList.size() - 3) {
        	Pixel newPixel = new Pixel();
        	newPixel.setRGB(inputList.get(++i),  inputList.get(++i), inputList.get(++i));
        	pixelList.add(newPixel);
        }
        
        Image myImage = new Image();
        myImage.setImageArraySize(height,  width);
        
        int l = 0;
        while (l < pixelList.size()) { //sets pixels into image array
        	for (int k = 0; k < width; ++k) {
        		for (int j = 0; j < height; ++j) {
        			myImage.setPixel(pixelList.get(l), k, j);
        			++l;
        		}
        	}
        }
        
        //IMPLEMENT MODIFICATIONS
        String mod = args[2];
        if(mod.equals("invert")) { //issue comparing argument to stuff
        	myImage.doInvert();
        }
        else if (mod.equals("grayscale")) {
        	myImage.doGrayscale();
        }
        else if (mod.equals("emboss")) {
        	myImage = myImage.doEmboss();
        }
        else if (mod.equals("motionblur")) {
        	int blurAmount = Integer.parseInt(args[3]);
        	myImage = myImage.doBlur(blurAmount);
        }
        
        
                
        ArrayList<Integer> outList = myImage.outputData();
        for (int o = 0; o < outList.size(); ++o) {
        	s.append(outList.get(o) + "\n");
        }
        
        outString = s.toString();
   
        myWriter.write(outString);
		
		myScan.close();
		myWriter.close();
		//System.out.println(outList);
		System.out.println( "output size: " + outList.size());
		System.out.println("width: " + width + " height: " + height);
		System.out.println("Pixels: " + pixelList.size());
		System.out.println("Done!");
		
		

	}

}
/*

note: may need to fix where the program looks for the input file later. 

Pseudocode:
Open input file
Open output file
Set up scanner and filewriter
Read in header, height, width and maxval and add them to the output string

Add the rest of the file into an int arraylist //can I do this in one go?
put the contents of the arraylist into pixels
add the pixels to the image

transform image 2d array into arraylist of ints
add arraylist contents to output string


*/
