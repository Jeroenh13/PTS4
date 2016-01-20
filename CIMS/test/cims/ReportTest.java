/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import java.time.LocalDateTime;
import java.time.Month;
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
    Report r2;
    
    @Before
    public void setUp() {
        r = new Report(1, "Cat in tree again", "Cat climbed into tree, does not want to leave.", "-51,54.5566", "Tat of sunshine", null, "Cat in a tree",null);
        r2 = new Report(2, "Fire", "Mexican Food Bathroom", LocalDateTime.of(1994, Month.MARCH, 2, 10, 59), LocalDateTime.now());
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
        assertEquals("is niet gelijk.",r.getLocationGPS(), "-51,54.5566");
        assertEquals("is niet gelijk.",r.getReportID(), 1);
        assertEquals("is niet gelijk.",r.getTitle(), "Cat in a tree");
        assertEquals("is niet gelijk.",r.getWeather(), "Tat of sunshine");
        
        assertEquals("is niet gelijk.",r2.getDescription(), "Fire");
        assertEquals("is niet gelijk.",r2.getTitle(), "Mexican Food Bathroom");
        assertEquals("is niet gelijk.",r2.getStartDate(), LocalDateTime.of(1994, Month.MARCH, 2, 10, 59));
        assertEquals("is niet gelijk.",r2.getReportID(), 2);        
    }
    
    @Test
    public void testEmployee()
    {
        Employee e = null;
        r.addEmployee(e);
        assertTrue("Employee is null.",r.getEmployees().size() == 0);
        
        Employee emp = new Employee(5, "Harry", "Officer","Bathroom break" , "Hong kong", "China town", "Chingchongstr", "over 9000", "Power rangers", null, LocalDateTime.MIN, LocalDateTime.MIN);
        r.addEmployee(emp);
        
        assertTrue("Did not add employee.",r.getEmployees().contains(emp));
        
        
        Employee e2 = null;
        r2.addEmployee(e2);
        assertTrue("Employee is null.",r2.getEmployees().size() == 0);
        
        Employee emp2 = new Employee(5, "Harry", "Officer","Bathroom break" , "Hong kong", "China town", "Chingchongstr", "over 9000", "Power rangers", null, LocalDateTime.MIN, LocalDateTime.MIN);
        r2.addEmployee(emp2);
        
        assertTrue("Did not add employee.",r2.getEmployees().contains(emp2));
        
        r.removeEmployee(emp);
        assertTrue("Employee is null.",r.getEmployees().size() == 0);
        
        r2.removeEmployee(emp2);
        assertTrue("Employee is null.",r2.getEmployees().size() == 0);
        
    }

}
