package org.firstinspires.ftc.teamcode.teleop.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(group = "test", name = "TEST Linear Slides")
public class LinearSlideTest extends OpMode {

    DcMotor lFlip, rFlip, extend;

    public void init() {
        lFlip = hardwareMap.dcMotor.get("lFlip");
        rFlip = hardwareMap.dcMotor.get("rFlip");
        extend = hardwareMap.dcMotor.get("extend");

        lFlip.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rFlip.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void loop() {
        lFlip.setPower(gamepad1.right_trigger - gamepad1.left_trigger);
        rFlip.setPower(gamepad1.right_trigger - gamepad1.left_trigger);
        extend.setPower(gamepad1.right_bumper ? 1 : gamepad1.left_bumper ? -1 : 0);
    }

}
