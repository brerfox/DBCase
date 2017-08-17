package services;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Graduate
 */
public class PropertyServiceTest {

    public PropertyServiceTest() {
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
    public void testDBPath()
    {
        PropertyService ps = PropertyService.getInstance();
        String p1 = ps.getProperty("dbPath");
        assertEquals("jdbc:mysql://192.168.99.100/",p1);
    }

    @Test
    public void testDBDriver()
    {
        PropertyService ps = PropertyService.getInstance();
        String p1 = ps.getProperty("dbDriver");
        assertEquals("com.mysql.jdbc.Driver",p1);
    }

    @Test
    public void testDBName()
    {
        PropertyService ps = PropertyService.getInstance();
        String p1 = ps.getProperty("dbName");
        assertEquals("db_grad_cs_1917",p1);
    }

    @Test
    public void testDBUser()
    {
        PropertyService ps = PropertyService.getInstance();
        String p1 = ps.getProperty("dbUser");
        assertEquals("test-user",p1);
    }

    @Test
    public void testDBPwd()
    {
        PropertyService ps = PropertyService.getInstance();
        String p1 = ps.getProperty("dbPwd");
        assertEquals("test-user",p1);
    }

    @Test
    public void testProperties()
    {
        PropertyService ps = PropertyService.getInstance();
        Properties properties = new Properties();
        properties.setProperty("dbDriver", "com.mysql.jdbc.Driver");
        properties.setProperty("dbPath", "jdbc:mysql://192.168.99.100/");
        properties.setProperty("dbName", "db_grad_cs_1917");
        properties.setProperty("dbUser", "test-user");
        properties.setProperty("dbPwd", "test-user");
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("dbConnector.properties")) {
            properties.load(input);

            System.out.println("Get Properties: ");
            System.out.println(properties.stringPropertyNames());

        } catch (IOException ex) {
            System.out.println("Problem with property file !!!");
            ex.printStackTrace();
        }
        assertEquals(properties,ps.getProperties());
    }
}