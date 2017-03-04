/************************************************************************************
* ClassName : JsonParser                                                            *
* Fields    : gcount                                                                *
* Methods   : parseJsonFile_business, parseJsonFile_review                          *
*************************************************************************************/
package ReviewAnalyzer;

import java.io.*;
import java.util.*;

import org.json.JSONObject;
import org.json.JSONArray;

class JsonParser
{
  public static int gcount;

  void parseJsonFile_business(String usrcity, String usrBusiness, String filePath, BusinessDataSet []BSDataset) throws Exception
  {
    try
    {
      String line;
      FileReader reader = new FileReader(filePath);
      BufferedReader br  = new BufferedReader (new FileReader(filePath));
      int set, index = 0, k = 0, i1 = 0, i2 = 0, i3 = 0;

      while (null != (line = br.readLine()))
      {
        JSONObject jsonObject = new JSONObject(line);
	set = 0;
        i2 = 0;
        i3 = 0;

	String city = (String) jsonObject.get("city");
	double stars = (double) jsonObject.get("stars");

	if (city.equalsIgnoreCase(usrcity))
	{
	  JSONArray categories= (JSONArray) jsonObject.get("categories");
	  i1 = 0;
			
	  while (!categories.isNull(i1)) 
	  {
	    if((categories.getString(i1)).equals(usrBusiness))
	    {
	      set = 1;
	    }
            i1++;
	  }

	  if ((set == 1) && (stars >= 3.5))
	  {
	    BSDataset[index] = new BusinessDataSet();
	    BSDataset[index].city = city;
	    BSDataset[index].stars = stars;
            BSDataset[index].business_id = (String) jsonObject.get("business_id");
	    BSDataset[index].name = (String) jsonObject.get("name");
	    BSDataset[index].review_count = (int) jsonObject.get("review_count");
	    i1 = 0;
            k = 0;	

            while (!categories.isNull(i1)) 
 	    {
	      BSDataset[index].categories[k] = categories.getString(i1);
              k++;
              i1++;
	    }

            k = 0;
            JSONObject attributes = (JSONObject) jsonObject.get("attributes");
            JSONArray attr = attributes.names();

            while ((attr != null) && (!attr.isNull(i2)))
            {
              if(attributes.get((String) attr.get(i2)) instanceof JSONObject)
              {
                JSONObject innerattr = (JSONObject) attributes.get((String) attr.get(i2));
                JSONArray attr2 = innerattr.names();
                i3 = 0;
                while ((attr2 != null) && (!attr2.isNull(i3)))
                {
                  BSDataset[index].attributes[k] = attr2.get(i3) + ":" + innerattr.get((String) attr2.get(i3));
                  k++;
                  i3++;
                }
              }
	      else
              {
                BSDataset[index].attributes[k] = attr.get(i2) + ":" + attributes.get((String) attr.get(i2));
                k++;
              }
              i2++;
            }
            index++;
	  }
	}
      }
      gcount = index;
      br.close();
    } 
    catch (FileNotFoundException ex) 
    {
      ex.printStackTrace();
    } 
    catch (IOException ex) 
    {
      ex.printStackTrace();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    } 
  }

  void parseJsonFile_review(String filePath, Clusters top_five[], int noOfClusters)
  {
    try
    {
      String line;
      FileReader reader = new FileReader(filePath);
      BufferedReader br  = new BufferedReader (new FileReader(filePath));
      JsonParser JParser = new JsonParser();
      int index, i2, i3; 

      while (null != (line = br.readLine()))
      {
        JSONObject jsonObject = new JSONObject(line);

	// get a String from the JSON object
	String business_id = (String) jsonObject.get("business_id");
        index = -1;

        JSONObject votes = (JSONObject) jsonObject.get("votes");
        int useful = (int)votes.get("useful");

        if (5 <= useful)
        {
          for (int i = 0; i < noOfClusters; i++)
          {
            for (int j = 0; j < top_five[i].datacount; j++)
            {
              if (null != top_five[i].BSDataset[j])
              {
                if (business_id.equals(top_five[i].BSDataset[j].business_id))
                {
                  if (2 == top_five[i].BSDataset[j].text_cnt)
                    break;

                  String text = (String) jsonObject.get("text");
                  if (null != text)
                  {
                    top_five[i].BSDataset[j].text = top_five[i].BSDataset[j].text + ". " + text;
                    top_five[i].BSDataset[j].text_cnt += 1;
                  }

                  break;
                }
              }
            }
          }
        }
      }
    }
    catch (FileNotFoundException ex) 
    {
      ex.printStackTrace();
    } 
    catch (IOException ex) 
    {
      ex.printStackTrace();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    } 
  }
}

