package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.subsystems.drivetrain.EncoderDrivetrain;

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
        if (step == 0) {
            drivetrain.climbDown();
            drivetrain.marker.setPosition(.5);
            drivetrain.sleep(200);
            step++;
        } else if (step == 1) {
            drivetrain.moveForward(105, .3);
            drivetrain.strafeRight(3, .3);
            drivetrain.moveBackward(105, .3);
            step++;
        } else if(step == 2){
            int ang = (int) drivetrain.imu.getZAngle();
            if (ang < 0)
                drivetrain.turnRight(-ang, .3);
            else
                drivetrain.turnLeft(ang, .3);
            drivetrain.sleep(1000);
            step++;
        } else if (step == 3) {
            double xOff = drivetrain.detector.getScreenPosition().x;
            if (xOff < 100) {
                drivetrain.turnLeft(40, .3);
                drivetrain.sleep(200);
                drivetrain.strafeLeft(122, .3);
                markerState = 1;
            } else if (xOff > 350) {
                drivetrain.moveForward(2, .3);
                drivetrain.sleep(200);
                drivetrain.turnRight(35, .3);
                drivetrain.strafeLeft(128, .3);
                markerState = 3;
            } else {
                drivetrain.strafeLeft(138, .3);
                markerState = 2;
            }
            step++;
        } else if (step == 4){
            if(markerState == 1){
                drivetrain.turnLeft(150, .3);
                drivetrain.sleep(200);
            }
            else if (markerState == 2) {

            }
            else if (markerState == 3){
                drivetrain.turnRight(125, .3);
                drivetrain.sleep(300);
            }
            step++;
        } else if(step == 5){
            if(markerState == 1){
                drivetrain.strafeRight(125, .3);
                drivetrain.sleep(200);
                drivetrain.marker.setPosition(.9);
                drivetrain.sleep(200);
            }
            else if(markerState == 2){
                drivetrain.turnRight(200, .3);
                drivetrain.sleep(300);
                drivetrain.marker.setPosition(.9);
                drivetrain.sleep(200);
            }
            else if(markerState == 3){
                drivetrain.strafeRight(120, .3);
                drivetrain.sleep(300);
                drivetrain.marker.setPosition(.9);
                drivetrain.sleep(200);
            }
            drivetrain.strafeLeft(106, .3);
            drivetrain.sleep(200);
            drivetrain.marker.setPosition(.9);
            drivetrain.sleep(200);
            step++;
        } else {
            drivetrain.stopMotors();
        }

        telemetry.addData("Loop count: ", ++loopCount);
    }

}
