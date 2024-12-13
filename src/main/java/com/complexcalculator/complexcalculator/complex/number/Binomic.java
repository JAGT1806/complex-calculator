package com.complexcalculator.complexcalculator.complex.number;

public class Binomic {
    private Double real;
    private Double imaginary;

    public Binomic() {
    }

    public Binomic(Double real, Double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public Double getReal() {
        return real;
    }

    public Double getImaginary() {
        return imaginary;
    }

    public void setReal(Double real) {
        this.real = real;
    }

    public void setImaginary(Double imaginary) {
        this.imaginary = imaginary;
    }

    public Binomic addBinomic(Binomic other) {
        return new Binomic(
                this.getReal() + other.getReal(),
                this.getImaginary() + other.getImaginary()
        );
    }

    public Binomic resBinomic(Binomic other) {
        return new Binomic(
                this.getReal() - other.getReal(),
                this.getImaginary() - other.getImaginary()
        );
    }

    public Binomic mulBinomic(Binomic other) {
        return new Binomic(
                (this.getReal() * other.getReal()) - (this.getImaginary() * other.getImaginary()),
                (this.getReal() * other.getImaginary()) + (this.getImaginary() * other.getReal())
        );
    }

    public Binomic divBinomic(Binomic other) {
        Binomic conjugate = new Binomic(other.getReal(), -other.getImaginary());

        Binomic numerator = this.mulBinomic(conjugate);

        Double denominator = Math.pow(other.getReal(), 2) + Math.pow(other.getImaginary(), 2);

        return new Binomic(
                numerator.getReal() / denominator,
                numerator.getImaginary() / denominator
        );
    }

    public Binomic polarToBinomic(Polar polar) {
        if (polar.getModule() == null || polar.getAngle() == null) {
            throw new IllegalArgumentException("Módulo y ángulo no pueden ser nulos");
        }

        double rad = Math.toRadians(polar.getAngle());
        
        double real = polar.getModule() * Math.cos(rad);
        double imaginary = polar.getModule() * Math.sin(rad);

        return new Binomic(real, imaginary);
    }

    @Override
    public String toString() {
        if (this.getReal() == 0 && this.getImaginary() == 0) {
            return "0";
        }

        if (this.getReal() == null && this.getImaginary() == null ||
                (this.getReal() != null && this.getReal() == 0.0 && this.getImaginary() != null && this.getImaginary() == 0.0)) {
            return "0";
        }

        StringBuilder result = new StringBuilder();

        if (this.getReal() != null && this.getReal() != 0.0) {
            this.setReal(Math.round(this.getReal() * 100.0) / 100.0);
            result.append(this.getReal());
        }

        if (this.getImaginary() != null && this.getImaginary() != 0.0) {
            this.setImaginary(Math.round(this.getImaginary() * 100.0) / 100.0);
            if (result.length() > 0) {
                result.append(this.getImaginary() > 0 ? " + " : " - ");
            } else if (this.getImaginary() < 0) {
                result.append("-");
            }
            result.append(Math.abs(this.getImaginary())).append("i");
        }

        return result.toString();
    }
}
