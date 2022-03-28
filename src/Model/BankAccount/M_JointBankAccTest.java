package Model.BankAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

// Unit testing for methods in M_JointBankAcc
public class M_JointBankAccTest 
{
    //test on setBankAccHolderID to getBankAccHolderID, "Tester123" = "Tester123"
    @Test
    public void testGetterSetterSubHolderID()
    {
        M_JointBankAcc jbc = new M_JointBankAcc();
        jbc.setBankAccHolderID("Tester123"); 
        assertEquals("Tester123", jbc.getBankAccHolderID());
    }
}
