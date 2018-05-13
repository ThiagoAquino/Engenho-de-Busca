package classify;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import weka.core.Instances;
import weka.core.converters.TextDirectoryLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class ClassifyBase {
	
	public static TextDirectoryLoader createFlatFile() throws IOException {
		TextDirectoryLoader loader = new TextDirectoryLoader();
		loader.setDirectory(new File("Examples/"));

		return loader;
	}
	
	public static Instances docToVector(TextDirectoryLoader loader) throws Exception{
		Instances dataReturn;
		Instances data = loader.getDataSet();
		
		 StringToWordVector filter = new StringToWordVector();
		 filter.setInputFormat(data);//converte para vetor de atributos == BagOfWords
		 dataReturn = Filter.useFilter(data, filter);
		 
		 return dataReturn;
	}
	
	public static void main(String[] args) throws Exception {
		// convert the directory into a dataset
		TextDirectoryLoader loader = createFlatFile();
	    Instances dataFiltered = docToVector(loader);
	    
	    //create flat file
	    PrintWriter writer = new PrintWriter("flatFileTest.arff", "UTF-8");
	    writer.println(dataFiltered);
	    writer.close();
	    
//	    
//	    System.out.println("\nNúmero de atributos: " + dataFiltered.numAttributes());
//	    System.out.println("\nQuantidade de classes: " + dataFiltered.numClasses());
//	    System.out.println("\nQuantidade de exemplos: " + dataFiltered.numInstances());
//	    
//	    
	   

	}
}
