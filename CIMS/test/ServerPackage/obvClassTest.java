package ServerPackage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Server.obvClass;
import cims.Report;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Bas
 */
public class obvClassTest {
    
    public obvClassTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
     private obvClass ob;
    
    @Before
    public void setUp() {
        //ob = new obvClass(); 
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    /*
    @Test
    public void testAddNewReport()
    {
        Report r = new Report();
        ob.addNewReport(r);
        assertEquals("Objecten niet gelijk", (Report)ob.returnReport(),r);
    }*/
}
