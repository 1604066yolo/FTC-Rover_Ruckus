package org.firstinspires.ftc.teamcode.autonomous.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.PID;

public class TestPID extends OpMode {

    private DcMotor motor;
    private PID motorPID;

    private final double kp = 1;
    private final double ki = 1;
    private final double kd = 1;
    private final double intMax = 1;
    private final double intMin = 0;
    private final double outputMax = 1;
    private final double outputMin = -1;

    private final double desiredSpeed = .75;

    private final double drivePidIntMax = 1;  // Limit to max speed.
    private final double driveOutMax = 1.0;  // Motor output limited to 100%.

    private double prevTime;
    private int prevEncoderPos;

    private PID leftDrive;
    private PID rightDrive;

    private ElapsedTime timer;

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
