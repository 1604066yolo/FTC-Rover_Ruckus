package org.firstinspires.ftc.teamcode.autonomous.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(group = "test", name = "TEST TankDrive with Encoders")
public class EncoderTest extends OpMode {

    private DcMotor r;
    private DcMotor l;

    private double prevREncoderPos;
    private double prevLEncoderPos;

    private final double turningRadius = 6;
    private final double TICKS_PER_ROTATION = 1440;
    private final double GEAR_RATIO = 3;
    private final double CIRCUMFERENCE = 4;
    private double convert(double d) { return (d / CIRCUMFERENCE) * TICKS_PER_ROTATION / GEAR_RATIO; }

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
        else if (step == 3 && driveBackward(10, .5))
            step++;
        else if (step == 4 && driveBackward(10, .75))
            step++;
        else if (step == 5 && driveBackward(10, 1))
            step++;
        else if (step == 6 && turnRight(90, .5))
            step++;
        else if (step == 7 && turnRight(90, .75))
            step++;
        else if (step == 8 && turnRight(90, 1))
            step++;
        else if (step == 6 && turnLeft(90, .5))
            step++;
        else if (step == 7 && turnLeft(90, .75))
            step++;
        else if (step == 8 && turnLeft(90, 1))
            step++;
        else stopMotors();
    }

    @Override
    public void stop() {
        stopMotors();
    }

    public boolean driveForward(double distance, double speed) {
        double ticks = convert(distance);
        r.setPower(speed);
        l.setPower(speed);
        if (r.getCurrentPosition() < prevREncoderPos + ticks && l.getCurrentPosition() < prevLEncoderPos + ticks)
            return false;
        prevREncoderPos = r.getCurrentPosition();
        prevLEncoderPos = l.getCurrentPosition();
        stopMotors();
        return true;
    }

    public boolean driveBackward(double distance, double speed) {
        double ticks = convert(distance);
        r.setPower(-speed);
        l.setPower(-speed);
        if (r.getCurrentPosition() > prevREncoderPos - ticks && l.getCurrentPosition() > prevLEncoderPos - ticks)
            return false;
        prevREncoderPos = r.getCurrentPosition();
        prevLEncoderPos = l.getCurrentPosition();
        stopMotors();
        return true;
    }

    public boolean turnRight(double angle, double speed) {
        angle = Math.toRadians(angle) * turningRadius;
        double ticks = convert(angle);
        r.setPower(-speed);
        l.setPower(speed);
        if (r.getCurrentPosition() > prevREncoderPos - ticks && l.getCurrentPosition() < prevLEncoderPos + ticks)
            return false;
        prevREncoderPos = r.getCurrentPosition();
        prevLEncoderPos = l.getCurrentPosition();
        stopMotors();
        return true;
    }

    public boolean turnLeft(double angle, double speed) {
        angle = Math.toRadians(angle) * turningRadius;
        double ticks = convert(angle);
        r.setPower(speed);
        l.setPower(-speed);
        if (r.getCurrentPosition() < prevREncoderPos + ticks && l.getCurrentPosition() > prevLEncoderPos - ticks)
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
