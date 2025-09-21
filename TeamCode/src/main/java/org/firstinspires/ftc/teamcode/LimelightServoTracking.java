package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

@TeleOp(name = "Sensor: Limelight3A with Servo Tracking", group = "Sensor")
public class LimelightServoTracking extends LinearOpMode {

    private Limelight3A limelight;
    private Servo trackingServo;

    private static final double SERVO_CENTER = 0.5; // Center position
    private static final double SERVO_MIN = 0.0;
    private static final double SERVO_MAX = 1.0;
    //private static final double TRACK_KP = 0.0005; // Proportional gain (tune this)
    PIDController pid = new PIDController(0.05, 0.0, 0.01); // P, I, D

    private double servoPosition = SERVO_CENTER;

    @Override
    public void runOpMode() throws InterruptedException {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        trackingServo = hardwareMap.get(Servo.class, "trackingServo");
       // trackingServo.setPosition(servoPosition);

        telemetry.setMsTransmissionInterval(11);
        limelight.pipelineSwitch(0);
        limelight.start();

        telemetry.addData(">", "Robot Ready. Press Play.");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            LLStatus status = limelight.getStatus();
            telemetry.addData("Name", status.getName());
            telemetry.addData("LL", "Temp: %.1fC, CPU: %.1f%%, FPS: %d",
                    status.getTemp(), status.getCpu(), (int) status.getFps());
            telemetry.addData("Pipeline", "Index: %d, Type: %s",
                    status.getPipelineIndex(), status.getPipelineType());

            LLResult result = limelight.getLatestResult();

            if (result.isValid()) {
                Pose3D botpose = result.getBotpose();
                double captureLatency = result.getCaptureLatency();
                double targetingLatency = result.getTargetingLatency();
                double parseLatency = result.getParseLatency();

                telemetry.addData("LL Latency", captureLatency + targetingLatency);
                telemetry.addData("Parse Latency", parseLatency);
                telemetry.addData("tx", result.getTx());
                telemetry.addData("ty", result.getTy());

                telemetry.addData("Botpose", botpose.toString());

                // --- Servo Tracking Logic ---
                double tx = result.getTx(); // Horizontal offset (-29.8 to 29.8 degrees typically)

                // Show detected AprilTag data
                List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
                for (LLResultTypes.FiducialResult fr : fiducialResults) {
                    telemetry.addData("April Tag", "ID: %d, Family: %s, X: %.2f, Y: %.2f",
                            fr.getFiducialId(), fr.getFamily(), fr.getTargetXDegrees(), fr.getTargetYDegrees());

                    if (fr.getFiducialId() == 24 ) {
                        // Apply proportional control
                        double correction = pid.calculate(tx);
                        //double correction = TRACK_KP * tx;
                        servoPosition -= correction; // Reverse if needed (depends on orientation)
                        telemetry.addData("correction Position", "%.2f", correction);
                        // Clip to servo bounds
                        telemetry.addData("before Position", "%.2f", servoPosition);
                        servoPosition = Math.max(SERVO_MIN, Math.min(SERVO_MAX, servoPosition));

                        trackingServo.setPosition(servoPosition);
                        telemetry.addData("Servo Position", "%.2f", servoPosition);
                    }
                   else if (fr.getFiducialId() == 20 ) {
                        // Apply proportional control0
                       // double correction = TRACK_KP * tx;
                        double correction = pid.calculate(tx);
                        servoPosition -= correction; // Reverse if needed (depends on orientation)
                        telemetry.addData("correction Position", "%.2f", correction);
                        // Clip to servo bounds
                        telemetry.addData("before Position", "%.2f", servoPosition);
                        servoPosition = Math.max(SERVO_MIN, Math.min(SERVO_MAX, servoPosition));

                        trackingServo.setPosition(servoPosition);
                        telemetry.addData("Servo Position", "%.2f", servoPosition);
                    }
                    else if((fr.getFiducialId() == 21) ) {

                        telemetry.addData("Sequesnce is ", " Green-Purpal-Purpal");

                    }
                    else if((fr.getFiducialId() == 22) ) {

                        telemetry.addData("Sequesnce is ", " Purpal-Green-Purpal");

                    }
                    else if((fr.getFiducialId() == 23) ) {

                        telemetry.addData("Sequesnce is ", " Purpal-Purpal-Green");

                    }
                }

            } else {
                telemetry.addData("Limelight", "No data available");
            }

            telemetry.update();
        }

        limelight.stop();
    }
}
