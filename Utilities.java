/************************************************************************************
* ClassName : Utilities                                                             *
* Fields    : none                                                                  *
* Methods   : writeCSVFile                                                          *
*************************************************************************************/
package ReviewAnalyzer;

import java.io.*;
import com.csvreader.CsvWriter;

class Utilities
{
  void writeCSVFile(String fileName, BusinessDataSet []BSDataset)
  {
    boolean alreadyExists = new File(fileName).exists();
    JsonParser JParser = new JsonParser();
			
    try 
    {
      CsvWriter csvOutput = new CsvWriter(new FileWriter(fileName, false), ',');
			
      csvOutput.write("feature1");
      csvOutput.write("feature2");
      csvOutput.write("feature3");
      csvOutput.write("feature4");
      csvOutput.write("feature5");
      csvOutput.write("feature6");
      csvOutput.write("feature7");
      csvOutput.write("feature8");
      csvOutput.write("business_id");
      csvOutput.endRecord();
			
      for (int i = 0; i < JParser.gcount; i++)
      {
        csvOutput.write(BSDataset[i].categories[0]);
        csvOutput.write(BSDataset[i].categories[1]);
        csvOutput.write(BSDataset[i].categories[2]);
        csvOutput.write(BSDataset[i].categories[3]);
        csvOutput.write(BSDataset[i].categories[4]);
        csvOutput.write(BSDataset[i].categories[5]);
        csvOutput.write(BSDataset[i].categories[6]);
        csvOutput.write(BSDataset[i].categories[7]);
        csvOutput.write(BSDataset[i].business_id);
        csvOutput.endRecord();
      }
			
      csvOutput.close();
    } 
    catch (IOException e) 
    {
      e.printStackTrace();
    }
  }
}

