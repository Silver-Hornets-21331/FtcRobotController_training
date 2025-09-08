package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name="distence sensor test",group = "example")
public class distence extends LinearOpMode {
    private Rev2mDistanceSensor distencesensor;
    @Override
    public void runOpMode(){
        distencesensor=hardwareMap.get(Rev2mDistanceSensor.class,"distencesensor1");

        telemetry.addData("status","initilization");
        telemetry.update();
        waitForStart();
        while(opModeIsActive() ){
            double distence=distencesensor.getDistance(DistanceUnit.INCH);
            telemetry.addData("Distance=",distence);
            telemetry.update();
        }
    }

}