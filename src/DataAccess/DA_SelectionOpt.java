package DataAccess;

import java.io.BufferedReader;
import java.io.FileReader;

import ModelView.MV_Global;

public class DA_SelectionOpt{
    public String[] dbSelectionOpt_GetByKey(String key) throws Exception{
        String line;
        String[] dataSegments = null;
        BufferedReader br = null;
        boolean foundTarget = false;

        try{
            br = new BufferedReader(new FileReader(MV_Global.dbSelectionOpt));
            //Skip first line; header line
            br.readLine();
    
            line = br.readLine();
            while(line != null && !foundTarget){
                dataSegments = line.split("\\|");
                if(dataSegments[0].toLowerCase().equals(key.toLowerCase())) 
                    foundTarget = true;
                else line = br.readLine();
            }
        }
        finally{
            br.close();
        }
        return dataSegments;
    }
}