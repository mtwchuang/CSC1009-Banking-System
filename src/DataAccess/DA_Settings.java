package DataAccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

import ModelView.MV_Global;

public class DA_Settings{
    //Retrive settings by key
    public String[] dbSettings_GetByKey(String key) throws Exception{
        String line;
        String[] dataSegments = null;
        BufferedReader br = null;

        try{
            br = new BufferedReader(new FileReader(MV_Global.dbSettings));
    
            line = br.readLine();
            while(line != null){
                if(!line.contains("//")){
                    dataSegments = line.split("\\|");
                    if(dataSegments[0].toLowerCase().equals(key.toLowerCase())) break;
                }
                line = br.readLine();
            }
        }
        finally{
            br.close();
        }
        return  Arrays.copyOfRange(dataSegments, 1, dataSegments.length);
    }
}