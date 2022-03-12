package DataAccess;

import java.io.BufferedReader;
import java.io.FileReader;

import ModelView.MV_Global;

public class DA_Settings{
    public String[] dbSelectionOpt_GetByKey(String key) throws Exception{
        String line;
        String[] dataSegments = null;
        BufferedReader br = null;

        try{
            br = new BufferedReader(new FileReader(MV_Global.dbSettings));
    
            line = br.readLine();
            while(line != null){
                dataSegments = line.split("\\|");
                if(dataSegments[0].toLowerCase().equals(key.toLowerCase())) break;
                else line = br.readLine();
            }
        }
        finally{
            br.close();
        }
        return dataSegments;
    }
}