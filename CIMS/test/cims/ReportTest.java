/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import java.time.LocalDateTime;
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
public class ReportTest {
    
    public ReportTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    Report r;
    
    @Before
    public void setUp() {
        r = new Report(1, "Cat in tree again", "Cat climbed into tree, does not want to leave.", "-51,54.5566", "Tat of sunshine", null, "Cat in a tree");
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
    public void testGetters()
    {
        assertEquals("is niet gelijk.",r.getDescription(), "Cat in tree again");
        assertEquals("is niet gelijk.",r.getExtraInformation(), "Cat climbed into tree, does not want to leave.");
        assertEquals("is niet gelijk.",r.getLocation(), "-51,54.5566");
        assertEquals("is niet gelijk.",r.getReportID(), 1);
        assertEquals("is niet gelijk.",r.getTitle(), "Cat in a tree");
        assertEquals("is niet gelijk.",r.getWeather(), "Tat of sunshine");
    }
    /*
    @Test
    public void testEmployee()
    {
        Employee e = null;
        r.addEmployee(e);
        Employee emp = new Employee(5, "Harry", "Officer","Bathroom break" , "Hong kong", "China town", "Chingchongstr", "over 9000", "Power rangers", null, LocalDateTime.MIN, LocalDateTime.MIN);
        r.addEmployee(emp);
        
        assertFalse("Employee is null.",r.getEmployees().contains(e));
        assertTrue("Did not add employee.",r.getEmployees().contains(emp));
    }
*/
}
