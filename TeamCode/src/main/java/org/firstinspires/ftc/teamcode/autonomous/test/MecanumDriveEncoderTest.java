package org.firstinspires.ftc.teamcode.autonomous.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.subsystems.drivetrain.EncoderDrivetrain;

@Autonomous(group = "test", name = "TEST Mecanum Encoder Test")
public class MecanumDriveEncoderTest extends OpMode {

    EncoderDrivetrain drivetrain;

    int step = 0;

    public void init() {
        drivetrain = new EncoderDrivetrain(hardwareMap, gamepad1, gamepad2, telemetry);
    }

    public void loop() {
        drivetrain.print("step: ", step);
        if (step == 0) {
            drivetrain.moveForward(20, .75);
            drivetrain.sleep(1000);
            step++;
        } else if (step == 1) {
            drivetrain.moveBackward(20, .75);
            drivetrain.sleep(1000);
            step++;
        } else if (step == 2) {
            drivetrain.turnLeft(90, .75);
            drivetrain.sleep(1000);
            step++;
        } else if (step == 3) {
            drivetrain.turnRight(90, .75);
            drivetrain.sleep(1000);
            step++;
        } else {
            drivetrain.stopMotors();
        }
    }

    public void stop() {
        drivetrain.stopDetector();
    }

}
