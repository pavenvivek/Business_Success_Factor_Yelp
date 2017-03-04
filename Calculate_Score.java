/************************************************************************************
* ClassName : Calculate_Score                                                       *
* Fields    : weight_3_5, weight_4_0, weight_4_5, weight_5_0                        *
* Methods   : calcRatingScore, calcReviewScore, calcAttrScore, calcSuccessScore     *
*************************************************************************************/
package ReviewAnalyzer;

class Calculate_Score
{
  double []weight_3_5 = new double[4];
  double []weight_4_0 = new double[4];
  double []weight_4_5 = new double[4];
  double []weight_5_0 = new double[4];

  Calculate_Score()
  {
    weight_3_5[0] = 0.10;
    weight_3_5[1] = 0.15;
    weight_3_5[2] = 0.20;
    weight_3_5[3] = 0.25;

    weight_4_0[0] = 0.20;
    weight_4_0[1] = 0.30;
    weight_4_0[2] = 0.45;
    weight_4_0[3] = 0.60;

    weight_4_5[0] = 0.45;
    weight_4_5[1] = 0.60;
    weight_4_5[2] = 0.75;
    weight_4_5[3] = 0.90;

    weight_5_0[0] = 0.70;
    weight_5_0[1] = 0.95;
    weight_5_0[2] = 1.15;
    weight_5_0[3] = 1.40;
  }

  double calcRatingScore(double ratings, double review_count)
  {
    double ratingScore = 0;

    if (3.5 == ratings)
    {
      if ((0 <= review_count) && (5 >= review_count))
      {
        ratingScore += (weight_3_5[0] * review_count);
      }
      if ((5 <= review_count) && (10 >= review_count))
      {
        ratingScore += (weight_3_5[1] * review_count);
      }
      if ((10 <= review_count) && (15 >= review_count))
      {
        ratingScore += (weight_3_5[2] * review_count);
      }
      else
      {
        ratingScore += (weight_3_5[3] * review_count);
      }
    }

    if (4.0 == ratings)
    {
      if ((0 <= review_count) && (5 >= review_count))
      {
        ratingScore += (weight_4_0[0] * review_count);
      }
      if ((5 <= review_count) && (10 >= review_count))
      {
        ratingScore += (weight_4_0[1] * review_count);
      }
      if ((10 <= review_count) && (15 >= review_count))
      {
        ratingScore += (weight_4_0[2] * review_count);
      }
      else
      {
        ratingScore += (weight_4_0[3] * review_count);
      }
    }

    if (4.5 == ratings)
    {
      if ((0 <= review_count) && (5 >= review_count))
      {
        ratingScore += (weight_4_5[0] * review_count);
      }
      if ((5 <= review_count) && (10 >= review_count))
      {
        ratingScore += (weight_4_5[1] * review_count);
      }
      if ((10 <= review_count) && (15 >= review_count))
      {
        ratingScore += (weight_4_5[2] * review_count);
      }
      else
      {
        ratingScore += (weight_4_5[3] * review_count);
      }
    }

    if (5.0 == ratings)
    {
      if ((0 <= review_count) && (5 >= review_count))
      {
        ratingScore += (weight_5_0[0] * review_count);
      }
      if ((5 <= review_count) && (10 >= review_count))
      {
        ratingScore += (weight_5_0[1] * review_count);
      }
      if ((10 <= review_count) && (15 >= review_count))
      {
        ratingScore += (weight_5_0[2] * review_count);
      }
      else
      {
        ratingScore += (weight_5_0[3] * review_count);
      }
    }

    return ratingScore;
  }
  
  double calcReviewScore(BusinessDataSet BSDataset, SentimentAnalysis snt)
  {
    double reviewScore = snt.AnalyzeSentiment(BSDataset);

    return reviewScore;
  }

  int calcAttrScore(String []Attributes)
  {
    int attrScore = 0;
    int n_true = 0;
    int n_false = 0;

    for (int i = 0; i < Attributes.length; i++)
    {
      if (Attributes[i] != null)
      {
        String val[] = Attributes[i].split(":");
      
        if ((val[1] == "false") || (val[1] == "loud") || (val[1] == "none") || (val[1] == "4"))
        {
          n_false += 1;
        }
        else if (val[0] != "Attire")
        {
          n_true += 1;
        }
      }
      else if (Attributes[i] == null)
      {
        break;
      }
    }
    attrScore = n_true; //- n_false;

    return attrScore;
  }

  void calcSuccessScore(BusinessDataSet []BSDataset)
  {
    JsonParser JParser = new JsonParser();

    for (int i = 0; i < JParser.gcount; i++)
    {
      BSDataset[i].rating_score = calcRatingScore(BSDataset[i].stars, BSDataset[i].review_count);
      //BSDataset[i].review_score = calcReviewScore(BSDataset[i], snt);
      BSDataset[i].attr_score = calcAttrScore(BSDataset[i].attributes);
      BSDataset[i].success_score = (0.70 * BSDataset[i].rating_score) + (0.30 * BSDataset[i].attr_score); //(0.35 * BSDataset[i].review_score)
    }
  }
}

