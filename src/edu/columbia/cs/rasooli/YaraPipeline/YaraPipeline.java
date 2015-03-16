package edu.columbia.cs.rasooli.YaraPipeline;

import edu.columbia.cs.rasooli.YaraPipeline.Structs.Info;
import edu.columbia.cs.rasooli.YaraPipeline.Structs.ParseResult;

import java.io.*;

public class YaraPipeline {

    public static void main(String[] args) throws Exception {
	    Info info= loadInfo(args);
        String inputPath=null;
        String outputPath=null;
        for(int i=0;i<args.length;i++) {
            if (args[i].equals("-input")) {
                File f=new File(args[i+1]);
                inputPath = f.getAbsolutePath();
            }
            else if (args[i].equals("-output")) {
                File f=new File(args[i+1]);
                outputPath = f.getAbsolutePath();
            }
        }
        if(outputPath!=null && inputPath!=null && info!=null){
            parseFile(inputPath,outputPath,info);
        }  else{
            showHelp();
        }
        System.exit(0);
    }
    
    public static void parseFile(String inputPath, String outputPath,Info info) throws  Exception{
        BufferedReader reader=new BufferedReader(new FileReader(inputPath));
        BufferedWriter writer=new BufferedWriter(new FileWriter(outputPath));
        String line;
        int count=0;
        while((line=reader.readLine())!=null){
            writer.write(parseText(line.trim(),info));
            count++;
            if(count%1000==0)
                System.err.print(count+"...");
        }
        System.err.print("done!\n");
        writer.flush();
        writer.close();
    }
    
    public static String parseText(String text,Info info) throws  Exception{
        String sentences[] =info.sentenceDetector.sentDetect(text);
        StringBuilder output=new StringBuilder();
        
        for(String sentence: sentences){
            output.append ((new ParseResult(sentence,info)).getConllOutput());          
        }
        return output.toString();
    }

    public static ParseResult parseSentence(String sentence, Info info) throws  Exception{
        return  new ParseResult(sentence,info);
    }
    
    public static ParseResult parseTaggedSentence(String[] words, String[] tags, Info info) throws  Exception{
        return new ParseResult(words,tags,info);
    }

    public static ParseResult parseTokenizedSentence(String[] words, Info info) throws  Exception{
        return new ParseResult(words,info);
    }
    
    public static Info loadInfo(String[] args)throws  Exception{
        String parseModelPath=null;
        String posModelPath=null;
        String tokenizerModelPath=null;
        String sentenceDetectorModelPath=null;
        int numberOfThreads=8;
        
        for(int i=0;i<args.length;i++){
            if (args[i].equals("-parse_model"))
                parseModelPath=args[i+1];
            else if (args[i].equals("-pos_model"))
                posModelPath=args[i+1];
            else if (args[i].equals("-tokenizer_model"))
                tokenizerModelPath=args[i+1];
            else if (args[i].equals("-sentence_model"))
                sentenceDetectorModelPath=args[i+1];
            else if (args[i].equals("-nt"))
                numberOfThreads=Integer.parseInt(args[i+1]);
        }
        
        if(parseModelPath==null || posModelPath==null ||tokenizerModelPath==null || sentenceDetectorModelPath==null )
            return  null;
        return  new Info(parseModelPath,posModelPath,tokenizerModelPath,sentenceDetectorModelPath,numberOfThreads);
    }
    
    public static void showHelp(){
      StringBuilder output=new StringBuilder();
      output.append("Yara pipeline\n");
      output.append("java -jar jar/YaraPipeline.jar -input [input file] -output [output file] -parse_model [parse model file] -pos_model [pos model] -tokenizer_model [tokenizer model] -sentence_model [sentence detector model]\n");
     output.append("Optional: -nt [number of threads]\n");
        output.append("Argument order is not important!\n");
     System.out.print(output.toString());
        
    }
}
