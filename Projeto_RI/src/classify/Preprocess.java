package classify;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

public class Preprocess {
	//Ignore html 
	public static String getText(String pageWeb) {
		Document document = Jsoup.parse(pageWeb);
		return document.body().text();
	}
	
	static String readFile(String fileName) throws IOException {
	    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8")); 
	    		//new BufferedReader(new FileReader(fileName));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
	
	public static String preProcessFile(String path){
		String document = null;
		String content= null;
		try {
			content = readFile(path);
			document = Jsoup.parse(content).body().text();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}catch (IOException e) {
			e.printStackTrace();
		} 
		return document;
	}
	
	
	
	//Get html file to String
	public static String fileToString(File file) throws IOException {
		int len;
		char[] chr = new char[4096];
		final StringBuffer buffer = new StringBuffer();
		final FileReader reader = new FileReader(file);
		try {
			while ((len = reader.read(chr)) > 0) {
				buffer.append(chr, 0, len);
			}
		} finally {
			reader.close();
		}
		return buffer.toString();
	}
	//Read html files to getText without html
	public static void readFiles(String path) throws IOException{
		int count = 0;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		PrintWriter writer;
		
		for (File file : listOfFiles) {
		    if (file.isFile()) {
				String doc = fileToString(new File(file.getPath()));
		    	writer = new PrintWriter("Examples/Negative/negDoc"+count, "UTF-8");
				writer.println(getText(doc));
				writer.close();
		    	count++;
		    }
		}
	}
}
