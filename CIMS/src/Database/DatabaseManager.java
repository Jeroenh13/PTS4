package Database;

import cims.Employee;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import cims.Helpline;
import cims.Report;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * *
 *
 * Class used for a connection to communicate with the database
 */
public class DatabaseManager {

    private Connection conn = null;

    public DatabaseManager() {
    }

    /**
     * *
     * Opens the database connection
     *
     * @return if the opening of the connection is succesfull
     */
    private boolean openConnection() {
        try {
            //conn = DriverManager.getConnection("jdbc:mysql://athena01.fhict.local/dbi298273?user=dbi298273&password=AbEc65A52w");
            conn = DriverManager.getConnection("jdbc:mysql://athena01.fhict.local/dbi296086?user=dbi296086&password=PqGVPb5cuG");
            return true;
        } catch (SQLException e) {
            conn = null;
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
            return false;
        }
    }

    /**
     * *
     * Closes the connection
     *
     * @return succesrate
     */
    private boolean closeConnection() {
        try {
            conn.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * *
     * Tests the connection to the database
     *
     * @return succes rate
     * @throws java.sql.SQLException closing the connections
     */
    public boolean testConnection() throws SQLException {
        if (!openConnection()) {
            return false;
        }
        ResultSet result = null;
        Statement statement;
        boolean returnval = false;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery("Select * from Personeel");
            statement.closeOnCompletion();
            result.last();
            if (result.getRow() != 0) {
                returnval = true;
            }
            return returnval;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
            conn = null;
            return false;
        } finally {
            if (result != null) {
                result.close();
            }
            closeConnection();
        }
    }
    
    // <editor-fold desc="UnitsAssign">
    public ObservableList<Employee> getEmployees(String query, HashMap<String, ObservableList> specificationTypes, ObservableList<Employee> employees) {
        HashMap<String, String> values = new HashMap<>();
        int level;
        employees.clear();
        
        if (!openConnection()) {
            return null;
        }
        
        ResultSet result = null;
        Statement statement = null;
        
        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(query);
            
            while (rs.next()) {
                values.clear();
                for (Map.Entry<String, ObservableList> entry : specificationTypes.entrySet()){
                    values.put(entry.getKey(), rs.getString(entry.getKey()));
                }
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
                LocalDateTime startReport = null;
                LocalDateTime endReport = null;
                if(values.get("ReportStartDate") != null){
                    startReport = LocalDateTime.parse(values.get("ReportStartDate"), formatter); 
                }
                if(values.get("ReportEndDate") != null){
                    endReport = LocalDateTime.parse(values.get("ReportEndDate"), formatter); 
                }
                
                Report report = null;
                if(values.get("reportID") != null){
                    report = new Report(Integer.parseInt(values.get("reportID")), values.get("description"), values.get("title"), startReport, endReport); 
                }
                
                LocalDateTime startEmp = null;
                LocalDateTime endEmp = null;
                if(values.get("start") != null){
                    startEmp =LocalDateTime.parse(values.get("start"), formatter); 
                }
                if(values.get("end") != null){
                    endEmp = LocalDateTime.parse(values.get("end"), formatter);
                }
                
                int badgeNr = Integer.parseInt(values.get("badgeNR"));
                Employee employee = new Employee(badgeNr, values.get("name"), values.get("function"), values.get("available"), values.get("department"), values.get("region"), values.get("commune"),values.get("level"), values.get("team"),report,startEmp,endEmp);
                employees.add(employee);
            }
        } catch (Exception ex) {
            System.out.println("Something went wrong");
            System.out.println(ex.getMessage());
        }
        finally{
            closeConnection();
        }

        return employees;
    }
    
    public HashMap<String, ObservableList> getSpeciafications(HashMap<String, ObservableList> specifications) {
        String query = "DESCRIBE vwemployee";
        String type;
        String spec;
        
        if (!openConnection()) {
            return null;
        }
        
        ResultSet result = null;
        Statement statement = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(query);

            while (result.next()) {
                type = result.getString("Type"); // can be int(11), varchar(255) or datetime
                spec = result.getString("Field"); 
                ObservableList<String> input = FXCollections.observableArrayList();
                if(("reportID".equals(spec) || "description".equals(spec) || "ReportStartDate".equals(spec) || "ReportEndDate".equals(spec)) && !"helpline".equals(spec)){
                    input.add("report");
                    specifications.put(spec, input);
                }else if(!"datetime".equals(type) && !"name".equals(spec) && !"badgeNR".equals(spec) && !"title".equals(spec) && !"helpline".equals(spec)){
                    input.add("no selection");
                    specifications.put(spec, input);
                }else if(!"helpline".equals(spec)){
                    input.add("table");
                    specifications.put(spec, input);
                }
            }
            
        } catch (Exception e) {
            System.out.println(e);
            specifications = null;
        } finally {
            try {
                result.close();
                statement.close();
                closeConnection();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        
        return specifications;
    }
    
    public HashMap<String, ObservableList> getSpeciaficationsValues(String query, HashMap<String, ObservableList> specificationValues) {       
        ObservableList<String> valuesSpec;
        String spec;
        
        if (!openConnection()) {
            return null;
        }
        
        ResultSet result = null;
        Statement statement = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(query);

            while (result.next()) {
                for (Map.Entry<String, ObservableList> entry : specificationValues.entrySet()){
                    if(entry.getValue().get(0).equals("no selection")){ 
                        spec = entry.getKey();
                        valuesSpec = entry.getValue();
                        String value = result.getString(spec);
                        if(!valuesSpec.contains(value)){
                            valuesSpec.add(value);
                        }
                    }
                }
            } 
        } catch (Exception e) {
            System.out.println(e);
            specificationValues = null;
        } finally {
            try {
                result.close();
                statement.close();
                closeConnection();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        
        return specificationValues;
    }
    
    public ObservableList getIncidents(String query, ObservableList<Report> reports) {
        if (!openConnection()) {
            return null;
        }
        ResultSet result = null;
        Statement statement = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
                LocalDateTime start = LocalDateTime.parse(result.getString("ReportStartDate"), formatter);
                reports.add(new Report(result.getInt("reportID"), result.getString("description"), result.getString("title"), start, null));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                result.close();
                statement.close();
                closeConnection();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        
        return reports;
    }
    
    // </editor-fold>

    /**
     * saves the report with the given items.
     *
     * @param repo report to add
     * @param helplineid helplineid
     * @return succes
     */
    public boolean saveReport(Report repo,int helplineid) {
        boolean succes = false;
        if (!openConnection()) {
            return succes;
        }
        try {
            CallableStatement cs = null;
            cs = conn.prepareCall("{call spInjectReport(?,?,?,0,?)}");
            cs.setString(1, repo.getDescription());
            cs.setString(2, repo.getLocation());
            cs.setInt(3, helplineid);
            cs.setString(4, repo.getTitle());
            cs.execute();
            succes = true;
            cs.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        finally{
            closeConnection();
        }
        return succes;
    }

    public ArrayList getHelpLines() {
        if (!openConnection()) {
            return null;
        }
        ResultSet result = null;
        Statement statement = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery("Select helplineID,name from Helpline");
            ArrayList lines = new ArrayList<Helpline>();
            while (result.next()) {
                lines.add(new Helpline(result.getInt("helplineID"), result.getString("name")));
            }
            return lines;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        } finally {
            try {
                result.close();
                statement.close();
                closeConnection();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
