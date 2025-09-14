import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Servo Control Example", group = "Examples")
public class servo67 extends LinearOpMode {

    private Servo myServo;

    @Override
    public void runOpMode() {
        // Initialize the servo from the hardware map
myServo = hardwareMap.get(Servo.class,  "myservo");
// Set initial servo position
      //  myServo.setposition(0.5);  // Midpoint (range: 0.0 to 1.0)

        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();
    }

}
