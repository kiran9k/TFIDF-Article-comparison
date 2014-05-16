package my_tfidf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import my_tfidf.main_tfidf.Global;

public class tfidf_2_documents {

	public static void tfidf_start(Logger L)
	{
		ArrayList<String> file1=file_lister.list_files(Global.prop.get(0),L);
		ArrayList<String> file2=file_lister.list_files(Global.prop.get(1),L);
		System.out.println(file1.size()+"\t"+file2.size());
		System.out.println(Global.prop.get(0)+Global.prop.get(1));
		String content1 = "",content2 = "";
		String[] split;
		ArrayList<String> temp=new ArrayList<String>();
		String temp_string;
		List<String[]> all_docs=new ArrayList<String[]>();
		List<String[]> temp_docs=new ArrayList<String[]>();
		Set<String> temp_unique=new LinkedHashSet<String>();
		Set<String> all_unique=new LinkedHashSet<String>();
		List<String> unique_words=new ArrayList<String>();
		List<double[]> tfidf_vector=new ArrayList<double[]>();
		for(int i=0;i<file1.size();i++)
		{
			if(Global.prop.get(2).contains("xml"))
					content1=read_contents.read_from_xml(file1.get(i));
			if(content1=="")
			{
				System.out.println("content empty");
				continue;
			}
			split=content1.replaceAll("[\\W&&[^\\s]]", "").split("\\W+");
			temp.addAll(Arrays.asList(split));
			temp.removeAll(Global.stop_words);
			temp_string = temp.toString().replace("[","").replace("]","");
			temp_docs.add(temp_string.split("\\W+"));
			temp_unique.addAll(Arrays.asList(temp_string.split("\\W+")));
			temp.clear();
			for(int j=0;j<file2.size();j++)
			{
				if(Global.prop.get(2).contains("xml"))
					content2=read_contents.read_from_xml(file2.get(j));
				if(content2=="")
				{
					System.out.println("Contnet empty");
					continue;					
				}
				
				split=content2.replaceAll("[\\W&&[^\\s]]", "").split("\\W+");
				temp.addAll(Arrays.asList(split));
				temp.removeAll(Global.stop_words);
				temp_string = temp.toString().replace("[","").replace("]","");
				temp.clear();
				all_docs.addAll(temp_docs);
				all_docs.add(temp_string.split("\\W+"));
				all_unique.addAll(temp_unique);
				all_unique.addAll(Arrays.asList(temp_string.split("\\W+")));
				unique_words.addAll(all_unique);
				all_unique.clear();
				
				System.out.println(all_docs.size()+"  "+unique_words.size());
				// call tf-idf
				tfidf_vector.addAll(tfidf_computations.tfIdfCalculator(all_docs, unique_words));				
				//compute %
				//System.out.println(tfidf_vector.get(0));
				System.out.println(/*file1.get(i)+" " +file2.get(j)+" "+*/tfidf_computations.getCosineSimilarity(tfidf_vector));
				
				tfidf_vector.clear();
				all_unique.clear();
				unique_words.clear();
				all_docs.clear();
			}
			temp_docs.clear();
			temp_unique.clear();
		}
			
	}

}
