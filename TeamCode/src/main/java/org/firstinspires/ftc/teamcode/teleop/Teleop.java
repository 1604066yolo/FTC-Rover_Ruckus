package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.drivetrain.Drivetrain;


@TeleOp
public class Teleop extends OpMode {

    double drive;
    double strafe;
    double rotate;

    Drivetrain drivetrain;

    public void init() {
        drivetrain = new Drivetrain(hardwareMap, gamepad1, gamepad2, telemetry);
        drivetrain.initParts();
    }

    int div = 1;

    boolean rpFlag = false;

    public void loop() {

      /* double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
       double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
       double rightX = gamepad1.right_stick_x;
       final double v1 = r * Math.cos(robotAngle) + rightX;
       final double v2 = r * Math.sin(robotAngle) - rightX;
       final double v3 = r * Math.sin(robotAngle) + rightX;
       final double v4 = r * Math.cos(robotAngle) - rightX;
       frontLeft.setPower(v2/div);
       frontRight.setPower(v1/div);
       backLeft.setPower(v4/div);
       backRight.setPower(v3/div);     ok this is vvvvvvv epic */

        drive = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;
        telemetry.addData("drive number", -gamepad1.left_stick_y);
        telemetry.addData("strafe number", gamepad1.left_stick_x);
        telemetry.addData("rotate number", gamepad1.right_stick_x);


        drivetrain.frontLeft.setPower((drive + strafe + rotate) / div);
        drivetrain.backLeft.setPower((drive - strafe+ rotate) / div);
        drivetrain.frontRight.setPower((drive - strafe - rotate) / div);
        drivetrain.backRight.setPower((drive + strafe - rotate) / div);




        if(gamepad1.b){
            if(div == 1){
                div = 3;
                drivetrain.sleep(200);
                //drivetrain.rp.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
            else{
                div = 1;
                drivetrain.sleep(200);
                //drivetrain.rp.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            }
        }


        /*if (gamepad2.x)
            drivetrain.contServo.setPosition(1);
        else if (gamepad2.y)
            drivetrain.contServo.setPosition(-1);
        else
            drivetrain.contServo.setPosition(0.53);

        if(gamepad2.a)
            drivetrain.winch.setPower(0.5);
        else if(gamepad2.b)
            drivetrain.winch.setPower(-0.5);
        else
            drivetrain.winch.setPower(0);*/

        //
        // drivetrain.spindle.setPower(gamepad2.left_trigger - gamepad2.right_trigger);

        if (gamepad2.dpad_up) {
            drivetrain.rp.setPower(-1);
            rpFlag = true;
        }
        else if (gamepad2.dpad_down) {
            drivetrain.rp.setPower(1);
            rpFlag = true;
        }
        else if (rpFlag) {
            drivetrain.rp.setPower(0);
            rpFlag = false;
        }

        if(gamepad2.right_bumper){
            drivetrain.extender.setPower(1);
        }
        else if(gamepad2.left_bumper){
            drivetrain.extender.setPower(-1);
        } else {
            drivetrain.extender.setPower(0);
        }

        if(gamepad2.x) {
            drivetrain.flipper.setPower(1);
        }
        else if(gamepad2.b){
            drivetrain.flipper.setPower(-1);
        } else {
            drivetrain.flipper.setPower(0);
        }


    }

}