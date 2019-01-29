package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.subsystems.drivetrain.EncoderDrivetrain;


@Autonomous
public class EncoderDepotAuton extends OpMode {
    EncoderDrivetrain drivetrain;
    int step = 0;
    int markerState = 0;
    boolean isFound = false;

    public void init(){
        drivetrain = new EncoderDrivetrain(hardwareMap, gamepad1, gamepad2, telemetry);
    }

    public void loop(){
        if(step == 0) {//landing
            drivetrain.rp.setPower(-1);
            drivetrain.sleep(1350);
            drivetrain.rp.setPower(0);
            drivetrain.marker.setPosition(.5);
            drivetrain.sleep(200);
            step++;
        }
        else if (step == 1){//adjusting
            drivetrain.moveBackward(5, .3);
            drivetrain.sleep(1000); //20
            drivetrain.strafe("left",20);
            drivetrain.sleep(2000); //200
            drivetrain.moveForward(5, .3);
            drivetrain.sleep(2000); //200
            step++;
        }
        else if(step == 2){ //gyro
            int ang = (int) drivetrain.imu.getZAngle();
            if (ang < 0)
               drivetrain.turnRight(-ang, .3);
            else
                drivetrain.turnLeft(ang, .3);
            drivetrain.sleep(1000);
            step++;
        }
        else if (step == 3){ //strafe towards minerals
            drivetrain.strafeLeft(15, .6);
            drivetrain.sleep(200);
            step++;
        } else if (step == 4) { //detect center mineral
            drivetrain.startDetector();
            if (drivetrain.detector.isFound() && !isFound) {
                drivetrain.strafeLeft(5, .6);
                drivetrain.sleep(200);
                isFound = true;
            }
            step++;
        } else if (step == 5) { //go to right mineral
            if (!isFound)
                drivetrain.moveForward(15, .3);
            drivetrain.sleep(200);
            step++;
        } else if (step == 6) { //detect right mineral
            drivetrain.startDetector();
            if (drivetrain.detector.isFound() && !isFound) {
                drivetrain.strafeLeft(5, .6);
                drivetrain.sleep(200);
                isFound = true;
            }
            step++;
        } else if (step == 7) { //move to left mineral
            if (!isFound)
                drivetrain.moveBackward(30, .3);
            drivetrain.sleep(200);
            step++;
        } else if (step == 8) { //detects left mineral
            drivetrain.startDetector();
            if (drivetrain.detector.isFound() && !isFound) {
                drivetrain.strafeLeft(5, .6);
                drivetrain.sleep(200);
                isFound = true;
            }
            step++;
        } else if (step == 9) {
            drivetrain.stopDetector();
            drivetrain.stopMotors();
        }
    }
}
