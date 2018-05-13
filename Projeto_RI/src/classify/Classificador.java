package classify;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.SparseInstance;
import weka.core.converters.ArffLoader.ArffReader;
import weka.filters.supervised.instance.Resample;

public class Classificador {
	private Classifier classifier;
	private Instances instances;
	public String[] attributes;
	private static ObjectInputStream objectInputStream;
	private static final String pathArff = "..//Projeto_RI//src//classify//arffs//flat500.arff";
	public Classificador(Classifier classifier, Instances instances, String[] attributes) {
		this.classifier = classifier;
		this.instances = instances;
		this.attributes = attributes;
	}
	
	public static Classificador getC(){
		Classificador c  =null;
		try {
			c = getClassify();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return c;
	}

	/*
	 * Metodo retorna se uma pagina pertence `a classe positiva
	 */
	public boolean classify(String page) throws Exception {
		boolean relevant = false;
		double[] values = getValues(page);
		weka.core.Instance instanceWeka = new SparseInstance(1, values);
		instanceWeka.setDataset(instances);
		double classificationResult = classifier.classifyInstance(instanceWeka);
		if (classificationResult == 0) {
			relevant = true;
		} else {
			relevant = false;
		}
		return relevant;
	}

	/*
	 * Metodo retorna as probabilidades da pagina pertencer `as classes positiva
	 * e negativa
	 */
	public double[] distributionForInstance(String page) throws Exception {
		double[] result = null;
		double[] values = getValues(page);
		System.out.println(values.length + "================================");
		weka.core.Instance instanceWeka = new SparseInstance(1, values);
		instanceWeka.setDataset(instances);
		result = classifier.distributionForInstance(instanceWeka);
		return result;
	}

	private double[] getValues(String pagina) throws IOException {
		double[] values = new double[this.attributes.length - 1];

		Scanner inFile = new Scanner(pagina);

		ArrayList<String> lisTokens = new ArrayList<String>();
		while (inFile.hasNext()) {
			String token = inFile.next();
			// System.out.println(token);
			lisTokens.add(token);
		}

		Map<String, Integer> map = new HashMap<>();
		for (String w : lisTokens) {
			Integer n = map.get(w);
			n = (n == null) ? 1 : ++n;
			map.put(w, n);
		}

		for (int i = 0; i < this.attributes.length - 1; i++) {
			if (map.containsKey(this.attributes[i])) {
				values[i] = map.get(this.attributes[i]);
			} else {
				values[i] = 0;
			}
		}
		return values;
	}	

	public static Classificador getClassify() throws IOException, ClassNotFoundException{
		Classificador classificador;
		BufferedReader reader = new BufferedReader(new FileReader(pathArff));
		ArffReader arff = new ArffReader(reader);
		String[] atts = new String[arff.getData().numAttributes() - 1];
		for (int i = 0; i < arff.getData().numAttributes() - 1; i++) {
			atts[i] = arff.getData().attribute(i).name();
			// System.out.println(arff.getData().attribute(i).name());
		}

		// local do modelo de classificacao criado
		String localModelo = "..//Projeto_RI//src//classify//Models//SVM_att.model";
		// features do classificador
		String[] attributes = atts;
		InputStream is = new FileInputStream(localModelo);
		objectInputStream = new ObjectInputStream(is);
		Classifier classifier = (Classifier) objectInputStream.readObject();
		FastVector vectorAtt = new FastVector();
		for (int i = 0; i < attributes.length; i++) {
			vectorAtt.addElement(new weka.core.Attribute(attributes[i]));
		}
		String[] classValues = { "Positive", "Negative" };
		FastVector classAtt = new FastVector();
		for (int i = 0; i < classValues.length; i++) {
			classAtt.addElement(classValues[i]);
		}
		vectorAtt.addElement(new Attribute("class", classAtt));
		Instances insts = new Instances("classification", vectorAtt, 1);
		insts.setClassIndex(attributes.length);
		classificador = new Classificador(classifier, insts, attributes);

		return classificador;
	}
//	public static void main(String[] args) throws Exception {
//		//test
//		Classificador classificador = getClassify();
//		String content = new Scanner(new File("Examples/negDoc0")).useDelimiter("\\Z").next();
//		boolean reposta = classificador.classify(preProcessFile("Examples/negDoc1"));
//		System.out.println(reposta);
//		
//	}

}
