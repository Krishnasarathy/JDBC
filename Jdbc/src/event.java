import java.sql.*;
import java.util.Scanner;

public class event {
    private String name;
    private Date date;
    private String venue;

    public event(String name, Date date, String venue) {
        this.name = name;
        this.date = date;
        this.venue = venue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

   
    @Override
    public String toString() {
        return "Event Name: " + name + "\nEvent Date: " + date + "\nEvent Venue: " + venue + "\n------------------------";
    }


    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/event";
        String user = "root";
        String password = "Krishna@#ks77";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("Choose an operation:");
                System.out.println("1. Create");
                System.out.println("2. Read");
                System.out.println("3. Update");
                System.out.println("4. Delete");
                System.out.println("5. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character left by nextInt()

                switch (choice) {
                    case 1:
                        createEvent(connection, scanner);
                        break;
                    case 2:
                        readEvent(connection);
                        break;
                    case 3:
                        updateEvent(connection, scanner);
                        break;
                    case 4:
                        deleteEvent(connection, scanner);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createEvent(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter event name:");
        String eventName = scanner.nextLine();

        System.out.println("Enter event date (YYYY-MM-DD):");
        String eventDateStr = scanner.nextLine();
        Date eventDate = Date.valueOf(eventDateStr);

        System.out.println("Enter event venue:");
        String eventVenue = scanner.nextLine();

        event event = new event(eventName, eventDate, eventVenue);
        insertEvent(connection, event);
        System.out.println("Event created successfully.");
    }

    private static void insertEvent(Connection connection, event event) throws SQLException {
        String insertSql = "INSERT INTO event (event_name, event_date, event_venue) VALUES (?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
            insertStatement.setString(1, event.getName());
            insertStatement.setDate(2, event.getDate());
            insertStatement.setString(3, event.getVenue());
            insertStatement.executeUpdate();
        }
    }

    private static void readEvent(Connection connection) throws SQLException {
        String selectSql = "SELECT * FROM event";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             ResultSet resultSet = selectStatement.executeQuery()) {

            while (resultSet.next()) {
                String eventName = resultSet.getString("event_name");
                Date eventDate = resultSet.getDate("event_date");
                String eventVenue = resultSet.getString("event_venue");

                event event = new event(eventName, eventDate, eventVenue);
                System.out.println(event);
            }
        }
    }

    private static void updateEvent(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter the event name to update:");
        String eventNameToUpdate = scanner.nextLine();

        System.out.println("Enter new event venue:");
        String newEventVenue = scanner.nextLine();

        event updatedEvent = new event(eventNameToUpdate, null, newEventVenue);
        updateEvent(connection, updatedEvent);
        System.out.println("Event updated successfully.");
    }

    private static void updateEvent(Connection connection, event event) throws SQLException {
        String updateSql = "UPDATE event SET event_venue = ? WHERE event_name = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
            updateStatement.setString(1, event.getVenue());
            updateStatement.setString(2, event.getName());
            updateStatement.executeUpdate();
        }
    }

    private static void deleteEvent(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter the event name to delete:");
        String eventNameToDelete = scanner.nextLine();

        event eventToDelete = new event(eventNameToDelete, null, null);
        deleteEvent(connection, eventToDelete);
        System.out.println("Event deleted successfully.");
    }

    private static void deleteEvent(Connection connection, event event) throws SQLException {
        String deleteSql = "DELETE FROM event WHERE event_name = ?";
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
            deleteStatement.setString(1, event.getName());
            deleteStatement.executeUpdate();
        }
    }
}
