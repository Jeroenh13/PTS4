package Database;

import cims.Employee;
import cims.Vehicle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import cims.Helpline;
import cims.Report;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
        employees.clear();

        if (!openConnection()) {
            return null;
        }

        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(query);

            while (rs.next()) {
                values.clear();
                for (Map.Entry<String, ObservableList> entry : specificationTypes.entrySet()) {
                    values.put(entry.getKey(), rs.getString(entry.getKey()));
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
                LocalDateTime startReport = null;
                LocalDateTime endReport = null;
                if (values.get("reportstartdate") != null) {
                    startReport = LocalDateTime.parse(values.get("reportstartdate"), formatter);
                }
                if (values.get("reportenddate") != null) {
                    endReport = LocalDateTime.parse(values.get("reportenddate"), formatter);
                }

                Report report = null;
                if (values.get("reportid") != null) {
                    report = new Report(Integer.parseInt(values.get("reportid")), values.get("description"), values.get("title"), startReport, endReport);
                }

                LocalDateTime startEmp = null;
                LocalDateTime endEmp = null;
                if (values.get("startdate") != null) {
                    startEmp = LocalDateTime.parse(values.get("startdate"), formatter);
                }
                if (values.get("enddate") != null) {
                    endEmp = LocalDateTime.parse(values.get("enddate"), formatter);
                }

                int badgeNr = -1;
                if (values.get("badgenr") != null) {
                    badgeNr = Integer.parseInt(values.get("badgenr"));
                }

                Employee employee = new Employee(badgeNr, values.get("name"), values.get("function"), values.get("available"), values.get("department"), values.get("region"), values.get("commune"), values.get("level"), values.get("team"), report, startEmp, endEmp);
                employees.add(employee);
            }
        } catch (Exception ex) {
            System.out.println("Something went wrong");
            System.out.println(ex.getMessage());
            System.out.println(ex);
        } finally {
            closeConnection();
        }

        return employees;
    }

    public HashMap<String, ObservableList> getSpecifications(HashMap<String, ObservableList> specifications) {
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
                if (("reportid".equals(spec) || "description".equals(spec) || "reportstartdate".equals(spec) || "reportenddate".equals(spec)) && !"helpline".equals(spec)) {
                    input.add("report");
                    specifications.put(spec, input);
                } else if (!"datetime".equals(type) && !"name".equals(spec) && !"badgenr".equals(spec) && !"title".equals(spec) && !"helpline".equals(spec)) {
                    input.add("no selection");
                    specifications.put(spec, input);
                } else if (!"helpline".equals(spec)) {
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

    public HashMap<String, ObservableList> getSpecificationsValues(String query, HashMap<String, ObservableList> specificationValues) {
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
                for (Map.Entry<String, ObservableList> entry : specificationValues.entrySet()) {
                    if (entry.getValue().get(0).equals("no selection")) {
                        spec = entry.getKey();
                        valuesSpec = entry.getValue();
                        String value = result.getString(spec);
                        if (!valuesSpec.contains(value)) {
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

    public ObservableList getNewIncidents(String query, ObservableList<Report> reports) {
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
                LocalDateTime start = LocalDateTime.parse(result.getString("startdate"), formatter);
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

    public boolean updateEmployeeForReport(Employee emp, Report report, String helpline) {
        boolean succes = false;
        String queryUpdate = "UPDATE employeereport SET EmpFromDate = ? ,EmpTillDate = ? WHERE EmployeeID = ? AND ReportID = ?;";

        if (!openConnection()) {
            return succes;
        }

        try {

            PreparedStatement preparedStmt = null;
            conn.setAutoCommit(true);
            preparedStmt = conn.prepareStatement(queryUpdate);

            Timestamp timestamp = Timestamp.valueOf(emp.getStart());
            java.sql.Timestamp date = new java.sql.Timestamp(timestamp.getTime());
            preparedStmt.setTimestamp(1, date);
            if (emp.getEnd() != null) {
                Timestamp timesp = Timestamp.valueOf(emp.getEnd());
                java.sql.Timestamp datee = new java.sql.Timestamp(timesp.getTime());
                preparedStmt.setTimestamp(2, datee);
            } else {
                preparedStmt.setTimestamp(2, null);
            }
            preparedStmt.setInt(3, emp.getBadgeNR());
            preparedStmt.setInt(4, report.getReportID());

            preparedStmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection();
        }
        return succes;
    }

    public boolean saveEmployeeForReport(Employee emp, Report report, String helpline) {
        boolean succes = false;

        String queryInsert = "INSERT INTO employeereport (EmployeeID, ReportID, EmpFromDate, EmpTillDate) VALUES (? ,? ,?, ?);";

        if (!openConnection()) {
            return succes;
        }

        try {
            PreparedStatement preparedStmt = null;
            conn.setAutoCommit(true);
            preparedStmt = conn.prepareStatement(queryInsert);
            preparedStmt.setInt(1, emp.getBadgeNR());
            preparedStmt.setInt(2, report.getReportID());
            Timestamp timeStart = Timestamp.valueOf(emp.getStart());
            java.sql.Timestamp dateStart = new java.sql.Timestamp(timeStart.getTime());
            preparedStmt.setTimestamp(3, dateStart);
            if (emp.getEnd() != null) {
                Timestamp timeEnd = Timestamp.valueOf(emp.getEnd());
                java.sql.Timestamp dateEnd = new java.sql.Timestamp(timeEnd.getTime());
                preparedStmt.setTimestamp(4, dateEnd);
            } else {
                preparedStmt.setTimestamp(4, null);
            }

            boolean f = preparedStmt.execute();
            System.out.println(f);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
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
    
    public int getApproachID(int helplineID, int reportID)
    {
        if (!openConnection())
            return -1;
        ResultSet result = null;
        Statement statement = null;
        try
        {
            statement = conn.createStatement();
            result = statement.executeQuery("SELECT HelplineReportID FROM helplinereport WHERE helplineID = " + helplineID + " AND reportID = " + reportID);
            
            while(result.next())
            {
                return result.getInt("helplinereportID");
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
            return -1;
        }
        finally
        {
            try
            {
                result.close();
                statement.close();
                closeConnection();
            }
            catch (Exception ex)
            {
                System.out.println(ex);
            }
        }
        return -1;
    }

    public boolean saveApproach(int helplinereportID, String approach, int helplineID, int reportID) {
        boolean succes = false;
        if (!openConnection()) {
            return succes;
        }
        PreparedStatement statement = null;
        try {
            String query = "REPLACE INTO helplinereport(helplinereportID, helplineID, reportID, approach) VALUES (?,?,?,?)";
            statement = conn.prepareStatement(query);
            statement.setInt(1, helplinereportID);
            statement.setInt(2, helplineID);
            statement.setInt(3, reportID);
            statement.setString(4, approach);
            statement.executeUpdate();
            succes = true;
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection();
        }
        return succes;
    }
    
    public boolean closeReport(int reportID, LocalDateTime enddate)
    {
        boolean succes = false;
        if (!openConnection()){
            return succes;
        }
        PreparedStatement statement = null;
        try
        {
            String query = "UPDATE report SET enddate=? WHERE reportid =?";
            statement = conn.prepareStatement(query);
            
            Timestamp timestamp = Timestamp.valueOf(enddate);
            java.sql.Timestamp date = new java.sql.Timestamp(timestamp.getTime());
            statement.setTimestamp(1, date);
            statement.setInt(2, reportID);
            statement.executeUpdate();
            succes = true;
            conn.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        finally
        {
            closeConnection();
        }
        return succes;
    }

    public ArrayList<Vehicle> getAllVehicles(int helplineID) {
        if (!openConnection()) {
            return null;
        }
        ResultSet result = null;
        Statement statement = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery("SELECT * FROM vehicle WHERE HelplineID = " + helplineID);
            ArrayList lines = new ArrayList<Vehicle>();
            while (result.next()) {
                lines.add(new Vehicle(result.getInt("VehicleID"), result.getString("VehicleType"), result.getInt("HelplineID"), result.getInt("InUse")));
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

    public ArrayList<Employee> getAllEmployees(String name) {
        if (!openConnection()) {
            System.out.println("Database connection failed!");
            return null;
        }
        ResultSet result = null;
        Statement statement = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery("SELECT * FROM vwemployee WHERE Helpline = '" + name + "'");
            ArrayList lines = new ArrayList<>();
            while (result.next()) {
                
                boolean exists = false;
                String available = result.getBoolean("Available") ? "Yes" : "No";

                LocalDateTime reportStart = null;
                LocalDateTime reportEnd = null;

                if (result.getDate("reportStartDate") != null) {
                    reportStart = LocalDateTime.parse(result.getString("reportStartDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
                }

                if (result.getDate("reportEndDate") != null) {
                    reportEnd = LocalDateTime.parse(result.getString("reportEndDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
                }
                
                for(Object e : lines)
                {
                    Employee emp = (Employee)e;
                    if(emp.getBadgeNR() == result.getInt("BadgeNR"))
                    {
                        exists = true;
                        break;
                    }
                }
                if(!exists)
                    lines.add(new Employee(result.getInt("BadgeNR"), result.getString("Name"), result.getString("Function"), available, result.getString("Department"), result.getString("Region"), result.getString("Commune"), result.getString("level"), result.getString("Team"), null, reportStart, reportEnd));
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

    public ArrayList<Report> getAllReports(String name) {
        if (!openConnection()) {
            return null;
        }
        ResultSet result = null;
        Statement statement = null;
        try {
            statement = conn.createStatement();
            //result = statement.executeQuery("SELECT * FROM vwhelplinereport");
            result = statement.executeQuery("SELECT * FROM vwhelplinereport WHERE Helpline = '" + name +"'");
            ArrayList reports = new ArrayList<>();
            while (result.next()) {
                //public Report(int reportID, String description, String extraInformation, String location, String weather, ArrayList<Helpline> helpline, String title)
                
                LocalDateTime reportStart = null;
                LocalDateTime reportEnd = null;

                if (result.getDate("startDate") != null) {
                    reportStart = LocalDateTime.parse(result.getString("startDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
                }

                if (result.getDate("endDate") != null) {
                    reportEnd = LocalDateTime.parse(result.getString("endDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
                }
                
                reports.add(new Report(result.getInt("ReportID"), result.getString("Description"), result.getString("ExtraInformation"), result.getString("locationGps"), result.getString("Weather"), new ArrayList<>(), result.getString("Title"), result.getString("locationName"), reportStart, reportEnd));
            }

            return reports;
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

    public HashMap<Integer, Integer> getAssignedReports() {
        if (!openConnection()) {
            return null;
        }
        ResultSet result = null;
        Statement statement = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery("SELECT * FROM `vwemployeereportass`");
            HashMap assigned = new HashMap<>();
            while (result.next()) {
                assigned.put(result.getInt("EmployeeID"), result.getInt("ReportID"));
            }

            return assigned;
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

    // </editor-fold>
    /**
     * saves the report with the given items.
     *
     * @param repo report to add
     * @param helplineid helplineid
     * @return succes
     */
    public int saveReport(Report repo) {

        int newID = 0;
        if (!openConnection()) {
            return newID;
        }
        try {
            CallableStatement cs = null;
            cs = conn.prepareCall("{call spInjectReport(?,?,?,?,?)}");
            cs.setString(1, repo.getDescription());
            cs.setInt(3, newID);
            cs.setString(2, repo.getLocationGPS());
            cs.setString(4, repo.getTitle());
            cs.setString(5, repo.getLocationName());
            cs.execute();
            cs.close();
            newID = getLatestId();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection();
        }
        return newID;
    }

    private int getLatestId() {

        ResultSet result = null;
        Statement statement = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery("Select Max(reportID) as maxid from report ");

            while (result.next()) {
                return result.getInt("maxid");
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
        }
        return 0;
    }

    public boolean saveHelplineReport(int reportId, int helpId) {
        boolean succes = false;
        if (!openConnection()) {
            return succes;
        }
        try {

            CallableStatement cs = null;
            cs = conn.prepareCall("{call spInjectHelplineReport(?,?)}");
            cs.setInt(1, reportId);
            cs.setInt(2, helpId);
            cs.execute();
            cs.close();
            succes = true;
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
            return succes;
        } finally {
            closeConnection();
        }
        return succes;
    }

    public String getHelplineNameById(int Id) {
        if (!openConnection()) {
            return null;
        }
        ResultSet result = null;
        Statement statement = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery("Select name from Helpline where HelplineID = " + Id);

            while (result.next()) {
                return result.getString("name");
            }
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
        return null;
    }

    public Employee getEmployees(String userName, String password) {
        if (!openConnection()) {
            return null;
        }
        ResultSet result = null;
        Statement statement = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery("Select employeeID,name,HelplineID from Employee where Inlogname = '" + userName + "' and inlogpassword = '" + password + "'");
            Employee e = null;
            while (result.next()) {
                e = new Employee(result.getInt("employeeID"), result.getString("name"), new Helpline(result.getInt("HelplineID")));
            }
            return e;
        } catch (SQLException e) {
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
    
    public String getApproachHelpline(int reportID, int helplineID)
    {
        if (!openConnection())
            return null;
        ResultSet result = null;
        Statement statement = null;
        try
        {
            statement = conn.createStatement();
            result = statement.executeQuery("SELECT Approach FROM helplinereport WHERE helplineID = " + helplineID + " AND reportID = " + reportID);
            
            while (result.next())
            {
                return result.getString("Approach");
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
            return null;
        }
        finally
        {
            try
            {
                result.close();
                statement.close();
                closeConnection();
            }
            catch (Exception ex)
            {
                System.out.println(ex);
            }
        }
        return null;
    }

}
