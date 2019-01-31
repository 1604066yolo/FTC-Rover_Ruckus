package org.firstinspires.ftc.teamcode.autonomous.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.util.Util;

@Autonomous(group = "test", name = "TEST TankDrive with Encoders")
public class EncoderTest extends LinearOpMode {

    private DcMotorEx m;
    int step = 0;

    @Override
    public void runOpMode() {
        m = (DcMotorEx) hardwareMap.dcMotor.get("frontLeft");
        m.setTargetPositionTolerance(30);
        m.setDirection(DcMotorEx.Direction.REVERSE);
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("vel: ", m.getVelocity());
            telemetry.addData("enc: ", m.getCurrentPosition());
            telemetry.addData("target: ", m.getTargetPosition());
            if (step == 0) {
                m.setTargetPosition(1000);
                m.setVelocity(1000);
                m.setMotorEnable();
                step++;
            } else if (step == 1 && Util.inRange(m.getCurrentPosition(), m.getTargetPosition() - 100, m.getTargetPosition() + 100)) {
                m.setMotorDisable();
                step++;
            }
            else if (step == 2) {
                m.setTargetPosition(-1000);
                m.setVelocity(-1000);
                m.setMotorEnable();
                step++;
            } else if (step == 3 && Util.inRange(m.getCurrentPosition(), m.getTargetPosition() - 100, m.getTargetPosition() + 100)) {
                m.setMotorDisable();
                step++;
            }
            telemetry.update();
        }
    }
}
