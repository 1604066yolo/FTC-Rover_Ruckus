package org.firstinspires.ftc.teamcode.subsystems.drivetrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.MotorControllers.PID;

public class EncoderDrivetrain extends Drivetrain {

    ElapsedTime timer;

    private PID fl, bl, fr, br;

    private final double kp = 1;
    private final double ki = 1;
    private final double kd = 1;
    private final double intMax = 1;
    private final double intMin = 0;
    private final double outputMax = 1;
    private final double outputMin = -1;

    private final double TICKS_PER_ROTATION = 1440;
    private final double GEAR_RATIO = 2;
    private final double CIRCUMFERENCE = 4 * Math.PI;
    private double convert(double d) { return (d / CIRCUMFERENCE) * TICKS_PER_ROTATION / GEAR_RATIO; }

    public EncoderDrivetrain(HardwareMap hmap, Gamepad g1, Gamepad g2, Telemetry telemetry) {
        super(hmap, g1, g2, telemetry);
        super.initParts();
        super.initDetector();

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        fl = new PID(kp, ki, kd, intMin, intMax, outputMin, outputMax);
        bl = new PID(kp, ki, kd, intMin, intMax, outputMin, outputMax);
        fr = new PID(kp, ki, kd, intMin, intMax, outputMin, outputMax);
        br = new PID(kp, ki, kd, intMin, intMax, outputMin, outputMax);
    }

    @Override
    public void sleep(int millis) {
        double t = timer.milliseconds();
        while (timer.milliseconds() < t + millis);
    }


    public void moveForward(double inches, double speed) {
        int ticks = (int) convert(inches);
        zeroEncoders();
        frontRight.setTargetPosition(ticks);
        frontLeft.setTargetPosition(ticks);
        backRight.setTargetPosition(ticks);
        backLeft.setTargetPosition(ticks);
        startMotors(speed);
        while (frontRight.isBusy() || frontLeft.isBusy() || backRight.isBusy() || backLeft.isBusy());
        stopMotors();
    }

    public void moveBackward(double inches, double speed) {
        int ticks = (int) convert(inches);
        zeroEncoders();
        frontRight.setTargetPosition(-ticks);
        frontLeft.setTargetPosition(-ticks);
        backRight.setTargetPosition(-ticks);
        backLeft.setTargetPosition(-ticks);
        startMotors(speed);
        while (frontRight.isBusy() || frontLeft.isBusy() || backRight.isBusy() || backLeft.isBusy());
        stopMotors();
    }

    public void turnLeft(int degrees, double speed) {
        double mult = 3d;
        int ticks = (int) convert(mult * Math.toRadians(degrees));
        zeroEncoders();
        frontRight.setTargetPosition(ticks);
        frontLeft.setTargetPosition(-ticks);
        backRight.setTargetPosition(ticks);
        backLeft.setTargetPosition(-ticks);
        startMotors(speed);
        while (frontRight.isBusy() || frontLeft.isBusy() || backRight.isBusy() || backLeft.isBusy());
        stopMotors();
    }

    public void turnRight(int degrees, double speed) {
        double mult = 3d;
        int ticks = (int) convert(mult * Math.toRadians(degrees));
        zeroEncoders();
        frontRight.setTargetPosition(-ticks);
        frontLeft.setTargetPosition(ticks);
        backRight.setTargetPosition(-ticks);
        backLeft.setTargetPosition(ticks);
        startMotors(speed);
        while (frontRight.isBusy() || frontLeft.isBusy() || backRight.isBusy() || backLeft.isBusy());
        stopMotors();
    }

    public void strafe(String direction, double inches, double speed) {
        int ticks = (int) convert(inches);
        zeroEncoders();
        if (direction == "left") {
            frontRight.setTargetPosition(ticks);
            frontLeft.setTargetPosition(-ticks);
            backRight.setTargetPosition(-ticks);
            backLeft.setTargetPosition(ticks);
        } else {
            frontRight.setTargetPosition(-ticks);
            frontLeft.setTargetPosition(ticks);
            backRight.setTargetPosition(ticks);
            backLeft.setTargetPosition(-ticks);
        }
        startMotors(speed);
        while (frontRight.isBusy() || frontLeft.isBusy() || backRight.isBusy() || backLeft.isBusy());
        stopMotors();
    }

    private void zeroEncoders() {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private void startMotors(double speed) {
        frontRight.setPower(-speed);
        frontLeft.setPower(speed);
        backRight.setPower(-speed);
        backLeft.setPower(speed);
    }

}
