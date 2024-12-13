package com.complexcalculator.complexcalculator;

import com.complexcalculator.complexcalculator.complex.number.Binomic;
import com.complexcalculator.complexcalculator.complex.number.Polar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ComplexCalculatorController {
    // Campos binómicos
    @FXML private TextField realField1;
    @FXML private TextField imaginaryField1;
    @FXML private TextField realField2;
    @FXML private TextField imaginaryField2;

    // Campos polares
    @FXML private TextField moduleField1;
    @FXML private TextField angleField1;
    @FXML private TextField moduleField2;
    @FXML private TextField angleField2;
    @FXML private TextField powField;

    // Campos de conversión
    @FXML private TextField realTextField;
    @FXML private TextField imaginaryTextField;
    @FXML private TextField moduleTextField;
    @FXML private TextField angleTextField;

    @FXML private Label resultLabel;

    @FXML private ToggleGroup binomicOperationGroup;
    @FXML private ToggleGroup polarOperationGroup;

    @FXML private ListView<String> historyList;

    private final ObservableList<String> history = FXCollections.observableArrayList();

    @FXML
    private void calculateBinomicResult() {
        try {
            Binomic z1 = new Binomic(
                    Double.parseDouble(realField1.getText()),
                    Double.parseDouble(imaginaryField1.getText())
            );

            Binomic z2 = new Binomic(
                    Double.parseDouble(realField2.getText()),
                    Double.parseDouble(imaginaryField2.getText())
            );

            RadioButton selectedOperation = (RadioButton) binomicOperationGroup.getSelectedToggle();
            Binomic result = switch(selectedOperation.getText()) {
                case "Sumar" -> z1.addBinomic(z2);
                case "Restar" -> z1.resBinomic(z2);
                case "Multiplicar" -> z1.mulBinomic(z2);
                case "Dividir" -> z1.divBinomic(z2);
                default -> throw new IllegalArgumentException("Operación no válida");
            };

            String operation = String.format("(%s) %s (%s) = %s",
                    z1, selectedOperation.getText(), z2, result);
            history.add(operation);
            historyList.setItems(history);

            resultLabel.setText("Resultado: " + result);
        } catch (NumberFormatException e) {
            resultLabel.setText("Error: Ingrese números válidos");
        } catch (Exception e) {
            resultLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void calculatePolarResult() {
        try {
            Polar z1 = new Polar(
                    Double.parseDouble(moduleField1.getText()),
                    Double.parseDouble(angleField1.getText())
            );

            RadioButton selectedOperation = (RadioButton) polarOperationGroup.getSelectedToggle();
            switch (selectedOperation.getText()) {
                case "Multiplicar" -> {
                    Polar z2 = new Polar(
                            Double.parseDouble(moduleField2.getText()),
                            Double.parseDouble(angleField2.getText())
                    );
                    Polar result = z1.mulPolar(z2);
                    resultLabel.setText("Resultado: " + result);
                    addToHistory(z1, selectedOperation.getText(), z2, result);
                }
                case "Dividir" -> {
                    Polar z2 = new Polar(
                            Double.parseDouble(moduleField2.getText()),
                            Double.parseDouble(angleField2.getText())
                    );
                    Polar result = z1.divPolar(z2);
                    resultLabel.setText("Resultado: " + result);
                    addToHistory(z1, selectedOperation.getText(), z2, result);
                }
                case "Potencia" -> {
                    int power = Integer.parseInt(powField.getText());
                    Polar result = z1.powPolar(power);
                    resultLabel.setText("Resultado: " + result);
                    addToHistory(z1, "Potencia (" + power + ")", null, result);
                }
                case "Raíz" -> {
                    int root = Integer.parseInt(powField.getText());
                    Polar[] results = z1.rootPolar(root);
                    resultLabel.setText("Resultado: Ver historial");
                    for (int i = 0; i < results.length; i++) {
                        history.add(String.format("Raíz %d: %s", i + 1, results[i]));
                    }
                    historyList.setItems(history);
                }
                default -> throw new IllegalArgumentException("Operación no válida");
            }
        } catch (NumberFormatException e) {
            resultLabel.setText("Error: Ingrese números válidos");
        } catch (Exception e) {
            resultLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void convertToPolar() {
        try {
            double real = Double.parseDouble(realTextField.getText());
            double imaginary = Double.parseDouble(imaginaryTextField.getText());
            Binomic binomic = new Binomic(real, imaginary);
            Polar polar = new Polar().binomicToPolar(binomic);
            resultLabel.setText("Resultado: " + polar);
            addToHistory(binomic, polar);
        } catch (NumberFormatException e) {
            resultLabel.setText("Error: Ingrese números válidos");
        }
    }

    @FXML
    private void convertToBinomial() {
        try {
            double module = Double.parseDouble(moduleTextField.getText());
            double angle = Double.parseDouble(angleTextField.getText());
            Polar polar = new Polar(module, angle);
            Binomic binomic = new Binomic().polarToBinomic(polar);
            resultLabel.setText("Resultado: " + binomic);
            addToHistory(polar, binomic);
        } catch (NumberFormatException e) {
            resultLabel.setText("Error: Ingrese números válidos");
        }
    }

    @FXML
    private void clearHistory() {
        history.clear();
        historyList.setItems(history);
    }

    // Método auxiliar para agregar operaciones al historial
    private void addToHistory(Polar z1, String operation, Polar z2, Polar result) {
        String entry = (z2 != null)
                ? String.format("(%s) %s (%s) = %s", z1, operation, z2, result)
                : String.format("(%s) %s = %s", z1, operation, result);
        history.add(entry);
        historyList.setItems(history);
    }

    private void addToHistory(Binomic z1, Polar result) {
        String entry = String.format("(%s) %s = %s", z1, "convert -> ", result);
        history.add(entry);
        historyList.setItems(history);
    }

    private void addToHistory(Polar z1, Binomic result) {
        String entry = String.format("(%s) %s = %s", z1, "convert -> ", result);
        history.add(entry);
        historyList.setItems(history);
    }
}