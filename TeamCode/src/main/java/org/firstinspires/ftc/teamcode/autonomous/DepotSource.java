package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.subsystems.drivetrain.Drivetrain;

public class DepotSource {

    Drivetrain drivetrain;

    int markerState = 0;
    int goldPos = 1; //1 is left, 2 middle, 3 right
    public void init(Drivetrain dt) {
        drivetrain = dt;
        drivetrain.initParts();
        drivetrain.initDetector();
/*        drivetrain.backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        drivetrain.backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        drivetrain.frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        drivetrain.frontRight.setDirection(DcMotorSimple.Direction.REVERSE);*/
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

    /*public void sampling() {
        drivetrain.startDetector();
        drivetrain.sleep(1000);
        if(drivetrain.detector.getScreenPosition().x != 0) {
            //drivetrain.turnRight(30);
            drivetrain.strafe("left", 40);
        }
        else {
            //drivetrain.stopDetector();
            drivetrain.moveBackward(23);
            //drivetrain.startDetector();
            drivetrain.sleep(1000);
            if(drivetrain.detector.getScreenPosition().x != 0)
                goldPos = 2;

            else{
                goldPos = 3;
            }
        }
        *//*  if (xOff < 100) {
            drivetrain.turnLeft(22);
            drivetrain.sleep(200);
            drivetrain.strafe("left", 22);
            markerState = 1;
        } else if (xOff > 150 && xOff < 450) {
            drivetrain.strafe("left", 38);
            markerState = 2;
        } else {
            drivetrain.moveForward(2);
            drivetrain.sleep(200);
            drivetrain.turnRight(35);
            drivetrain.strafe("left", 28);
            markerState = 3;*//*
    }

    public void sampling2(){
        if(goldPos == 1){

        }else if(goldPos == 2){
            drivetrain.strafe("left", 15);
        }else{
            //drivetrain.stopDetector();
            drivetrain.moveBackward(23);
            //drivetrain.startDetector();
            drivetrain.sleep(1000);
            drivetrain.strafe("left", 5);
        }
    }*/


    /*public void claiming1(){
        if(markerState == 1){
            drivetrain.turnLeft(150);
            drivetrain.sleep(200);
        }
        else if (markerState == 2) {

        }
        else if (markerState == 3){
            drivetrain.turnRight(125);
            drivetrain.sleep(300);
        }
    }

    public void claiming2(){
        if(markerState == 1){
            drivetrain.strafe("right", 25);
            drivetrain.sleep(200);
            drivetrain.marker.setPosition(0);
            drivetrain.sleep(200);
        }
        else if(markerState == 2){
            drivetrain.turnRight(200);
            drivetrain.sleep(300);
            drivetrain.marker.setPosition(0);
            drivetrain.sleep(200);
        }
        else if(markerState == 3){
            drivetrain.strafe("right", 20);
            drivetrain.sleep(300);
            drivetrain.marker.setPosition(0);
            drivetrain.sleep(200);
        }
        drivetrain.strafe("left", 6);
        drivetrain.sleep(200);
        drivetrain.marker.setPosition(0);
        drivetrain.sleep(200);
    }*/

    public void sampleMiddle() {
        /*drivetrain.turnLeft(90);
        drivetrain.sleep(200);
        drivetrain.moveForward(65);
        drivetrain.sleep(200);*/
        drivetrain.strafe("left", 80);
        drivetrain.sleep(200);
    }

    public void claimMiddle() {
      /*  drivetrain.turnLeft(90);
        drivetrain.marker.setPosition(0);
        drivetrain.sleep(200);
        drivetrain.strafe("left", 5);
        drivetrain.turnRight(6);*/
      drivetrain.turnRight(240);
      drivetrain.sleep(200);
      drivetrain.marker.setPosition(0);
    }

    public void positioning1(){
        drivetrain.strafe("left", 6);
        drivetrain.sleep(200);
        drivetrain.marker.setPosition(.5);
        drivetrain.sleep(200);
        drivetrain.strafe("right",3);
        drivetrain.sleep(200);
        drivetrain.turnRight(30);
        drivetrain.sleep(200);
    }

    public void positioning2(){
        drivetrain.moveBackward(40);
        drivetrain.sleep(200);
        drivetrain.turnLeft(130);
        drivetrain.sleep(200);
        drivetrain.moveForward(10);
        drivetrain.sleep(200);
    }

    public void allianceGyroPositioning(){
        int angle = (int) drivetrain.imu.getZAngle() - 90;
        if (angle < 0)
            drivetrain.turnRight(angle);
        else drivetrain.turnLeft(angle);
    }

    public void allianceSamping(){
        double xOff = drivetrain.detector.getScreenPosition().x;
        if (xOff != 0) {
            drivetrain.strafe("left", 5);
        } else {
            drivetrain.moveForward(6);
            drivetrain.sleep(200);
        }
    }

    public void stopMotors(){
        drivetrain.stopMotors();
    }

}
