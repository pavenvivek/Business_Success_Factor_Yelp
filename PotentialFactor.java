/************************************************************************************
* ClassName : PotentialFactors                                                      *
* Fields    : None                                                                  *
* Methods   : potentialFactors                                                      *
*************************************************************************************/
package ReviewAnalyzer;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;

class PotentialFactors
{
  void potentialFactors(BusinessDataSet []BSDataset, int noOfClusters, int noOfDataset, String gncity, String gnbusiness)
  {
    String filePath2 = "./Dataset/yelp_dataset_challenge/yelp_academic_dataset_review.json";
    Clusters []clu = new Clusters[noOfClusters];
    Clusters []top_five = new Clusters[noOfClusters];
    Calculate_Score cscore = new Calculate_Score();
    SentimentAnalysis snt = new SentimentAnalysis();
    JsonParser parser = new JsonParser();
    Pattern pattern1 = Pattern.compile ("true", Pattern.CASE_INSENSITIVE);
    //Pattern pattern2 = Pattern.compile ("false", Pattern.CASE_INSENSITIVE);
    Matcher matcher;
    String []attr = new String[2];
    String []attr2 = new String[2];
    String temp_Bid;
    int temp_bestScr;
    double review_score; 
    int matched = 0;

    for (int i = 0; i < noOfClusters; i++)
    {
      clu[i] = new Clusters();
      clu[i].datacount = 0;
      clu[i].size = 500;
      top_five[i] = new Clusters();
      top_five[i].datacount = 5;
    }

    for (int i = 0; i < noOfDataset; i++)
    {
      clu[BSDataset[i].cluster].BSDataset[clu[BSDataset[i].cluster].datacount] = BSDataset[i];
      clu[BSDataset[i].cluster].datacount++;

      if (clu[BSDataset[i].cluster].datacount == clu[BSDataset[i].cluster].size)
      {
        clu[BSDataset[i].cluster].BSDataset = Arrays.copyOf(clu[BSDataset[i].cluster].BSDataset, clu[BSDataset[i].cluster].datacount + 500);
        clu[BSDataset[i].cluster].size += 500;
      }
    }

    for (int i = 0; i < noOfClusters; i++)
    {
      for (int j = 0; j < top_five[i].datacount; j++)
      {
        if (null != clu[i].BSDataset[j])
        {
          top_five[i].BSDataset[j] = clu[i].BSDataset[j];
        }
      }
    }
    parser.parseJsonFile_review(filePath2, top_five, noOfClusters);

    for (int i = 0; i < noOfClusters; i++)
    {
      for (int j = 0; j < top_five[i].datacount; j++)
      {
        if (null != top_five[i].BSDataset[j])
        {
            review_score = cscore.calcReviewScore(top_five[i].BSDataset[j], snt);
            top_five[i].BSDataset[j].review_score = review_score;
        }
      }
    }

    for (int i = 0; i < noOfClusters; i++)
    {
      System.out.println("\n\n<========== Cluster "+ i + " ==========>");
      for (int j = 0; j < top_five[i].datacount; j++)
      {
        if (null != top_five[i].BSDataset[j])
        {
          System.out.println("Category: " + top_five[i].BSDataset[j].categories[0] + "\tStars: " + 
                           top_five[i].BSDataset[j].stars+"\tCluster: " + top_five[i].BSDataset[j].cluster + 
                           "\tReview Count: " + top_five[i].BSDataset[j].review_count+"\tSuccess Score: "+
                           top_five[i].BSDataset[j].success_score);
        }
      }
    }

    for (int i = 0; i < noOfClusters; i++)
    {      
      for (int j = 0; j < 3; j++)
      {
        for (int k = 0; k < 60; k++)
        {
          if (null == top_five[i].BSDataset[j])
          {
            break;
          }

          if (null != top_five[i].BSDataset[j].attributes[k])
          {
            attr = (top_five[i].BSDataset[j].attributes[k]).split(":");

            if (attr[1].equals("true") && !ArrayUtils.contains(top_five[i].success_categories, attr[0]))
            {
              if (!(attr[0].equals("Attire")) && !(attr[0].equals("Alcohol")))
              {
                matched = 0;
                for (int j1 = j+1; j1 < top_five[i].datacount; j1++)
                {
                  if (null == top_five[i].BSDataset[j1])
                  {
                    break;
                  }
                  for (int k1 = 0; k1 < 60; k1++)
                  {
                    if (null != top_five[i].BSDataset[j1].attributes[k1])
                    {
                      Pattern pattern2 = Pattern.compile (attr[0], Pattern.CASE_INSENSITIVE);
                      matcher = pattern2.matcher (top_five[i].BSDataset[j1].attributes[k1]);

                      if (matcher.find())
                      {
                        attr2 = (top_five[i].BSDataset[j1].attributes[k1]).split(":");
                    
                        if (attr2[1].equals("true"))
                        {
                          matched++;
                          break;
                        }
                      }
                    }
                    else
                      break;
                  }
                }
                if (3 <= matched)
                {
                  top_five[i].success_categories[top_five[i].sc_index] = attr[0];
                  top_five[i].sc_index++;
                  if (ArrayUtils.contains(top_five[i].fail_categories, attr[0]))
                  {
                    top_five[i].fail_categories = ArrayUtils.removeElement(top_five[i].fail_categories, attr[0]);
                    top_five[i].fc_index--;
                  }
                }
                else
                {
                  if (!ArrayUtils.contains(top_five[i].fail_categories, attr[0]))
                  {
                    top_five[i].fail_categories[top_five[i].fc_index] = attr[0];
                    top_five[i].fc_index++;
                  }
                }
              }
            }
          }
          else
            break;
        }
      }
    }

    
    System.out.println("\n\n<========== Successfull Factors for Starting new "+ gnbusiness + " Business in " + gncity +" ==========>\n");
    for (int i = 0; i < noOfClusters; i++)
    {      
      if (null == top_five[i].BSDataset[0])
      {
        break;
      }

      System.out.println("\n\n<~~~~~~~~~~~~~~~ For Category: " + top_five[i].BSDataset[0].categories[0] +"/" + top_five[i].BSDataset[0].categories[1] + " ~~~~~~~~~~~~~~~>");
      
      System.out.println("\nStandards to be maintained: \n");
      System.out.print("[#] ");

      for (int j = 0; j < top_five[i].sc_index; j++)
      {
        if (null != top_five[i].success_categories[j])
        {
          System.out.print(top_five[i].success_categories[j] + ", ");
        }
      }

      System.out.println("\n\nAdditional Features to be added: \n");
      System.out.print("[#] ");

      for (int j = 0; j < top_five[i].fc_index; j++)
      {
        if (null != top_five[i].fail_categories[j])
        {
          System.out.print(top_five[i].fail_categories[j] + ", ");
        }
      }

      System.out.println("\n\nFew Compliments about competitor: [Maintain the same standards]\n");
      System.out.println(top_five[i].BSDataset[0].text_pos);

      System.out.println("\n\nFew Concerns about competitor: [Address the concerns in your business]\n");
      System.out.println(top_five[i].BSDataset[0].text_neg);
    }
  }
}

