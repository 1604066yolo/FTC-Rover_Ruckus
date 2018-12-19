package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.subsystems.drivetrain.Drivetrain;

public class CraterSource {

    Drivetrain drivetrain;

    int markerState = 0;

    public void init(Drivetrain dt) {
        drivetrain = dt;
        drivetrain.initParts();
        drivetrain.initDetector();
        drivetrain.backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        drivetrain.backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        drivetrain.frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        drivetrain.frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        drivetrain.startDetector();
    }

    public void landing(){
        drivetrain.climbDown();
        drivetrain.marker.setPosition(.5);
        drivetrain.sleep(200);
    }

    public void gyroPositioning(){
        int ang = (int) drivetrain.imu.getZAngle();
        if (ang < 0)
            drivetrain.turnRight(-ang);
        else
            drivetrain.turnLeft(ang);
        drivetrain.sleep(1000);
    }

    public void sampling() {
        double xOff = drivetrain.detector.getScreenPosition().x;
        if (xOff < 100) {
            drivetrain.turnLeft(30);
            drivetrain.sleep(200);
            drivetrain.strafe("left", 25);
            markerState = 1;
        } else if (xOff > 350) {
            drivetrain.turnRight(41);
            drivetrain.strafe("left", 22);
            markerState = 3;
        } else {
            drivetrain.moveBackward(1);
            drivetrain.strafe("left", 21);
            markerState = 2;
        }
    }

    public void claiming1(){
        if (markerState == 1) {
            drivetrain.moveBackward(5);
            drivetrain.sleep(200);
            drivetrain.turnRight(90);
            drivetrain.sleep(200);
            drivetrain.moveBackward(8);
            drivetrain.sleep(200);
        } else if (markerState == 2) {
            drivetrain.strafe("right", 12);
            drivetrain.sleep(200);
            drivetrain.moveBackward(35);
            drivetrain.sleep(200);
        } else if (markerState == 3) {
            drivetrain.turnLeft(15);
            drivetrain.sleep(200);
            drivetrain.strafe("right",11);
            drivetrain.sleep(200);
            drivetrain.moveBackward(65);
            drivetrain.sleep(200);
        }
    }

    public void claiming2(){
        if(markerState == 1){
            drivetrain.strafe("right", 40);
            drivetrain.sleep(200);
            drivetrain.marker.setPosition(.9);
        } else if(markerState == 2){
            drivetrain.turnRight(36);
            drivetrain.sleep(200);
            drivetrain.moveBackward(11);
            drivetrain.sleep(200);
            drivetrain.strafe("right", 35);
            drivetrain.sleep(200);
            drivetrain.marker.setPosition(.9);
        } else if(markerState == 3){
            drivetrain.turnRight(20);
            drivetrain.sleep(200);
            drivetrain.moveBackward(5);
            drivetrain.sleep(200  );
            drivetrain.strafe("right",45);
            drivetrain.sleep(200);
            drivetrain.marker.setPosition(.9);
        }
        drivetrain.sleep(300);
        drivetrain.strafe("left", 4);
    }

    public void parking(){
        drivetrain.strafe("left", 55);
        drivetrain.sleep(200);
    }

    public void stopMotors(){
        drivetrain.stopMotors();
    }

}