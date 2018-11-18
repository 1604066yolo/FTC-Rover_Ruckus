package org.firstinspires.ftc.teamcode.autonomous.test;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subsystems.imu.BoschIMU;

public class TankDriveAccelerometerTest extends OpMode {

    BNO055IMU bimu;
    BoschIMU imu;

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
        bimu = hardwareMap.get(BNO055IMU.class, "imu");
        imu = new BoschIMU(bimu);
    }

    @Override
    public void loop() {
        telemetry.addData("X acceleration", imu.getXAcc());
        telemetry.addData("Y acceleration", imu.getYAcc());
        telemetry.addData("Z acceleration", imu.getZAcc());

        telemetry.addData("X velocity", imu.getXVel());
        telemetry.addData("Y velocity", imu.getYVel());
        telemetry.addData("Z velocity", imu.getZVel());

        telemetry.addData("X position", imu.getXPos());
        telemetry.addData("Y position", imu.getYPos());
        telemetry.addData("Z position", imu.getZPos());

        if (step == 0 && driveForward(10, .75))
            step++;
        else if (step == 1 && driveBackward(10, .5))
            step++;
        else stopMotors();
    }

    @Override
    public void stop() {
        telemetry.clear();
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

