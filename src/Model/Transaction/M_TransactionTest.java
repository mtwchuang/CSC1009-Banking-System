package Model.Transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;

public class M_TransactionTest {
    
    @Test
    public void testGetterSetterCreatedBy(){

        M_Transaction t = new M_Transaction();
        t.setCreatedBy("Test123");
        assertEquals("Test123", t.getCreatedBy());
        
    }

    @Test
    public void testGetterSetterCreatedAt(){
        M_Transaction t = new M_Transaction();
        t.setCreatedAt(999999);
        assertEquals(999999, t.getCreatedAt());
    }
}
