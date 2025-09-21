package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "Mechnume Drive Test")
public class DriveWithPID extends LinearOpMode {

    // Declare distance sensor at class level so it's accessible in the function
    Rev2mDistanceSensor distanceSensor;
    @Override
    public void runOpMode() throws InterruptedException {
// Create PID controller instance (tune these values!)
        PIDController pid = new PIDController(0.05, 0.0, 0.01); // P, I, D
        double targetDistance = 30.0; // Target distance from wall (in cm)

        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("FrontLeft");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("BackLeft");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("FrontRight");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("BackRight");

        distanceSensor = hardwareMap.get(Rev2mDistanceSensor.class, "distanceSensor");

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("STATUS","Initilization...");
        telemetry.update();

        waitForStart();


        while (opModeIsActive()) {
              // Movement controls

            double x = gamepad1.left_stick_x * 1.1;
            double rx = gamepad1.right_stick_x;
           // double y = -gamepad1.left_stick_y;

// Optional: PID-based control for distance maintenance
            double currentDistance = getDistanceInCM();
            double correction = pid.calculate(targetDistance, currentDistance);

// Clamp correction to avoid too fast reverse/forward motion
            correction = Math.max(-0.5, Math.min(0.5, correction));

// Example: Use correction to move forward/back to maintain distance
            double y = correction;

// Standard Mecanum drive calculations
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);

// Telemetry
            telemetry.addData("Distance (cm)", "%.2f", currentDistance);
            telemetry.addData("PID Correction", "%.2f", correction);
            telemetry.update();
        }
    }

    // Function to get distance in centimeters
    public double getDistanceInCM() {
        return distanceSensor.getDistance(DistanceUnit.CM);
    }

}