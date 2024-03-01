import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        try {

            String url = "jdbc:postgresql://localhost:5432/person";

            Properties authorization = new Properties();
            authorization.put("user", "postgres");
            authorization.put("password", "D30is10id3");

            Connection connection = DriverManager.getConnection(url, authorization);

            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet table = statement.executeQuery("SELECT * FROM clients");

            table.first();
            for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                System.out.print(table.getMetaData().getColumnName(j) + "\t\t");
            }
            System.out.println();

            table.beforeFirst();
            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }

                System.out.println();
            }

            createClients(connection, "Dariga", "Seisekeyeva", "German Level1", 86633);
            readClients(connection);
            updateClients(connection, 1, "Islam", "Skakov", "English Level1", 9452);
            deleteClients(connection, 2);

            if (table != null) { table.close(); }
            if (statement != null) { statement.close(); }
            if (connection != null) { connection.close(); }

        } catch (Exception e) {
            System.err.println("Error accessing database!");
            e.printStackTrace();
        }
    }

    private static void createClients(Connection connection, String name, String surname, String book, Integer number) throws SQLException {
        String sql = "INSERT INTO clients (name, surname, book, number) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, book);
            statement.setInt(4, number);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Created successfully");
            }
        }
    }

    private static void readClients(Connection connection) {
        try {
            String sql = "SELECT id, name, surname, book, number FROM clients";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    System.out.println("ID: " + resultSet.getInt("id") +
                            ", Name: " + resultSet.getString("name") +
                            ", Surname: " + resultSet.getString("surname") +
                            ", Book: " + resultSet.getString("book") +
                            ", Number: " + resultSet.getInt("number"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Cannot read clients");
            e.printStackTrace();
        }
    }

    private static void updateClients(Connection connection, int id, String name, String surname, String book, int number) {
        try {
            String sql = "UPDATE clients SET name=?, surname=?, book=?, number=? WHERE id=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, surname);
                statement.setString(3, book);
                statement.setInt(4, number);
                statement.setInt(5, id);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println(id);
                }
            }
        } catch (SQLException e) {
            System.err.println("Cannot update client");
            e.printStackTrace();
        }
    }

    private static void deleteClients(Connection connection, int id) {
        try {
            String sql = "DELETE FROM clients WHERE id=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println(id);
                }
            }
        } catch (SQLException e) {
            System.err.println("Cannot delete client");
            e.printStackTrace();
        }
    }
}