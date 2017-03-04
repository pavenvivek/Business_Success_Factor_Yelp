/*##########################################################################################
# FILE NAME        : SuccessFactor.java                                                    #
# FILE DESCRIPTION : This code retrieves the potential factors for success of a given      #
#                    business in the given city using yelp dataset                         #
# CREATED BY       : Paventhan Vivekanandan, Rashmi Rayala                                 #
# UMAIL ID         : pvivekan@indiana.edu, rashraya@indiana.edu                            #
# DATE OF CREATION : November 25, 2014                                                     #
# COURSE NAME      : Z534 Information Retrieval                                            #
# ORGANIZATION     : Indiana University Bloomington                                        #
############################################################################################*/
package ReviewAnalyzer;

import java.util.*;

/************************************************************************************
* ClassName : SuccessFactor                                                         *
* Fields    : main                                                                  *
* Methods   : None                                                                  *
*************************************************************************************/

public class SuccessFactor 
{
  public static void main(String[] args) throws Exception
  {
    int rd_size = 5000;
    String filePath1 = "./Dataset/yelp_dataset_challenge/yelp_academic_dataset_business.json"; 
    String filePath2 = "./Dataset/yelp_dataset_challenge/yelp_academic_dataset_review.json";
    String gncity = "Las Vegas";
    String gnbusiness = "Restaurants";
    JsonParser parser = new JsonParser();
    Utilities utility = new Utilities();
    BusinessDataSet []BSDataset = new BusinessDataSet[rd_size];
    KMeansCluster kmeans = new KMeansCluster();
    Calculate_Score csore = new Calculate_Score();
    PotentialFactors factors = new PotentialFactors();

    parser.parseJsonFile_business(gncity, gnbusiness, filePath1, BSDataset);

    if (1000 < parser.gcount)
    {
      kmeans.numberOfClusters = (int) Math.floor(parser.gcount/100);
    }
    else if (500 < parser.gcount)
    {
      kmeans.numberOfClusters = (int) Math.floor(parser.gcount/50);
    }
    else if (100 < parser.gcount)
    {
      kmeans.numberOfClusters = (int) Math.floor(parser.gcount/25);
    }
    else
    {
      kmeans.numberOfClusters = 5;
    }

    utility.writeCSVFile("yelp_categories.csv", BSDataset);
    kmeans.clusterData("yelp_categories.csv", BSDataset);

    csore.calcSuccessScore(BSDataset);

    BusinessDataSet []BSDataset1 = Arrays.copyOf(BSDataset, parser.gcount);
    Arrays.sort(BSDataset1);

    factors.potentialFactors(BSDataset1,kmeans.numberOfClusters, parser.gcount, gncity, gnbusiness);
  }
}
