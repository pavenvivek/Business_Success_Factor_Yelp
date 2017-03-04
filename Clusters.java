/************************************************************************************
* ClassName : Clusters                                                              *
* Fields    : BSDataset, success_categories, fail_categories, sc_index, fc_index,   *
*             clusterNumber, datacount, size                                        *
* Methods   : None                                                                  *
*************************************************************************************/
package ReviewAnalyzer;

class Clusters
{
  BusinessDataSet []BSDataset = new BusinessDataSet[500];
  String []success_categories = new String[60];
  String []fail_categories    = new String[60];
  int sc_index;
  int fc_index;
  int clusterNumber;
  int datacount;
  int size;
}

