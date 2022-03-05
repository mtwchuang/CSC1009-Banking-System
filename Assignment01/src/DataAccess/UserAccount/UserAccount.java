package DataAccess.UserAccount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Model.UserAccount.IUserAccount;
import ModelView.Global;

public class UserAccount {
    public List<IUserAccount> dbGetUserAccounts() throws IOException{
        List<IUserAccount> userAccounts = new ArrayList();

        String line;
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(getDynamicDbPath() + Global.dbUserAccount));
            br.readLine();
            line = br.readLine();
        }
        finally{
            br.close();
        }
        
        return userAccounts;
    }

    private String getDynamicDbPath(){
        File dynamicDir = new File("");
        String localDir = dynamicDir.getAbsolutePath();
        return localDir + "\\data";
    }
}