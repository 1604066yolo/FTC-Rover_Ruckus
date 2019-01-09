package org.firstinspires.ftc.teamcode.subsystems.drivetrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
    private final double endTolerance = 50;

    private final double TICKS_PER_ROTATION = 1440;
    private final double GEAR_RATIO = 2;
    private final double CIRCUMFERENCE = 4 * Math.PI;
    private double convert(double d) { return (d / CIRCUMFERENCE) * TICKS_PER_ROTATION / GEAR_RATIO; }

    private PID frPID, brPID, flPID, blPID;

    public EncoderDrivetrain(HardwareMap hmap, Gamepad g1, Gamepad g2, Telemetry telemetry) {
        super(hmap, g1, g2, telemetry);
        super.initParts();
        super.initDetector();

        frPID = new PID(kp, ki, kd, intMin, intMax, outputMin, outputMax);
        flPID = new PID(kp, ki, kd, intMin, intMax, outputMin, outputMax);
        brPID = new PID(kp, ki, kd, intMin, intMax, outputMin, outputMax);
        blPID = new PID(kp, ki, kd, intMin, intMax, outputMin, outputMax);

        timer = new ElapsedTime();
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

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
        //zeroEncoders();
        timer.reset();
        setSetpoint(-speed, speed);
        /*frontRight.setTargetPosition(ticks);
        frontLeft.setTargetPosition(ticks);
        backRight.setTargetPosition(ticks);
        backLeft.setTargetPosition(ticks);*/
        startMotors(speed);
        while (frontRight.getCurrentPosition() < ticks && frontLeft.getCurrentPosition() < ticks
                && backRight.getCurrentPosition() < ticks && backLeft.getCurrentPosition() < ticks) {
            telemetry.addData("frontRight", frontRight.getCurrentPosition());
            telemetry.addData("frontLeft", frontLeft.getCurrentPosition());
            telemetry.addData("backRight", backRight.getCurrentPosition());
            telemetry.addData("backLeft", backLeft.getCurrentPosition());
            telemetry.addData("Timer: ", timer.milliseconds());
            telemetry.update();

            frontRight.setPower(frPID.update(ticks, frontRight.getCurrentPosition(), 50));
            frontLeft.setPower(flPID.update(ticks, frontLeft.getCurrentPosition(), 50));
            backRight.setPower(brPID.update(ticks, backRight.getCurrentPosition(), 50));
            backLeft.setPower(blPID.update(ticks, backLeft.getCurrentPosition(), 50));

            if (timer.milliseconds() > 4000) break;
        }
        stopMotors();
    }

    public void moveBackward(double inches, double speed) {
        int ticks = (int) convert(inches);
        zeroEncoders();
        timer.reset();
        setSetpoint(-speed, speed);
        /*frontRight.setTargetPosition(-ticks);
        frontLeft.setTargetPosition(-ticks);
        backRight.setTargetPosition(-ticks);
        backLeft.setTargetPosition(-ticks);*/
        startMotors(speed);
        while (!isFinished(-ticks, -ticks, -ticks, -ticks)) {
            frPID.update(-ticks, frontRight.getCurrentPosition(), 50);
            flPID.update(-ticks, frontLeft.getCurrentPosition(), 50);
            brPID.update(-ticks, backRight.getCurrentPosition(), 50);
            blPID.update(-ticks, backLeft.getCurrentPosition(), 50);
            if (timer.milliseconds() > 4500) break;
        }
        stopMotors();
    }

    public void turnLeft(int degrees, double speed) {
        double mult = 14d;
        int ticks = (int) convert(mult * Math.toRadians(degrees));
       // zeroEncoders();
        timer.reset();
        setSetpoint(-speed, speed);
       /* frontRight.setTargetPosition(ticks);
        frontLeft.setTargetPosition(-ticks);
        backRight.setTargetPosition(ticks);
        backLeft.setTargetPosition(-ticks);*/
        startMotors(speed);
        while (!isFinished(ticks, -ticks, ticks, -ticks)) {
            frPID.update(ticks, frontRight.getCurrentPosition(), 50);
            flPID.update(-ticks, frontLeft.getCurrentPosition(), 50);
            brPID.update(ticks, backRight.getCurrentPosition(), 50);
            blPID.update(-ticks, backLeft.getCurrentPosition(), 50);
            if (timer.milliseconds() > 4500) break;
        }
        stopMotors();
    }

    public void turnRight(int degrees, double speed) {
        double mult = 14d;
        int ticks = (int) convert(mult * Math.toRadians(degrees));
       // zeroEncoders();
        timer.reset();
        setSetpoint(-speed, speed);
       /* frontRight.setTargetPosition(-ticks);
        frontLeft.setTargetPosition(ticks);
        backRight.setTargetPosition(-ticks);
        backLeft.setTargetPosition(ticks); */
        startMotors(speed);
        while (!isFinished(-ticks, ticks, -ticks, ticks)) {
            frPID.update(-ticks, frontRight.getCurrentPosition(), 50);
            flPID.update(ticks, frontLeft.getCurrentPosition(), 50);
            brPID.update(-ticks, backRight.getCurrentPosition(), 50);
            blPID.update(ticks, backLeft.getCurrentPosition(), 50);
            if (timer.milliseconds() > 4500) break;
        }
        stopMotors();
    }

    public void strafe(String direction, double inches, double speed) {
        int ticks = (int) convert(inches);
        timer.reset();
        zeroEncoders();
        setSetpoint(-speed, speed);
        if (direction == "left") {
            /*frontRight.setTargetPosition(ticks);
            frontLeft.setTargetPosition(-ticks);
            backRight.setTargetPosition(-ticks);
            backLeft.setTargetPosition(ticks);*/
            while (!isFinished(ticks, -ticks, -ticks, ticks)) {
                frPID.update(ticks, frontRight.getCurrentPosition(), 50);
                flPID.update(-ticks, frontLeft.getCurrentPosition(), 50);
                brPID.update(-ticks, backRight.getCurrentPosition(), 50);
                blPID.update(ticks, backLeft.getCurrentPosition(), 50);
                if (timer.milliseconds() > 4500) break;
            }
        } else {
            /*frontRight.setTargetPosition(-ticks);
            frontLeft.setTargetPosition(ticks);
            backRight.setTargetPosition(ticks);
            backLeft.setTargetPosition(-ticks);*/
            while(!isFinished(-ticks, ticks, ticks, -ticks)){
                frPID.update(-ticks, frontRight.getCurrentPosition(), 50);
                flPID.update(ticks, frontLeft.getCurrentPosition(), 50);
                brPID.update(ticks, backRight.getCurrentPosition(), 50);
                blPID.update(-ticks, backLeft.getCurrentPosition(), 50);
            }
        }
        startMotors(speed);

        stopMotors();
    }

    private void zeroEncoders() {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private void startMotors(double speed) {
        //frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setPower(speed);
        frontLeft.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);
    }

    public void setSetpoint(double min, double max) {
        frPID.setOutputBounds(min, max);
        flPID.setOutputBounds(min, max);
        brPID.setOutputBounds(min, max);
        blPID.setOutputBounds(min, max);
    }

    public boolean isFinished(double fr, double fl, double br, double bl) {
        return (frontRight.getCurrentPosition() >= fr - endTolerance && frontRight.getCurrentPosition() <= fr + endTolerance)
                && (frontLeft.getCurrentPosition() >= fr - endTolerance && frontLeft.getCurrentPosition() <= fr + endTolerance)
                && (backLeft.getCurrentPosition() >= bl - endTolerance && backLeft.getCurrentPosition() <= bl + endTolerance)
                && (backRight.getCurrentPosition() >= br - endTolerance && backRight.getCurrentPosition() <= br + endTolerance);


    }

}
