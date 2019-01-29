package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.subsystems.drivetrain.EncoderDrivetrain;
@Disabled
@Autonomous(group = "enc", name="Depot Auton")
public class NirvanaDepotAuton extends OpMode{

    EncoderDrivetrain drivetrain;
    int step = 0;
    int markerState = 0;

    int loopCount = 0;

    public void init() {
        drivetrain = new EncoderDrivetrain(hardwareMap, gamepad1, gamepad2, telemetry);
    }

    public void loop(){

//        else if (step == 1) {
//            drivetrain.moveBackward(5, .3);
//            step++;
//        }
//        else if(step == 2) {
//            drivetrain.strafeLeft(3, .3);
//            step++;
//        }
//        else if(step == 3){
//            drivetrain.moveForward(5, .3);
//            step++;
//        }
//        else if(step == 4){
//            int ang = (int) drivetrain.imu.getZAngle();
//            if (ang < 0)
//                drivetrain.turnRight(-ang, .3);
//            else
//                drivetrain.turnLeft(ang, .3);
//            drivetrain.sleep(1000);
//            step++;
//        }
//        else if (step == 5) {
//            double xOff = drivetrain.detector.getScreenPosition().x;
//            telemetry.addData("xoff", xOff);
//            if (xOff < 100) {
//                drivetrain.turnLeft(40, .3);
//                drivetrain.sleep(200);
//                drivetrain.strafeLeft(22, .6);
//                markerState = 1;
//            } else if (xOff > 350) {
//                drivetrain.moveForward(2, .6);
//                drivetrain.sleep(200);
//                drivetrain.turnRight(35, .3);
//                drivetrain.strafeLeft(28, .6);
//                markerState = 3;
//            } else {
//                drivetrain.strafeLeft(38, .6);
//                markerState = 2;
//            }
//            step++;
//        } else if (step == 6){
//            if(markerState == 1){
//                drivetrain.turnLeft(150, .3);
//                drivetrain.sleep(200);
//            }
//            else if (markerState == 2) {
//
//            }
//            else if (markerState == 3){
//                drivetrain.turnRight(125, .3);
//                drivetrain.sleep(300);
//            }
//            step++;
//        } else if(step == 7){
//            if(markerState == 1){
//                drivetrain.strafeRight(125, .6);
//                drivetrain.sleep(200);
//                drivetrain.marker.setPosition(.9);
//                drivetrain.sleep(200);
//            }
//            else if(markerState == 2){
//                drivetrain.turnRight(200, .3);
//                drivetrain.sleep(300);
//                drivetrain.marker.setPosition(.9);
//                drivetrain.sleep(200);
//            }
//            else if(markerState == 3){
//                drivetrain.strafeRight(20, .6);
//                drivetrain.sleep(300);
//                drivetrain.marker.setPosition(.9);
//                drivetrain.sleep(200);
//            }
//            drivetrain.strafeLeft(6, .6);
//            drivetrain.sleep(200);
//            drivetrain.marker.setPosition(.9);
//            drivetrain.sleep(200);
//            step++;
//        } */
        if (step == 0) {
            drivetrain.climbDown();
            drivetrain.marker.setPosition(.5);
            drivetrain.sleep(200);
            step++;
        } else if(step == 1){
            drivetrain.turnLeft(105, .4);
            drivetrain.sleep(200);
            drivetrain.moveForward(15, .4);
            drivetrain.sleep(200);
            drivetrain.turnRight(105, .4);
            step++;
        } else if (step == 2){
            if(drivetrain.detector.getScreenPosition().x > 100 && drivetrain.detector.getScreenPosition().x < 350){
                drivetrain.turnLeft(105, .4);
                drivetrain.sleep(200);
                drivetrain.moveForward(15, .4);
                step = 100;
            }
            step++;
        }
        else if (step == 3){
            drivetrain.moveBackward(11, .4);
            step++;
        }
        else if(step == 4){
            if(drivetrain.detector.getScreenPosition().x > 100 && drivetrain.detector.getScreenPosition().x < 350){
                drivetrain.turnLeft(105, .4);
                drivetrain.sleep(200);
                drivetrain.moveForward(15, .4);
                step = 100;
            }
            step++;
        }
        else if (step == 5){
            drivetrain.moveForward(22, .4);
            step++;
        }
        else if (step == 6){
            if(drivetrain.detector.getScreenPosition().x > 100 && drivetrain.detector.getScreenPosition().x < 350){
                drivetrain.turnLeft(105, .4);
                drivetrain.sleep(200);
                drivetrain.moveForward(15, .4);
                step = 100;
            }
            step++;
        }
        else {
            drivetrain.stopMotors();
        }

        telemetry.addData("Loop count: ", ++loopCount);
    }

}
