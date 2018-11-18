package org.firstinspires.ftc.teamcode.autonomous.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subsystems.PID;

public class TestPID extends OpMode {

    DcMotor motor;
    PID motorPID;

    private final double kp = 1;
    private final double ki = 1;
    private final double kd = 1;
    private final double intMax = 1;
    private final double intMin = 0;
    private final double outputMax = 1;
    private final double outputMin = -1;

    private final double desiredSpeed = .75;

    @Override
    public void init() {
        motor = hardwareMap.dcMotor.get("motor");
        motorPID = new PID(kp, ki, kd, intMax, intMin, outputMin, outputMax);
    }

    @Override
    public void loop() {

    }

    @Override
    public void stop() {

    }
}
