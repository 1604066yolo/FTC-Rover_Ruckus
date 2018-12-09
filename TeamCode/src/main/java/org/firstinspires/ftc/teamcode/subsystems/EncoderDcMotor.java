package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Wrapper class for handling a DcMotor that has an encoder attached to it.
 * Handles the math and positioning for the encoder.
 */
public class EncoderDcMotor {

    private final double TICKS_PER_ROTATION = 1440; //change to the resolution of your encoder
    private double GEAR_RATIO; //Driven:Drive
    private double CIRCUMFERENCE; //set to whatever the circumference is of the thing you are trying to turn
    private double convert(double d) { return (d / CIRCUMFERENCE) * TICKS_PER_ROTATION / GEAR_RATIO; } //turns an input in inches to encoder ticks

    private final DcMotor motor;
    private double prevEncPos = 0d;

    public EncoderDcMotor(DcMotor motor, double circumference, double gearRatio) {
        this.motor = motor;
        this.CIRCUMFERENCE = circumference;
        this.GEAR_RATIO = gearRatio;
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    /**
     * Transforms the absolute encoder value into a relative one,
     * resetting every time that the motor receives a target
     * @return the current relative position of the motor
     */
    public double getPos() {
        return motor.getCurrentPosition() - prevEncPos;
    }

    /**
     * Effectively resets the encoder reading to 0
     */
    public void resetPos() {
        prevEncPos = motor.getCurrentPosition();
    }

    /**
     * Runs the motor either forward or backward
     * till the encoder ticks matches the inputted value
     *
     * @param target the position you want the motor to run to, in inches, negative to go backwards
     * @param speed the speed you want the motor to run at
     * @return whether the motor is at the target or not
     */
    public boolean runToTarget(double target, double speed) {
        double ticks = convert(target);
        motor.setPower(speed);
        if (speed > 0) {
            if (getPos() < ticks + prevEncPos)
                return false;
        } else {
            if (getPos() > ticks + prevEncPos)
                return false;
        }
        resetPos();
        return true;
    }

    /**
     * Returns the motor's average velocity over a given time
     * @param time the period over which you want to measure velocity in milliseconds
     * @return the current average velocity of the motor
     */
    public double getVelocity(double time) {
        ElapsedTime timer = new ElapsedTime();
        double firstPos = getPos();
        while (timer.milliseconds() < time);
        double endPos = getPos();
        return (endPos - firstPos) / time;
    }

    public DcMotor getMotor() {
        return motor;
    }

}
