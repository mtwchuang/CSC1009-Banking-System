package Model.UserAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.Test;

public class M_UserAccountTest {
    @Test
    public void testGetterSetterCreatedBy()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setCreatedBy("Test123");
        assertEquals("Test123",ua.getCreatedBy());
    }

    @Test
    public void testGetterSetterCreatedAt()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setCreatedAt(999999);
        assertEquals(999999, ua.getCreatedAt());
    }
    @Test
    public void testGetterSetterUpdatedAt()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setUpdatedAt(999999);
        assertEquals(999999, ua.getUpdatedAt());
    }
    @Test
    public void testGetterSetterUpdatedBy()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setUpdatedBy("Test123");
        assertEquals("Test123", ua.getUpdatedBy());
    } 
    @Test
    public void testGetterSetterUserID()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setUserID("Test123");
        assertEquals("Test123", ua.getUserID());
    }
    @Test
    public void testGetterSetterUserType()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setUserAccType((short) 888);
        assertEquals((short) 888, ua.getUserType());
    }
    @Test
    public void testGetterSetterUserName()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setUserName("Test123");
        assertEquals("Test123", ua.getUserName());
    }
    @Test
    public void testGetterSetterUserPassword()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setUserPassword(new byte[]{5,6});
        assertArrayEquals(new byte[]{5,6}, ua.getUserPassword());
    }
    @Test
    public void testGetterSetterUserFirstName()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setUserFirstName("Test123");
        assertEquals("Test123", ua.getUserFirstName());
    }
    @Test
    public void testGetterSetterUserLastName()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setUserLastName("Test123");
        assertEquals("Test123", ua.getUserLastName());
    }
    @Test
    public void testGetterSetterUserAddress()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setUserAddress("Test123");
        assertEquals("Test123", ua.getUserAddress());
    }
    @Test
    public void testGetterSetterUserPhoneNumber()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setUserPhoneNumber(999999);
        assertEquals(999999, ua.getUserPhoneNumber());
    }
     @Test
    public void testGetterSetterUserBankAccounts()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setUserBankAccounts(new String[]{"Test1","Test2"});
        assertArrayEquals(new String[]{"Test1","Test2"}, ua.getUserBankAccounts());
    }
}
