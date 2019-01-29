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

    private final double kp = .01d;
    private final double ki = .05d;
    private final double kd = .03d;
    private final double intMax = 1;
    private final double intMin = 0;
    private final double outputMax = 1;
    private final double outputMin = -1;
    private final double endTolerance = 50;

    private double frLastEnc, flLastEnc, brLastEnc, blLastEnc;

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
        /*frontRight.setTargetPosition(ticks);
        frontLeft.setTargetPosition(ticks);
        backRight.setTargetPosition(ticks);
        backLeft.setTargetPosition(ticks);*/
        int ticks = (int) convert(inches);
        //zeroEncoders();
        timer.reset();
        setSetpoint(-speed, speed);
        frontRight.setPower(speed);
        frontLeft.setPower(speed);
        backRight.setPower(speed);
        backLeft.setPower(speed);
        while (frontRight.getCurrentPosition() < ticks + frLastEnc && frontLeft.getCurrentPosition() < ticks + flLastEnc
                && backRight.getCurrentPosition() < ticks + brLastEnc && backLeft.getCurrentPosition() < ticks + blLastEnc) {
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
        updateLastEnc();
        stopMotors();
    }

    public void moveBackward(double inches, double speed) {
        /*frontRight.setTargetPosition(-ticks);
        frontLeft.setTargetPosition(-ticks);
        backRight.setTargetPosition(-ticks);
        backLeft.setTargetPosition(-ticks);*/
        int ticks = (int) convert(inches);
        //zeroEncoders();
        timer.reset();
        setSetpoint(-speed, speed);
        frontRight.setPower(-speed);
        frontLeft.setPower(-speed);
        backRight.setPower(-speed);
        backLeft.setPower(-speed);
        while (frontRight.getCurrentPosition() > -ticks + frLastEnc && frontLeft.getCurrentPosition() > -ticks + flLastEnc
                && backRight.getCurrentPosition() > -ticks + brLastEnc && backLeft.getCurrentPosition() > -ticks + blLastEnc) {
            telemetry.addData("frontRight", frontRight.getCurrentPosition());
            telemetry.addData("frontLeft", frontLeft.getCurrentPosition());
            telemetry.addData("backRight", backRight.getCurrentPosition());
            telemetry.addData("backLeft", backLeft.getCurrentPosition());
            telemetry.addData("Timer: ", timer.milliseconds());
            telemetry.update();

            frontRight.setPower(frPID.update(-ticks, frontRight.getCurrentPosition(), 50));
            frontLeft.setPower(flPID.update(-ticks, frontLeft.getCurrentPosition(), 50));
            backRight.setPower(brPID.update(-ticks, backRight.getCurrentPosition(), 50));
            backLeft.setPower(blPID.update(-ticks, backLeft.getCurrentPosition(), 50));

            if (timer.milliseconds() > 4000) break;
        }
        stopMotors();
        updateLastEnc();
    }

    public void turnLeft(int degrees, double speed) {
        /*frontRight.setTargetPosition(ticks);
        frontLeft.setTargetPosition(-ticks);
        backRight.setTargetPosition(ticks);
        backLeft.setTargetPosition(-ticks);*/
        double mult = 14d;
        int ticks = (int) convert(mult * Math.toRadians(degrees));
        //zeroEncoders();
        timer.reset();
        setSetpoint(-speed, speed);
        frontRight.setPower(speed);
        frontLeft.setPower(-speed);
        backRight.setPower(speed);
        backLeft.setPower(-speed);
        while (frontRight.getCurrentPosition() < ticks + frLastEnc && frontLeft.getCurrentPosition() > -ticks + flLastEnc
                && backRight.getCurrentPosition() < ticks + brLastEnc && backLeft.getCurrentPosition() > -ticks + blLastEnc) {
            telemetry.addData("frontRight", frontRight.getCurrentPosition());
            telemetry.addData("frontLeft", frontLeft.getCurrentPosition());
            telemetry.addData("backRight", backRight.getCurrentPosition());
            telemetry.addData("backLeft", backLeft.getCurrentPosition());
            telemetry.addData("Timer: ", timer.milliseconds());
            telemetry.update();

            frontRight.setPower(frPID.update(ticks, frontRight.getCurrentPosition(), 50));
            frontLeft.setPower(flPID.update(-ticks, frontLeft.getCurrentPosition(), 50));
            backRight.setPower(brPID.update(ticks, backRight.getCurrentPosition(), 50));
            backLeft.setPower(blPID.update(-ticks, backLeft.getCurrentPosition(), 50));

            if (timer.milliseconds() > 4000) break;
        }
        stopMotors();
        updateLastEnc();
    }

    public void turnRight(int degrees, double speed) {
        double mult = 14d;
        int ticks = (int) convert(mult * Math.toRadians(degrees));
        //zeroEncoders();
        timer.reset();
        setSetpoint(-speed, speed);
        frontRight.setPower(-speed);
        frontLeft.setPower(speed);
        backRight.setPower(-speed);
        backLeft.setPower(speed);
        while (frontRight.getCurrentPosition() > -ticks + frLastEnc && frontLeft.getCurrentPosition() < ticks + flLastEnc
                && backRight.getCurrentPosition() > -ticks + brLastEnc && backLeft.getCurrentPosition() < ticks + blLastEnc) {
            telemetry.addData("frontRight", frontRight.getCurrentPosition());
            telemetry.addData("frontLeft", frontLeft.getCurrentPosition());
            telemetry.addData("backRight", backRight.getCurrentPosition());
            telemetry.addData("backLeft", backLeft.getCurrentPosition());
            telemetry.addData("Timer: ", timer.milliseconds());
            telemetry.update();

            frontRight.setPower(frPID.update(-ticks, frontRight.getCurrentPosition(), 50));
            frontLeft.setPower(flPID.update(ticks, frontLeft.getCurrentPosition(), 50));
            backRight.setPower(brPID.update(-ticks, backRight.getCurrentPosition(), 50));
            backLeft.setPower(blPID.update(ticks, backLeft.getCurrentPosition(), 50));

            if (timer.milliseconds() > 4000) break;
        }
        stopMotors();
        updateLastEnc();
    }

    public void strafeLeft(double inches, double speed) {
        int ticks = (int) convert(inches);
        timer.reset();
        //zeroEncoders();
        setSetpoint(-speed, speed);
        /*frontRight.setTargetPosition(ticks);
        frontLeft.setTargetPosition(-ticks);
        backRight.setTargetPosition(-ticks);
        backLeft.setTargetPosition(ticks);*/
        frontRight.setPower(speed);
        frontLeft.setPower(-speed);
        backRight.setPower(-speed);
        backLeft.setPower(speed);
        while (frontRight.getCurrentPosition() < ticks + frLastEnc && frontLeft.getCurrentPosition() > -ticks + flLastEnc
                && backRight.getCurrentPosition() > -ticks + brLastEnc && backLeft.getCurrentPosition() < ticks + blLastEnc) {
            /*frontRight.setPower(frPID.update(ticks, frontRight.getCurrentPosition(), 50));
            frontLeft.setPower(flPID.update(-ticks, frontLeft.getCurrentPosition(), 50));
            backRight.setPower(brPID.update(-ticks, backRight.getCurrentPosition(), 50));
            backLeft.setPower(blPID.update(ticks, backLeft.getCurrentPosition(), 50));*/

            if (timer.milliseconds() > 4500) break;
        }
        stopMotors();
        updateLastEnc();
    }

    public void strafeRight (double inches, double speed) {
        int ticks = (int) convert(inches);
        timer.reset();
        //zeroEncoders();
        setSetpoint(-speed, speed);
        /*frontRight.setTargetPosition(-ticks);
        frontLeft.setTargetPosition(ticks);
        backRight.setTargetPosition(ticks);
        backLeft.setTargetPosition(-ticks);*/
        frontRight.setPower(-speed);
        frontLeft.setPower(speed);
        backRight.setPower(speed);
        backLeft.setPower(-speed);
        while(frontRight.getCurrentPosition() > -ticks + frLastEnc && frontLeft.getCurrentPosition() < ticks + flLastEnc
                && backRight.getCurrentPosition() < ticks + brLastEnc && backLeft.getCurrentPosition() > -ticks + blLastEnc){
            /*frontRight.setPower(frPID.update(-ticks, frontRight.getCurrentPosition(), 50));
            frontLeft.setPower(flPID.update(ticks, frontLeft.getCurrentPosition(), 50));
            backRight.setPower(brPID.update(ticks, backRight.getCurrentPosition(), 50));
            backLeft.setPower(blPID.update(-ticks, backLeft.getCurrentPosition(), 50));*/

            if (timer.milliseconds() > 4500) break;
        }
        stopMotors();
        updateLastEnc();
    }

    private void zeroEncoders() {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void setSetpoint(double min, double max) {
        frPID.setOutputBounds(min, max);
        flPID.setOutputBounds(min, max);
        brPID.setOutputBounds(min, max);
        blPID.setOutputBounds(min, max);
    }

    public void updateLastEnc() {
        frLastEnc = frontRight.getCurrentPosition();
        flLastEnc = frontLeft.getCurrentPosition();
        brLastEnc = backRight.getCurrentPosition();
        blLastEnc = backLeft.getCurrentPosition();
    }

}
