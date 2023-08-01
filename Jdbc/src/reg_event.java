import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Define the EventManagement interface
interface EventManagement {
    void create(Connection connection) throws SQLException;

    static void readAll(Connection connection) throws SQLException {
        String selectSql = "SELECT * FROM reg_event";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             ResultSet resultSet = selectStatement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String mailId = resultSet.getString("mail_id");
                long mobile = resultSet.getLong("mobile");
                String college = resultSet.getString("college");
                String event = resultSet.getString("event");

                System.out.println("Name: " + name);
                System.out.println("Mail ID: " + mailId);
                System.out.println("Mobile: " + mobile);
                System.out.println("College: " + college);
                System.out.println("Event: " + event);
                System.out.println("------------------------");
            }
        }
    }

    void update(Connection connection) throws SQLException;

    void delete(Connection connection) throws SQLException;
}

// The reg_event class implementing the EventManagement interface
public class reg_event implements EventManagement {
    private String name;
    private String mailId;
    private long mobile;
    private String college;
    private String event;

    // Constructor
    public reg_event(String name, String mailId, long mobile, String college, String event) {
        this.name = name;
        this.mailId = mailId;
        this.mobile = mobile;
        this.college = college;
        this.event = event;
    }

    // Getters and Setters (Encapsulation)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    // Implementation of the create method from the EventManagement interface
    @Override
    public void create(Connection connection) throws SQLException {
        String insertSql = "INSERT INTO reg_event (name, mail_id, mobile, college, event) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
            insertStatement.setString(1, name);
            insertStatement.setString(2, mailId);
            insertStatement.setLong(3, mobile);
            insertStatement.setString(4, college);
            insertStatement.setString(5, event);
            insertStatement.executeUpdate();
            System.out.println("Registration created successfully.");
        }
    }

    // We need to implement the update and delete methods from the EventManagement interface
    @Override
    public void update(Connection connection) throws SQLException {
        String updateSql = "UPDATE reg_event SET mail_id = ?, mobile = ?, college = ?, event = ? WHERE name = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
            updateStatement.setString(1, mailId);
            updateStatement.setLong(2, mobile);
            updateStatement.setString(3, college);
            updateStatement.setString(4, event);
            updateStatement.setString(5, name);
            int rowsAffected = updateStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated.");
        }
    }

    @Override
    public void delete(Connection connection) throws SQLException {
        String deleteSql = "DELETE FROM reg_event WHERE name = ?";
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
            deleteStatement.setString(1, name);
            int rowsAffected = deleteStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        }
    }
}
