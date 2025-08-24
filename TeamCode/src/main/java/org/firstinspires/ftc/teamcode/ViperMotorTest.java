package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Viper Motor Test")
public class ViperMotorTest extends LinearOpMode {

    private DcMotor motor;

    @Override
    public void runOpMode() {
        // Initialize hardware
        motor = hardwareMap.get(DcMotor.class, "ViperSlide");

        // Reset encoder and set mode
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Optional: Reverse direction if needed
        motor.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start
        waitForStart();

        // Run until stop is pressed
        while (opModeIsActive()) {
            // Example: Set power using left joystick
            double power = gamepad1.left_stick_y;
            motor.setPower(power);

            // Telemetry to show encoder position
            telemetry.addData("Motor Power", power);
            telemetry.addData("Encoder Position", motor.getCurrentPosition());
            telemetry.update();
        }

        // Stop motor when OpMode ends
        motor.setPower(0);
    }
}
