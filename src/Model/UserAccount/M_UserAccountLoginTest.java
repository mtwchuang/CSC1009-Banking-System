package Model.UserAccount;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

// unit testing for M_UserAccountLoginTest
public class M_UserAccountLoginTest 
{
    @Test
    public void testGetterSetterUserPassword()
    {
        M_UserAccountLogin ual = new M_UserAccountLogin();
        ual.setUserPassword(new byte[] {1,2});
        assertArrayEquals(new byte[] {1,2}, ual.getUserPassword());
    }
}
