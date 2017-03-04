/************************************************************************************
* ClassName : BusinessDataSet                                                       *
* Fields    : business_id, name, city, text, text_pos, text_neg, text_pos_ind,      *
*             text_pos_cnt, text_neg_ind, text_neg_cnt, attr_score, success_score,  *
*             stars, review_count, categories, attributes, cluster, rating_score,   *
*             review_score                                                          *
* Methods   : getScore, compareTo                                                   *
*************************************************************************************/
package ReviewAnalyzer;

class BusinessDataSet implements Comparable<BusinessDataSet>
{
  int text_ind = 0;
  int text_cnt = 0;
  int text_pos_ind = 0;
  int text_pos_cnt = 0;
  int text_neg_ind = 0;
  int text_neg_cnt = 0;
  int review_count;  
  int cluster;
  int attr_score;
  double stars;
  double rating_score;
  double review_score;
  double success_score;
  String business_id;
  String name;
  String city;
  String text = "";
  String text_pos = "";
  String text_neg = "";
  String []categories = new String[10];
  String []attributes = new String[60];

  public double getScore() 
  {
    return success_score;
  }

  public int compareTo(BusinessDataSet compare) 
  {
    double compareScore = ((BusinessDataSet) compare).getScore(); 
 
    return (int)(compareScore - this.success_score);
  }	
}

