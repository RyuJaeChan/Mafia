package com.joycity.intern.mafia.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


//https://www.microsoft.com/en-us/sql-server/developer-get-started/java/windows/step/2.html

public class App {
/*
	public static void main(String[] args) {

		String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=mafia;user=sa;password=rkskek123!@#";

		try {
			// Load SQL Server JDBC driver and establish connection.
			System.out.print("Connecting to SQL Server ... ");
			try (Connection connection = DriverManager.getConnection(connectionUrl)) {
				System.out.println("Done.");
				
				// READ demo
				System.out.print("Reading data from table, press ENTER to continue...");
				System.in.read();
				String sql = "SELECT * FROM test;";
				try (Statement statement = connection.createStatement();
						ResultSet resultSet = statement.executeQuery(sql)) {
					while (resultSet.next()) {
						System.out.println(
								resultSet.getInt(1) + " ");
					}
				}
				connection.close();
				System.out.println("All done.");
			}
		} catch (Exception e) {
			System.out.println();
			e.printStackTrace();
		}
	}
	*/
}