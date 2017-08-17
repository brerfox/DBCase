/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.dbService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import services.PropertyService;
import services.dbService.DBException;
import services.dbService.DBService;
import services.dbService.entities.Deal;
import services.dbService.entities.Instrument;

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

    @Test
    public void testWrongUser() throws DBException
    {
        DBService dbs = new DBService(PropertyService.getInstance());

        assertFalse(dbs.validateUser("wera", "gradprog201602"));
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
    public void testDealEqual() throws DBException
    {
        Deal d = new Deal(20001,"2017-07-28T17:06:29.955","B","5820.48",3,1002,"Lina","Borealis");
        DBService dbs = new DBService(PropertyService.getInstance());
        Long id = new Long(20001);
        Deal d2 = dbs.getDealById(id);

        assertTrue(testDealEquality(d, d2));

    }

    public boolean testInstrumentEquality(Instrument i1, Instrument i2) throws DBException
    {
        boolean ans = true;
        if(i1.getInstrument_id() != i2.getInstrument_id())
            ans = false;
        if(i1.getInstrument_name() == null ? i2.getInstrument_name() != null : !i1.getInstrument_name().equals(i2.getInstrument_name()))
            ans = false;
        return ans;
    }
    @Test
    public void testInstrumentEquality() throws DBException
    {
        Instrument i = new Instrument(1003,"Celestial");
        DBService dbs = new DBService(PropertyService.getInstance());
        Long id = new Long(1003);
        Instrument i2 = dbs.getInstrumentByID(id);
        assertTrue(testInstrumentEquality(i, i2));
    }

    @Test
    public void testGetAllDeals() throws DBException
    {
        List<Deal> dealList = new LinkedList<>();
        Deal d1 = new Deal(20042,"2017-07-28T17:06:29.976","B","3474.17",713,1001,"Lina","Astronomica");
        Deal d2 = new Deal(20049,"2017-07-28T17:06:30","S","3444.86",4,1001,"Nidia","Astronomica");

        dealList.add(d1);
        dealList.add(d2);

        DBService dbs = new DBService(PropertyService.getInstance());
        List<Deal> testList;
        testList = dbs.getAllDeals();
        int i=0;
        boolean ans = true;
        for(i=0; i<dealList.size(); i++)
        {
            if(testDealEquality(dealList.get(i),testList.get(i)) == false)
                ans = false;
        }
        assertTrue(ans);
    }

    @Test
    public void testGetAllInstruments() throws DBException
    {
        List<Instrument> insList = new LinkedList<>();
        Instrument i1 = new Instrument(1001,"Astronomica");
        Instrument i2 = new Instrument(1002,"Borealis");
        insList.add(i1);
        insList.add(i2);

        DBService dbs = new DBService(PropertyService.getInstance());
        List<Instrument> testList = dbs.getAllInstruments();
        boolean ans = true;
        for(int i=0; i<insList.size(); i++)
        {
            if(testInstrumentEquality(insList.get(i), testList.get(i)) == false)
                ans = false;
        }

        assert(ans);
    }

    @Test
    public void testGetDealsPage() throws DBException
    {
        List<Deal> dealList = new LinkedList<>();
        Deal d1 = new Deal(20042,"2017-07-28T17:06:29.976","B","3474.17",713,1001,"Lina","Astronomica");
        Deal d2 = new Deal(20049,"2017-07-28T17:06:30","S","3444.86",4,1001,"Nidia","Astronomica");

        dealList.add(d1);
        dealList.add(d2);

        DBService dbs = new DBService(PropertyService.getInstance());
        List<Deal> testList;
        testList = dbs.getDealsPage(1,2);
        int i=0;
        boolean ans = true;
        for(i=0; i<dealList.size(); i++)
        {
            if(testDealEquality(dealList.get(i),testList.get(i)) == false)
                ans = false;
        }
        assertTrue(ans);
    }

    @Test
    public void testGetDealsPageFail() throws DBException
    {
        boolean ans = true;
        try{
            List<Deal> dealList = new LinkedList<>();
            Deal d1 = new Deal(20042,"2017-07-28T17:06:29.976","B","3474.17",713,1001,"Lina","Astronomica");
            Deal d2 = new Deal(20049,"2017-07-28T17:06:30","S","3444.86",4,1001,"Nidia","Astronomica");

            dealList.add(d1);
            dealList.add(d2);

            DBService dbs = new DBService(PropertyService.getInstance());
            List<Deal> testList;
            testList = dbs.getDealsPage(1,1);
            int i=0;
            Deal d;
            for(i=0; i<dealList.size(); i++)
            {
                if(testDealEquality(dealList.get(i),testList.get(i)) == false)
                    ans = false;
            }
        }
        catch(IndexOutOfBoundsException e){ ans = false;}
        assertFalse(ans);
    }

}