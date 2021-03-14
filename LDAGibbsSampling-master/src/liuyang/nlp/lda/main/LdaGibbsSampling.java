package liuyang.nlp.lda.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import liuyang.nlp.lda.com.FileUtil;
import liuyang.nlp.lda.conf.ConstantConfig;
import liuyang.nlp.lda.conf.PathConfig;

/**Liu Yang's implementation of Gibbs Sampling of LDA
 * @author yangliu
 * @blog http://blog.csdn.net/yangliuy
 * @mail yangliuyx@gmail.com
 */

public class LdaGibbsSampling {
	
	public static class modelparameters {
		float alpha = 0.5f; //usual value is 50 / K
		float beta = 0.1f;//usual value is 0.1
		int topicNum = 100;
		int iteration = 100;
		int saveStep = 10;
		int beginSaveIters = 50;
	}
	
	/**Get parameters from configuring file. If the 
	 * configuring file has value in it, use the value.
	 * Else the default value in program will be used
	 * @param ldaparameters
	 * @param parameterFile
	 * @return void
	 */
	private static void getParametersFromFile(modelparameters ldaparameters,
			String parameterFile) {
		// TODO Auto-generated method stub
		ArrayList<String> paramLines = new ArrayList<String>();
		FileUtil.readLines(parameterFile, paramLines);
		for(String line : paramLines){
			String[] lineParts = line.split("\t");
			switch(parameters.valueOf(lineParts[0])){
			case alpha:
				ldaparameters.alpha = Float.valueOf(lineParts[1]);
				break;
			case beta:
				ldaparameters.beta = Float.valueOf(lineParts[1]);
				break;
			case topicNum:
				ldaparameters.topicNum = Integer.valueOf(lineParts[1]);
				break;
			case iteration:
				ldaparameters.iteration = Integer.valueOf(lineParts[1]);
				break;
			case saveStep:
				ldaparameters.saveStep = Integer.valueOf(lineParts[1]);
				break;
			case beginSaveIters:
				ldaparameters.beginSaveIters = Integer.valueOf(lineParts[1]);
				break;
			}
		}
	}
	
	public enum parameters{
		alpha, beta, topicNum, iteration, saveStep, beginSaveIters;
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String originalDocsPath = PathConfig.ldaDocsPath;
		String resultPath = PathConfig.LdaResultsPath;
		String parameterFile= ConstantConfig.LDAPARAMETERFILE;
		
		modelparameters ldaparameters = new modelparameters();
		getParametersFromFile(ldaparameters, parameterFile);
		Documents docSet = new Documents();
		docSet.readDocs(originalDocsPath);
		new 相关输出(docSet,true);
		System.out.println("wordMap size " + docSet.termToIndexMap.size());
		FileUtil.mkdir(new File(resultPath));
		LdaModel model = new LdaModel(ldaparameters);
		System.out.println("1 Initialize the model ...");
		model.initializeModel(docSet);
		System.out.println("2 Learning and Saving the model ...");
		model.inferenceModel(docSet);
		System.out.println("3 Output the final model ...");
		model.saveIteratedModel(ldaparameters.iteration, docSet);
		System.out.println("Done!");
	}
	
	public static class 相关输出{
		public 相关输出(Documents docSet,Boolean 未训练) throws IOException{
			输出文档_词号变矩阵(docSet,PathConfig.LdaResultsPath+"文档_词号变矩阵.txt");
			输出词号_词次数_词表(docSet,PathConfig.LdaResultsPath+"词号_词次数_词表.txt");
			输出文档编号_文档名表(docSet,PathConfig.LdaResultsPath+"文档编号_文档名表.txt");
		}
		
		public void 输出文档_词号变矩阵(Documents docSet,String 输出地址) throws IOException{
			BufferedWriter writer = new BufferedWriter(new FileWriter(输出地址));
			for(int i=0;i<docSet.docs.size();i++){
				for(int j=0;j<docSet.docs.get(i).docWords.length;j++){
					writer.write(docSet.docs.get(i).docWords[j]+"");
					if(j<docSet.docs.get(i).docWords.length-1)
						writer.write("\t");
				}
				writer.write("\r\n");
			}
			writer.close();
		}
		
		public void 输出词号_词次数_词表(Documents docSet,String 输出地址) throws IOException{
			BufferedWriter writer = new BufferedWriter(new FileWriter(输出地址));
			for(String 词:docSet.indexToTermMap){
				String 词次数=docSet.termCountMap.get(词)+"";
				String 词号=docSet.termToIndexMap.get(词)+"";
				writer.write(词号+"\t"+词次数+"\t"+词+"\r\n");
			}
			writer.close();
		}
		
		public void 输出文档编号_文档名表(Documents docSet,String 输出地址) throws IOException{
			BufferedWriter writer = new BufferedWriter(new FileWriter(输出地址));
			for(int i=0;i<docSet.docs.size();i++){
				String 文档编号=i+"";
				String 文档名=new File(docSet.docs.get(i).docName).getName();
				文档名=文档名.substring(0, 文档名.length()-4);
				writer.write(文档编号+"\t"+文档名+"\r\n");
			}
			writer.close();
		}
	}
}

/* model.saveIteratedModel() 中增加了输出 主题_词次数矩阵 和 文档_主题次数矩阵
 * 
 */