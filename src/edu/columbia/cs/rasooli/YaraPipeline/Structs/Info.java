package edu.columbia.cs.rasooli.YaraPipeline.Structs;

import SemiSupervisedPOSTagger.Tagging.Tagger;
import YaraParser.Learning.AveragedPerceptron;
import YaraParser.Structures.IndexMaps;
import YaraParser.Structures.InfStruct;
import YaraParser.TransitionBasedSystem.Parser.KBeamArcEagerParser;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by Mohammad Sadegh Rasooli.
 * ML-NLP Lab, Department of Computer Science, Columbia University
 * Date Created: 3/2/15
 * Time: 4:07 PM
 * To report any bugs or problems contact rasooli@cs.columbia.edu
 */

public class Info {
   public final KBeamArcEagerParser parser;
    public   final Tagger tagger;
    public  final SemiSupervisedPOSTagger.Structures.IndexMaps posMaps;
    public  final InfStruct infStruct;
    public final IndexMaps parserMaps;
    private  final TokenizerModel tokenizerModel;
    public  final TokenizerME tokenizer;
    private final SentenceModel sentenceModel;
    public  final SentenceDetectorME sentenceDetector;

    public Info(String parseModelPath, int numberOfThreads) throws Exception {
        tagger = null;
        posMaps=null;
        infStruct = new InfStruct(parseModelPath);
        tokenizerModel=null;
        tokenizer=null;
        sentenceModel=null;
        sentenceDetector=null;

        ArrayList<Integer> dependencyLabels = infStruct.dependencyLabels;
        parserMaps = infStruct.maps;
        AveragedPerceptron averagedPerceptron = new AveragedPerceptron(infStruct);

        int featureSize = averagedPerceptron.featureSize();
        parser = new KBeamArcEagerParser(averagedPerceptron, dependencyLabels, featureSize, parserMaps, numberOfThreads);
    }
    
    public Info(String parseModelPath, String tagModelPath, int numberOfThreads) throws Exception {
        tagger = new Tagger(tagModelPath);
        posMaps=tagger.getMaps();
        infStruct = new InfStruct(parseModelPath);
        tokenizerModel=null;
        tokenizer=null;
        sentenceModel=null;
        sentenceDetector=null;
        
        ArrayList<Integer> dependencyLabels = infStruct.dependencyLabels;
        parserMaps = infStruct.maps;
        AveragedPerceptron averagedPerceptron = new AveragedPerceptron(infStruct);

        int featureSize = averagedPerceptron.featureSize();
        parser = new KBeamArcEagerParser(averagedPerceptron, dependencyLabels, featureSize, parserMaps, numberOfThreads);
    }

    public Info(String parseModelPath, String tagModelPath, String tokenizerModelPath, int numberOfThreads) throws Exception {
        tagger = new Tagger(tagModelPath);
        posMaps=tagger.getMaps();
        infStruct = new InfStruct(parseModelPath);
        tokenizerModel=new TokenizerModel(new FileInputStream(tokenizerModelPath));
        tokenizer=new TokenizerME(tokenizerModel);
        sentenceModel=null;
        sentenceDetector=null;

        ArrayList<Integer> dependencyLabels = infStruct.dependencyLabels;
        parserMaps = infStruct.maps;
        AveragedPerceptron averagedPerceptron = new AveragedPerceptron(infStruct);

        int featureSize = averagedPerceptron.featureSize();
        parser = new KBeamArcEagerParser(averagedPerceptron, dependencyLabels, featureSize, parserMaps, numberOfThreads);
    }

    public Info(String parseModelPath, String tagModelPath, String tokenizerModelPath, String sentenceModelPath, int numberOfThreads) throws Exception {
        tagger = new Tagger(tagModelPath);
        posMaps=tagger.getMaps();
        infStruct = new InfStruct(parseModelPath);
        tokenizerModel=new TokenizerModel(new FileInputStream(tokenizerModelPath));
        tokenizer=new TokenizerME(tokenizerModel);
        sentenceModel=new SentenceModel(new FileInputStream(sentenceModelPath));
        sentenceDetector=new SentenceDetectorME(sentenceModel);

        ArrayList<Integer> dependencyLabels = infStruct.dependencyLabels;
        parserMaps = infStruct.maps;
        AveragedPerceptron averagedPerceptron = new AveragedPerceptron(infStruct);

        int featureSize = averagedPerceptron.featureSize();
        parser = new KBeamArcEagerParser(averagedPerceptron, dependencyLabels, featureSize, parserMaps, numberOfThreads);
    }
    
    
    
    public void turnOff(){
        parser.shutDownLiveThreads();
    }
}
