package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "CRServo Control", group = "Examples")
public class ContinousServoTest extends LinearOpMode {

    private CRServo crServo;

    @Override
    public void runOpMode() {
        // Map the CRServo from configuration
        crServo = hardwareMap.get(CRServo.class, "crServo");

        // Stop it initially
        crServo.setPower(0);

        telemetry.addData("Status", "Ready");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                crServo.setPower(1.0);  // Full speed forward
                telemetry.addData("Direction", "Forward (A pressed)");
            } else if (gamepad1.b) {
                crServo.setPower(-1.0); // Full speed reverse
                telemetry.addData("Direction", "Reverse (B pressed)");
            } else {
                crServo.setPower(0.0);  // Stop
                telemetry.addData("Direction", "Stopped");
            }

            telemetry.update();
        }
    }
}
