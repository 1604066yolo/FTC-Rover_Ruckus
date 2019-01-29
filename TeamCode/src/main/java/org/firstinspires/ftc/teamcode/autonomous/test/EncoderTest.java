package org.firstinspires.ftc.teamcode.autonomous.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(group = "test", name = "TEST TankDrive with Encoders")
public class EncoderTest extends LinearOpMode {

    private DcMotorEx m;
    int step = 0;

    @Override
    public void runOpMode() {
        m = (DcMotorEx) hardwareMap.dcMotor.get("frontLeft");
        m.setTargetPositionTolerance(20);
        m.setDirection(DcMotorEx.Direction.REVERSE);
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("vel: ", m.getVelocity());
            telemetry.addData("enc: ", m.getCurrentPosition());
            telemetry.addData("target: ", m.getTargetPosition());
            if (step == 0) {
                m.setTargetPosition(1000);
                m.setVelocity(300);
                m.setMotorEnable();
                step++;
            }
            if (m.getCurrentPosition() > m.getTargetPosition() - m.getTargetPositionTolerance())
                m.setMotorDisable();
            telemetry.update();
        }
    }
}
