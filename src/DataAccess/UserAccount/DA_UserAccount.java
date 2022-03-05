package DataAccess.UserAccount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Model.UserAccount.*;
import ModelView.Global;

public class DA_UserAccount {
    public List<M_IUserAccount> dbUserAccounts_GetAll() throws IOException{
        List<M_IUserAccount> userAccounts = new ArrayList<M_IUserAccount>();

        String line;
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(getDynamicDbPath() + Global.dbUserAccount));
            //Skip first line; header line
            br.readLine();

            line = br.readLine();
            while(line != null){
                String[] dataSegments = line.split(",");

                //Data mapping
                Model.UserAccount.M_UserAccount currentAccount = new Model.UserAccount.M_UserAccount();

                currentAccount.setCreatedBy(dataSegments[0]);
                currentAccount.setCreatedAt(Long.parseLong(dataSegments[1]));
                currentAccount.setUpdatedBy(dataSegments[2]);
                currentAccount.setUpdatedAt(Long.parseLong(dataSegments[3]));

                currentAccount.setUserID(dataSegments[4]);
                currentAccount.setUserAccType(Short.parseShort(dataSegments[5]));
                currentAccount.setUserName(dataSegments[6]);
                currentAccount.setUserPassword(dataSegments[7]);
                currentAccount.setUserFirstName(dataSegments[8]);
                currentAccount.setUserLastName(dataSegments[9]);
                currentAccount.setUserAddress(dataSegments[10]);
                currentAccount.setUserPhoneNumber(Long.parseLong(dataSegments[11]));

                userAccounts.add((M_IUserAccount)currentAccount);
            }
        }
        finally{
            br.close();
        }
        
        return userAccounts;
    }
    public M_IUserAccount dbUserAccounts_GetByUserName(String userName) throws IOException{
        String line;
        BufferedReader br = null;
        M_IUserAccount targetAccount = null;

        try{
            System.out.println(Global.dbUserAccount);
            br = new BufferedReader(new FileReader(getDynamicDbPath() + Global.dbUserAccount));
            //Skip first line; header line
            br.readLine();

            line = br.readLine();
            while(line != null){
                String[] dataSegments = line.split(",");
                
                if(dataSegments[6] == userName){
                    //Data mapping
                    M_UserAccount currentAccount = new M_UserAccount();

                    currentAccount.setCreatedBy(dataSegments[0]);
                    currentAccount.setCreatedAt(Long.parseLong(dataSegments[1]));
                    currentAccount.setUpdatedBy(dataSegments[2]);
                    currentAccount.setUpdatedAt(Long.parseLong(dataSegments[3]));

                    currentAccount.setUserID(dataSegments[4]);
                    currentAccount.setUserAccType(Short.parseShort(dataSegments[5]));
                    currentAccount.setUserName(dataSegments[6]);
                    currentAccount.setUserPassword(dataSegments[7]);
                    currentAccount.setUserFirstName(dataSegments[8]);
                    currentAccount.setUserLastName(dataSegments[9]);
                    currentAccount.setUserAddress(dataSegments[10]);
                    currentAccount.setUserPhoneNumber(Long.parseLong(dataSegments[11]));

                    targetAccount = (M_IUserAccount)currentAccount;
                    break;
                }
            }
            br.close();
        }
        catch(Exception e)
        {
            throw e;
        }
        return targetAccount;
    }
    public M_IUserAccount dbUserAccounts_GetByUserID(String userID) throws IOException{
        String line;
        BufferedReader br = null;
        M_IUserAccount targetAccount = null;

        try{
            br = new BufferedReader(new FileReader(getDynamicDbPath() + Global.dbUserAccount));
            //Skip first line; header line
            br.readLine();

            line = br.readLine();
            while(line != null){
                String[] dataSegments = line.split(",");
                
                if(dataSegments[4] == userID){
                    //Data mapping
                    Model.UserAccount.M_UserAccount currentAccount = new Model.UserAccount.M_UserAccount();

                    currentAccount.setCreatedBy(dataSegments[0]);
                    currentAccount.setCreatedAt(Long.parseLong(dataSegments[1]));
                    currentAccount.setUpdatedBy(dataSegments[2]);
                    currentAccount.setUpdatedAt(Long.parseLong(dataSegments[3]));

                    currentAccount.setUserID(dataSegments[4]);
                    currentAccount.setUserAccType(Short.parseShort(dataSegments[5]));
                    currentAccount.setUserName(dataSegments[6]);
                    currentAccount.setUserPassword(dataSegments[7]);
                    currentAccount.setUserFirstName(dataSegments[8]);
                    currentAccount.setUserLastName(dataSegments[9]);
                    currentAccount.setUserAddress(dataSegments[10]);
                    currentAccount.setUserPhoneNumber(Long.parseLong(dataSegments[11]));

                    targetAccount = (M_IUserAccount)currentAccount;
                    break;
                }
            }
        }
        finally{
            br.close();
        }
        
        return targetAccount;
    }

    private String getDynamicDbPath(){
        File dynamicDir = new File("");
        String localDir = dynamicDir.getAbsolutePath();
        return localDir;
    }
}