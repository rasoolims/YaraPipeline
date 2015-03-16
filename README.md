# Yara Pipeline
A full pipleline for [Yara Parser](https://github.com/yahoo/YaraParser)

# Command line usage
    java -jar jar/YaraPipeline.jar -input [input file] -output [output file] -parse_model [parse model file] -pos_model [pos model] -tokenizer_model [tokenizer model] -sentence_model [sentence detector model]

    Optional: -nt [number of threads]

    Argument order is not important!

# API USAGE
Look at the class ``ApiExample.java``

```java       
// this function just includes examples
// you can cut and paste examples into you code (with importing packages)
// should put real file path in the brackets (e.g. [parse_model_path])
           
// you can change it depending on your model
int numberOfThreads=8;
           
// case #1 parsing a file
Info info1=new Info("[parse_model_path]","[pos_model_path]","[open_nlp_tokenizer_model_path]","[open_nlp_sentence_detector_model_path]",numberOfThreads);
YaraPipeline.parseFile("[input_file]","[output_file]",info1);
           
// case #2 parsing text
Info info2=new Info("[parse_model_path]","[pos_model_path]","[open_nlp_tokenizer_model_path]","[open_nlp_sentence_detector_model_path]",numberOfThreads);
String someText="some text....";
String conllOutputText2= YaraPipeline.parseText(someText,info1);
           
// case #3 parsing a sentence (do not need sentence tokenizer)
Info info3=new Info("[parse_model_path]","[pos_model_path]","[open_nlp_tokenizer_model_path]",numberOfThreads);
String someSentence="some sentence.";
ParseResult parseResult3= YaraPipeline.parseSentence(someSentence, info1);
String conllOutputText3=parseResult3.getConllOutput();
           
// case #4 parsing a tokenized sentence (do not need any openNLP model)
Info info4=new Info("[parse_model_path]","[pos_model_path]",numberOfThreads);
String[] someWords4={"some", "words","..."};
ParseResult parseResult4= YaraPipeline.parseTokenizedSentence(someWords4, info1);
String conllOutputText4=parseResult4.getConllOutput();
   
// case #5 parsing a tagged sentence (do not need any openNLP model and no pos model)
Info info5=new Info("[parse_model_path]",numberOfThreads);
String[] someWords5={"some", "words","..."};
String[] someTags5={"tag1", "tag2","tag3"};
ParseResult parseResult5= YaraPipeline.parseTaggedSentence(someWords5,someTags5, info1);
String conllOutputText5=parseResult5.getConllOutput();
```