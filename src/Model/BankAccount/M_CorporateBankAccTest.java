package Model.BankAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class M_CorporateBankAccTest 
{
    @Test
    public void testGetterSetterTransactOnlyIDs()
    {
        M_CorporateBankAcc cba = new M_CorporateBankAcc();
        String[] test = {"test1","test2"};
        cba.setBankAccTransactOnlyIDs(test);
        assertEquals(test, cba.getBankAccTransactOnlyIDs());
    }
    @Test
    public void testGetterSubHoldersIDs()
    {
        M_CorporateBankAcc cba = new M_CorporateBankAcc();
    }
}
