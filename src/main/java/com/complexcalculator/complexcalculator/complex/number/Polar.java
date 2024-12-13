package com.complexcalculator.complexcalculator.complex.number;

import java.util.ArrayList;
import java.util.List;

public class Polar {
    private Double module;
    private Double angle;

    public Polar() {
    }

    public Polar(Double module, Double angle) {
        this.module = module;
        this.angle = angle;
    }

    public Double getModule() {
        return module;
    }

    public void setModule(Double module) {
        this.module = module;
    }

    public Double getAngle() {
        return angle;
    }

    public void setAngle(Double angle) {
        this.angle = angle;
    }

    public Polar mulPolar(Polar other) {
        if (this.module == null || other.module == null ||
                this.angle == null || other.angle == null ) {
            throw new IllegalArgumentException("Módulo o ángulo no pueden ser nulos");
        }

        return new Polar(
                this.getModule() * other.getModule(),
                this.getAngle() * other.getAngle()
        );
    }

    public Polar divPolar(Polar other) {
        if (this.module == null || other.module == null ||
                this.angle == null || other.angle == null ||
                other.module == 0) {
            throw new IllegalArgumentException("Módulo o ángulo no válidos");
        }

        return new Polar(
                this.getModule() / other.getModule(),
                this.getAngle() - other.getAngle()
        );
    }

    public Polar powPolar(int exponent) {
        if (this.module == null || this.angle == null) {
            throw new IllegalArgumentException("Módulo o ángulo no pueden ser nulos");
        }

        return new Polar(
                Math.pow(this.getModule(), exponent),
                this.getAngle() * exponent
        );
    }

    // Raíz de un número complejo en forma polar
    public Polar[] rootPolar(int n) {
        if (this.module == null || this.angle == null) {
            throw new IllegalArgumentException("Módulo o ángulo no pueden ser nulos");
        }

        Polar[] roots = new Polar[n];
        double module = Math.pow(this.module, 1.0 / n);
        module = Math.round(module * 100.0) / 100.0;

        for (int k = 0; k < n; k++) {
            // Calcular los ángulos de las raíces
            // double angle = (baseAngle + 2 * Math.PI * k) / n;
            double angle = (this.getAngle() + 360 * k) / n;
            roots[k] = new Polar(module, angle);
            }
        return roots;
    }

    public Polar binomicToPolar(Binomic binomic) {
        if (binomic.getReal() == null || binomic.getImaginary() == null) {
            throw new IllegalArgumentException("Los componentes del número complejo no pueden ser nulos");
        }

        double module = Math.sqrt(Math.pow(binomic.getReal(), 2) + Math.pow(binomic.getImaginary(), 2));

        double angleRadians = Math.atan2(binomic.getImaginary(), binomic.getReal());
        double angleDegrees = Math.toDegrees(angleRadians);

        return new Polar(module, angleDegrees);
    }

    @Override
    public String toString() {
        if (this.module == null || this.module == 0.0 ||
                this.angle == null ) {
            return "0";
        }

        StringBuilder result = new StringBuilder();

        this.setModule(Math.round(this.getModule() * 100.0) / 100.0);
        this.setAngle(Math.round(this.getAngle() * 100.0) / 100.0);

        result.append(this.module)
                .append("cis(")
                .append(this.angle)
                .append(")");

        return result.toString();
    }
}