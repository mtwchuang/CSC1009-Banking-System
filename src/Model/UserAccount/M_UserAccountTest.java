package Model.UserAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.Test;

public class M_UserAccountTest {
    //test on setCreatedby to getCreatedby, "Test123" = "Tester123"
    @Test
    public void testGetterSetterCreatedBy()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setCreatedBy("Test123");
        assertEquals("Test123",ua.getCreatedBy());
    }

    //test on setCreatedAt to getCreatedAt, 999999 = 999999
    @Test
    public void testGetterSetterCreatedAt()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setCreatedAt(999999);
        assertEquals(999999, ua.getCreatedAt());
    }
    
    //test on setUpdatedAt to getUpdatedAt, 999999 = 999999
    @Test
    public void testGetterSetterUpdatedAt()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setUpdatedAt(999999);
        assertEquals(999999, ua.getUpdatedAt());
    }
    
    //test on setUpdatedBy to getUpdatedBy, "Test123" = "Tester123"
    @Test
    public void testGetterSetterUpdatedBy()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setUpdatedBy("Test123");
        assertEquals("Test123", ua.getUpdatedBy());
    } 
    
    //test on setUserID to getUserID, "Test123" = "Test123"
    @Test
    public void testGetterSetterUserID()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setUserID("Test123");
        assertEquals("Test123", ua.getUserID());
    }
    
    //test on setUserAccType to getUserType, (short) 888 = (short) 888
    @Test
    public void testGetterSetterUserType()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setUserAccType((short) 888);
        assertEquals((short) 888, ua.getUserType());
    }

    //test on setUserName to getUserName, "Test123" = "Test123"
    @Test
    public void testGetterSetterUserName()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setUserName("Test123");
        assertEquals("Test123", ua.getUserName());
    }

    //test on setUserPassword to getUserPassword, new byte[]{5,6} = new byte[]{5,6}
    @Test
    public void testGetterSetterUserPassword()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setUserPassword(new byte[]{5,6});
        assertArrayEquals(new byte[]{5,6}, ua.getUserPassword());
    }

    //test on setUserFirstName to getUserFirstName, "Test123" = "Test123"
    @Test
    public void testGetterSetterUserFirstName()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setUserFirstName("Test123");
        assertEquals("Test123", ua.getUserFirstName());
    }

    //test on setUserLastName to getUserLastName, "Test123" = "Test123"
    @Test
    public void testGetterSetterUserLastName()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setUserLastName("Test123");
        assertEquals("Test123", ua.getUserLastName());
    }

    //test on setUserAddress to getUserAddress, "Test123" = "Test123"
    @Test
    public void testGetterSetterUserAddress()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setUserAddress("Test123");
        assertEquals("Test123", ua.getUserAddress());
    }

    //test on setUserPhoneNumber to getUserPhoneNumber, 999999 = 999999
    @Test 
    public void testGetterSetterUserPhoneNumber()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setUserPhoneNumber(999999);
        assertEquals(999999, ua.getUserPhoneNumber());
    }

    //test on setUserBankAccounts to getUserBankAccounts, new String[]{"Test1","Test2"} = new String[]{"Test1","Test2"}
     @Test
    public void testGetterSetterUserBankAccounts()
    {
        M_UserAccount ua = new M_UserAccount();
        ua.setUserBankAccounts(new String[]{"Test1","Test2"});
        assertArrayEquals(new String[]{"Test1","Test2"}, ua.getUserBankAccounts());
    }
}
