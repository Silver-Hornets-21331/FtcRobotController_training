package org.firstinspires.ftc.teamcode;

public class PIDController {
    private double kP, kI, kD;
    private double integralSum = 0;
    private double lastError = 0;
    private long lastTime = 0;

    public PIDController(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.lastTime = System.nanoTime();
    }

    public double calculate(double target, double current) {
        double error = target - current;
        long currentTime = System.nanoTime();
        double deltaTime = (currentTime - lastTime) / 1e9; // Convert to seconds

        integralSum += error * deltaTime;
        double derivative = (error - lastError) / deltaTime;

        double output = (kP * error) + (kI * integralSum) + (kD * derivative);

        lastError = error;
        lastTime = currentTime;

        return output;
    }


    public double calculate(double error) {
       // double error = target - current;
        long currentTime = System.nanoTime();
        double deltaTime = (currentTime - lastTime) / 1e9; // Convert to seconds

        integralSum += error * deltaTime;
        double derivative = (error - lastError) / deltaTime;

        double output = (kP * error) + (kI * integralSum) + (kD * derivative);

        lastError = error;
        lastTime = currentTime;

        return output;
    }

    public void reset() {
        integralSum = 0;
        lastError = 0;
        lastTime = System.nanoTime();
    }
}
