package java_2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Java_lab2 {

    public static void main(String[] args) {
        try {
            // Адрес нашей базы данных "ears" на локальном компьютере(localhost)
            String url = "jdbc:mysql://localhost:3306/ears?useSSL="
                    + "false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

            // Создание свойств соединения с базой данных
            Properties authorization = new Properties();
            authorization.put("user", "Adil"); // Зададим имя пользователя БД
            authorization.put("password", "Sword_fish03"); // Зададим пароль доступа в БД

            // Создание соединения с базой данных
            Connection connection = DriverManager.getConnection(url, authorization);

            // Создание оператора доступа к базе данных
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            // Выполнение запроса к базе данных, получение набора данных
            ResultSet table = statement.executeQuery("SELECT * FROM wireless ");

            System.out.println("Initial database:");
            table.first(); // Выведем имена полей
            for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                System.out.print(table.getMetaData().getColumnName(j) + "\t\t");
            }
            System.out.println();

            table.beforeFirst(); // Выведем записи таблицы
            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the parameters of the new table field:");
            System.out.print("name - ");
            String scannedName = sc.nextLine();
            System.out.print("price - ");
            String scannedPrice = sc.nextLine();
            
            System.out.println("After adding:");
            statement.execute("INSERT wireless(name, price) VALUES ('" + scannedName + "', '" + scannedPrice + "')");
            table = statement.executeQuery("SELECT * FROM wireless");

            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }

            System.out.println("What line ID do you want to remove?");
            System.out.print("id - ");
            int scannedId = sc.nextInt();
            statement.execute("DELETE FROM wireless WHERE Id = " + scannedId);
            
            System.out.println("After removal:");
            table = statement.executeQuery("SELECT * FROM wireless");
            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }
            sc.nextLine();
            
            System.out.println("What should I change in the first line?");
            System.out.print("name - ");
            String scannedNameUp = sc.nextLine();
            System.out.print("price - ");
            String scannedPriceUp = sc.nextLine();
            statement.executeUpdate("UPDATE wireless SET Name = '" + scannedNameUp + "' WHERE id = 1");
            statement.executeUpdate("UPDATE wireless SET price = '" + scannedPriceUp + "' WHERE id = 1");
            System.out.println("After the change:");
            table = statement.executeQuery("SELECT * FROM wireless");

            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }

            System.out.println("After filtering:");
            
            System.out.println("num ?");
            Scanner in = new Scanner(System.in);
            int num = in.nextInt();
            table = statement.executeQuery("SELECT * FROM wireless WHERE price >="+ num +" ORDER BY price");

            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }
            
            if (table != null) {
                table.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            } // Отключение от базы данных

        } catch (Exception e) {
            System.err.println("Error accessing database!");
            e.printStackTrace();
        }
    }

}
