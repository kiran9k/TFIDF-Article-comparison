package my_tfidf;

import java.util.ArrayList;
import java.util.List;

import tfidf.CosineSimilarity;
import tfidf.TfIdf;

public class tfidf_computations {
	public static List<double[]> one_time_tfidfcalculator(List<String[]>  termsDocsArray,List<String> allTerms)
	{
		System.out.println("one time calculations");
        double tf; //term frequency
        double idf; //inverse document frequency
        double tfidf; //term requency inverse document frequency
        List<double[]> tfidfDocsVector = new ArrayList<double[]>();
        for (String[] docTermsArray : termsDocsArray) {
            double[] tfidfvectors = new double[allTerms.size()];
            int count = 0;
            for (String terms : allTerms) {
                tf = new TfIdf().tfCalculator(docTermsArray, terms);
                idf = new TfIdf().idfCalculator(termsDocsArray, terms);
                tfidf = tf * idf;
                tfidfvectors[count] = tfidf;
                count++;
            }
            tfidfDocsVector.add(tfidfvectors);  //storing document vectors;            
        }
        System.out.println("one time calculation complete");
        return tfidfDocsVector; 
	}
	public static  List<double[]> tfIdfCalculator(List<String[]> termsDocsArray,List<String> allTerms) 
	{
        double tf; //term frequency
        double idf; //inverse document frequency
        double tfidf; //term requency inverse document frequency
        List<double[]> tfidfDocsVector = new ArrayList<double[]>();
        for (String[] docTermsArray : termsDocsArray) {
            double[] tfidfvectors = new double[allTerms.size()];
            int count = 0;
            for (String terms : allTerms) {
                tf = new TfIdf().tfCalculator(docTermsArray, terms);
                idf = new TfIdf().idfCalculator(termsDocsArray, terms);
                tfidf = tf * idf;
                tfidfvectors[count] = tfidf;
                count++;
            }
            tfidfDocsVector.add(tfidfvectors);  //storing document vectors;            
        }
        //System.out.println("calculation complete");
        return tfidfDocsVector;
		
    }
	 public static double getCosineSimilarity(List<double[]> tfidfDocsVector) {
	     //System.out.println(tfidfDocsVector.get(0)[1]+" " +tfidfDocsVector.get(1)[1]);   
	     double best_match=-1;
	     int found=0;
	     System.out.println( (new CosineSimilarity().cosineSimilarity(tfidfDocsVector.get(0),tfidfDocsVector.get(1))));
	     return (new CosineSimilarity().cosineSimilarity(tfidfDocsVector.get(0),tfidfDocsVector.get(1)));
	     /*for (int i = 0; i < tfidfDocsVector.size(); i++) 
	     {
	    	 for (int j = 1; j < tfidfDocsVector.size(); j++)
	    	 {
	    		 double temp=new CosineSimilarity().cosineSimilarity(tfidfDocsVector.get(i),tfidfDocsVector.get(j));
	    		 if(temp>best_match)
	    		 {
	    			 found=j;	    		 
	    			 best_match=temp;
	    		 }
	    	 
	    	 }
	     }
	     return best_match;*/
	 }
}
