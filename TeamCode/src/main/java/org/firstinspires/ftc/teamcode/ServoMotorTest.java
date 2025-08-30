package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Servo Control Example", group = "Examples")
public class ServoMotorTest extends LinearOpMode {

    private Servo myServo;

    @Override
    public void runOpMode() {
        // Initialize the servo from the hardware map
        myServo = hardwareMap.get(Servo.class, "servo0");

        // Set initial servo position
        myServo.setPosition(0.5);  // Midpoint (range: 0.0 to 1.0)

        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Move servo to different positions based on gamepad input
            if (gamepad1.a) {
                myServo.setPosition(0.0);  // Move to 0%
                telemetry.addData("Servo Position", "0.0 (A Pressed)");
            } else if (gamepad1.b) {
                myServo.setPosition(1.0);  // Move to 100%
                telemetry.addData("Servo Position", "1.0 (B Pressed)");
            } else if (gamepad1.x) {
                myServo.setPosition(0.75443);  // Move to 50%
                telemetry.addData("Servo Position", "0.5 (X Pressed)");
            }

            telemetry.update();
        }
    }
}
