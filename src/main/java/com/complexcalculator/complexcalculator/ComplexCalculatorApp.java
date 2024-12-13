package com.complexcalculator.complexcalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ComplexCalculatorApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(ComplexCalculatorApp.class.getResource("complex-calculator.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 600, 600);

        scene.getStylesheets().add(ComplexCalculatorApp.class.getResource("styles/styles.css").toExternalForm());

        stage.setTitle("Calculadora de Números Complejos");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}