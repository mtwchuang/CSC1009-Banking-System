package Model.UserAccount._UnitTesting;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import Model.UserAccount.M_UserAccountLogin;

// unit testing for M_UserAccountLoginTest
public class M_UserAccountLoginTest 
{
    //test on setUserPassword to getUserPassword, new byte[] {1,2} = new byte[] {1,2}
    @Test
    public void testGetterSetterUserPassword()
    {
        M_UserAccountLogin ual = new M_UserAccountLogin();
        ual.setUserPassword(new byte[] {1,2});
        assertArrayEquals(new byte[] {1,2}, ual.getUserPassword());
    }
}
