/*
 * Derrick Fox
 * CS 214 - Advanced Java
 * Project 10 - Address Book
 * April 27, 2015
 * Java 1.8 JavaFX 2.2
 */

package application;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class Main extends Application { 
  
  public static Stage theStage;

  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {  
	theStage = primaryStage;
    // Create a scene and place it in the stage
    Scene scene = new Scene(new DBConnectionPane(), 420, 180);
    primaryStage.setTitle("DB Connection"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage   
    
  }
  
  

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}

