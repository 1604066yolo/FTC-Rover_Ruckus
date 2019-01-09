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
        telemetry.addData("frontRight", drivetrain.frontRight.getCurrentPosition());
        telemetry.addData("frontLeft", drivetrain.frontLeft.getCurrentPosition());
        telemetry.addData("backRight", drivetrain.backRight.getCurrentPosition());
        telemetry.addData("backLeft", drivetrain.backLeft.getCurrentPosition());
        telemetry.update();
        /*if (step == 0) {
            drivetrain.strafe("left", 20, .3);
            drivetrain.sleep(1000);
            step++;
        } else if (step == 1) {
            drivetrain.strafe("right", 20, .3);
            drivetrain.sleep(1000);
            step++;
        }*/
        if (step == 0) {
            drivetrain.moveForward(20, .5);
            drivetrain.sleep(1000);
            step++;
        } else if (step == 1) {
            drivetrain.moveBackward(20, .5);
            drivetrain.sleep(1000);
            step++;
        } else if (step == 2) {
            drivetrain.turnLeft(90, .5);
            drivetrain.sleep(1000);
            step++;
        } else if (step == 3) {
            drivetrain.turnRight(90, .5);
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
