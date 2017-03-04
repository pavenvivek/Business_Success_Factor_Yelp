/************************************************************************************
* ClassName : SentimentAnalysis                                                     *
* Fields    : props, pipeline                                                       *
* Methods   : AnalyzeSentiment                                                      *
*************************************************************************************/
package ReviewAnalyzer;

import java.util.*;

import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;

class SentimentAnalysis
{
  Properties props;
  StanfordCoreNLP pipeline;

  SentimentAnalysis()
  {
    props = new Properties();
    props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
    pipeline = new StanfordCoreNLP(props);
  }

  int AnalyzeSentiment(BusinessDataSet BSDataset)
  {
    int n_pos = 0;
    int n_neg = 0;
    int n_nuetral = 0;
    int final_score = 0;

    try 
    {
      if (null != BSDataset.text)
      {
      Annotation annotation = pipeline.process(BSDataset.text);
      
      List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

      for (CoreMap sentence : sentences) 
      {
  	String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);

        if (sentiment.equals("Positive") || sentiment.equals("Very positive"))
        {
          n_pos += 1;
          String pos = sentence.toString();
          BSDataset.text_pos = BSDataset.text_pos + " " + pos;
          BSDataset.text_pos_cnt++;
        }
        else if (sentiment.equals("Negative") || sentiment.equals("Very negative"))
        {
          n_neg += 1;
          String neg = sentence.toString();
          BSDataset.text_neg = BSDataset.text_neg + " " + neg;
          BSDataset.text_neg_cnt++;
        }
        else
        {
          n_nuetral += 1;
        }
      }
      n_pos = n_pos * 10;
      n_neg = n_neg * 5;
      final_score  += (n_pos - n_neg);
      }
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }

    return final_score;
  }
}

