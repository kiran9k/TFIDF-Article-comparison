package my_tfidf;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class main_tfidf {
	private static Logger L;
	public static void jaccard_comparison()
	{
		ArrayList<String> file1=file_lister.list_files(Global.prop.get(0),L);
		ArrayList<String> file2=file_lister.list_files(Global.prop.get(1),L);
		System.out.println(file1.size()+"\t"+file2.size());
		System.out.println(Global.prop.get(0)+Global.prop.get(1));
		if(Global.prop.get(2).matches("xml"))
		{
			
			String content1="";
			String content2="";
			float prev_result=0;
			float curr_result=0;
			float actual_result=0;
			String actual_file="";
			String output="";
			List<String[]> all_words2=new ArrayList<String[]>();
			Set<String> unique_words=new LinkedHashSet<String>();
			List<String> temp=new ArrayList<String>();
			List<String> tfid_unique=new ArrayList<String>();
			Set<String> temp_unique_words=new LinkedHashSet<String>();
			String temp_string;
			String[] split;
			for(int j=0;j<file2.size();j++)
			{
				content2=read_contents.read_from_xml(file2.get(j));
				split=content2.replaceAll("[\\W&&[^\\s]]", "").split("\\W+");
				temp.addAll(Arrays.asList(split));
				temp.removeAll(Global.stop_words);
				temp_string=temp.toString().replace("[","").replace("]","");
				all_words2.add(temp_string.split("\\W+"));
				unique_words.addAll(temp);
				temp.clear();
				temp_string="";								
			}
			System.out.println("folder 2 done");
			int size=all_words2.size();
			tfid_unique.addAll(unique_words);
			//List<double[]> tfidf_one_time_vector=tfidf_computations.one_time_tfidfcalculator(all_words2, tfid_unique);
			List<double[]> tfidf_vector=new ArrayList<double[]>();
			for(int  i=0;i<file1.size();i++)
			{
				
				//System.out.println(file1.get(i));
				content1=read_contents.read_from_xml(file1.get(i));
				split=content1.replaceAll("[\\W&&[^\\s]]", "").split("\\W+");
				temp.addAll(Arrays.asList(split));
				temp.removeAll(Global.stop_words);
				temp_string=temp.toString().replace("[","").replace("]","");
				all_words2.add(temp_string.split("\\W+"));
				temp_unique_words.addAll(unique_words);
				temp_unique_words.addAll(Arrays.asList(temp_string.split("\\W+")));
				tfid_unique.addAll(temp_unique_words);
				temp_unique_words.clear();
				//call the tfid computations
			//	tfidf_vector.addAll(tfidf_one_time_vector);
				tfidf_vector.addAll(tfidf_computations.tfIdfCalculator(all_words2, tfid_unique));
				
				
				int x=tfidf_computations.getCosineSimilarity(tfidf_vector);
				if(x!=-1)
				{
					System.out.println("match found");
					System.out.println(file1.get(i)+file2.get(x));
				}
				//compute the best match
				System.out.println(i+"th run");
				all_words2.remove(size);
				tfid_unique.clear();
				tfidf_vector.clear();
				
				
				
			}
			/*output_writer.txt_writer(output, Global.prop.get(5)+"/"+Global.prop.get(4));
			L.info("output written to "+Global.prop.get(5)+"/"+Global.prop.get(4));*/
		}
	}
	public static void main(String[] args) {
		L=Logger.getLogger("log_test");		
		FileHandler fh;		
		try
		{			
			fh = new FileHandler("MainLogFile.log",true);
			L.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
		    fh.setFormatter(formatter);  
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		System.out.println("Main program started");
		L.info("Main Program started");
		tfidf_2_documents.tfidf_start(L);
		//jaccard_comparison();
		L.info("Program completed ");
	}
	public static  class Global
	{
		public static ArrayList<String> prop=config_reader.get_prop();
		public static Set<String> stop_words=new HashSet<String>(Arrays.asList(read_contents.read_from_txt("stop_words.txt").split(",")));
	}
}
