/************************************************************************************
* ClassName : KMeansCluster                                                         *
* Fields    : numberOfClusters                                                      *
* Methods   : clusterData                                                           *
*************************************************************************************/
package ReviewAnalyzer;

import java.io.*;
import java.io.IOException;

import weka.core.Instances;
import weka.clusterers.SimpleKMeans;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;

class KMeansCluster
{
  int numberOfClusters;

  void clusterData(String FileName, BusinessDataSet []BSDataset) throws IOException
  {
    try
    {
      SimpleKMeans kmeans = new SimpleKMeans();
      CSVLoader loader = new CSVLoader(); 
      loader.setSource(new File(FileName)); 
      Instances structure = loader.getDataSet(); 

      kmeans.setSeed(10);
      kmeans.setPreserveInstancesOrder(true);
      kmeans.setNumClusters(numberOfClusters);
      kmeans.buildClusterer(structure);
      int[] assignments = kmeans.getAssignments();

      int i=0;
      for(int clusterNum : assignments) 
      {
        BSDataset[i].cluster = clusterNum;
        i++;
      }
    }
    catch (Exception e)
    {
      System.out.println("Exception :"+ e);
    }
  }
}

