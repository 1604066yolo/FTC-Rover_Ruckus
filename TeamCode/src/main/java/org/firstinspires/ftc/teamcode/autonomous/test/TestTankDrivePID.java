package org.firstinspires.ftc.teamcode.autonomous.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.PID;

@Autonomous(group = "test")
public class TestTankDrivePID extends OpMode {

    private DcMotor r;
    private DcMotor l;
    private PID leftDrive;
    private PID rightDrive;

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
    private int prevLeftEncoderPos;
    private int prevRightEncoderPos;

    private final double TICKS_PER_ROTATION = 1440;
    private final double GEAR_RATIO = 3;
    private final double CIRCUMFERENCE = 4;
    private double convert(double d) { return (d / CIRCUMFERENCE) * TICKS_PER_ROTATION / GEAR_RATIO; }

    private double prevLEncoderPos;
    private double prevREncoderPos;

    private ElapsedTime timer;

    int step = 0;

    @Override
    public void init() {
        r = hardwareMap.dcMotor.get("right");
        l = hardwareMap.dcMotor.get("left");

        l.setDirection(DcMotorSimple.Direction.REVERSE);

        r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        r.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        l.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        if (step == 0 && driveForward(10, .5))
            step++;
        else if (step == 1 && driveForward(10, .75))
            step++;
        else if (step == 2 && driveForward(10, 1))
            step++;
        else stopMotors();
    }

    @Override
    public void stop() {
        stopMotors();
    }

    public boolean driveForward(double distance, double speed) {
        double time = timer.milliseconds();
        double deltaTime = time - prevTime;

        double rightSpeed = (r.getCurrentPosition() - prevRightEncoderPos) / deltaTime;
        double leftSpeed = (l.getCurrentPosition() - prevLeftEncoderPos) / deltaTime;

        prevTime = time;
        prevRightEncoderPos = r.getCurrentPosition();
        prevLeftEncoderPos = l.getCurrentPosition();

        double right = rightDrive.update(speed, rightSpeed, deltaTime);
        double left = leftDrive.update(speed, leftSpeed, deltaTime);

        telemetry.addData("currentRightSpeed", rightSpeed);
        telemetry.addData("currentLeftSpeed", leftSpeed);
        telemetry.addData("PID rightSpeed", right);
        telemetry.addData("PID leftSpeed", left);

        double ticks = convert(distance);
        r.setPower(right);
        l.setPower(left);
        if (r.getCurrentPosition() < prevREncoderPos + ticks && l.getCurrentPosition() < prevLEncoderPos + ticks)
            return false;
        prevREncoderPos = r.getCurrentPosition();
        prevLEncoderPos = l.getCurrentPosition();
        stopMotors();
        return true;
    }

    public void stopMotors() {
        r.setPower(0);
        l.setPower(0);
    }

}
