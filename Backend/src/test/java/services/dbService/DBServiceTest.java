/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.dbService;

import junit.framework.TestCase;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import services.PropertyService;
import services.dbService.entities.Deal;

/**
 *
 * @author Graduate
 */
public class DBServiceTest extends TestCase {
    
    public DBServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testValidateConnection()
    {
        DBService dbs = new DBService(PropertyService.getInstance());
        assertEquals(dbs.validateConnection(), true);
        dbs.printConnectInfo();
    }
    
    @Test
    public void testValidateUser() throws DBException
    {
        DBService dbs = new DBService(PropertyService.getInstance());
        
        assertTrue(dbs.validateUser("debs", "gradprog2016@02"));
    }
    
    
    public boolean testDealEquality(Deal d1, Deal d2) throws DBException
    {
        boolean ans = true;
        if(d1.getCounterparty_name() == null ? d2.getCounterparty_name() != null : !d1.getCounterparty_name().equals(d2.getCounterparty_name()))
            ans = false;
        if(d1.getDeal_amount() == null ? d2.getDeal_amount() != null : !d1.getDeal_amount().equals(d2.getDeal_amount()))
            ans = false;
        if(d1.getDeal_id() != d2.getDeal_id())
            ans = true;
        if(d1.getDeal_instrument_id() != d2.getDeal_instrument_id())
            ans = false;
        if(d1.getDeal_quantity() != d2.getDeal_quantity())
            ans =false;
        if(d1.getDeal_time() == null ? d2.getDeal_time() != null : !d1.getDeal_time().equals(d2.getDeal_time()))
            ans =false;
        if(d1.getDeal_type() == null ? d2.getDeal_type() != null : !d1.getDeal_type().equals(d2.getDeal_type()))
            ans = false;
        if(d1.getInstrument_name() == null ? d2.getInstrument_name() != null : !d1.getInstrument_name().equals(d2.getInstrument_name()))
            ans =false;
        
        return ans;
    }
    
    @Test
    public void testEqual() throws DBException
    {
        Deal d = new Deal(20001,"2017-07-28T17:06:29.955","B","5820.48",3,1002,"Lina","Borealis");
        DBService dbs = new DBService(PropertyService.getInstance());
        Long id = new Long(20001);
        Deal d2 = dbs.getDealById(id);
        
        int i = 20001;
        assertTrue(testDealEquality(d, d2));
        
    }
}
