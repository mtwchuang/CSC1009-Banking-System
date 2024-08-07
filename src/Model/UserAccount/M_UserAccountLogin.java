package Model.UserAccount;

import java.io.Serializable;

/* Model:
M_UserAccountLogin [Serializable StandAlone]
    00: userPassword - byte[]
*/

public class M_UserAccountLogin implements Serializable{
    private byte[] userPassword;

    public M_UserAccountLogin(){
    }
    public M_UserAccountLogin(byte[] userPassword){
        this.userPassword = userPassword;
    }

    public byte[] getUserPassword(){
        return userPassword;
    }

    public void setUserPassword(byte[] userPassword){
        this.userPassword = userPassword;
    }
}
