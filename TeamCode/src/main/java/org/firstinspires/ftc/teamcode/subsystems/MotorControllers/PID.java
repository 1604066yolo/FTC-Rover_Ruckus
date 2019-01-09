package org.firstinspires.ftc.teamcode.subsystems.MotorControllers;

/**
 * This class houses the math behind a PID controller.
 * To use PID Control with a motor, see the PIDDcMotor class.
 */
public class PID {

    private double kp;
    private double ki;
    private double kd;

    private double integralMin;
    private double integralMax;

    private double outputMin;
    private double outputMax;

    private double lastError;
    private double runningIntegral;

    /**
     * Creates a PID Controller.
     * @param kp Proportional factor to scale error to output.
     * @param ki The number of seconds to eliminate all past errors.
     * @param kd The number of seconds to predict the error in the future.
     * @param integralMin The min of the running integral.
     * @param integralMax The max of the running integral.
     * @param outputMin The min of the PID output.
     * @param outputMax The max of the PID output.
     */
    public PID(double kp, double ki, double kd, double integralMin, double integralMax, double outputMin, double outputMax) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.integralMin = integralMin;
        this.integralMax = integralMax;
        this.outputMin = outputMin;
        this.outputMax = outputMax;
        this.lastError = 0;
        this.runningIntegral = 0;
    }

    /**
     * Performs a PID update and returns the output control.
     * @param desiredValue The desired state value (e.g. speed).
     * @param actualValue The actual state value (e.g. speed).
     * @param dt The amount of time (sec) elapsed since last update.
     * @return The output which impacts state value (e.g. motor throttle).
     */
    public double update(double desiredValue, double actualValue, double dt) {
        double error = desiredValue - actualValue;
        double p = error * kp;
        double i = ki * clampValue(error + runningIntegral, integralMin, integralMax);
        double d = kd * ((error - lastError) / dt);
        runningIntegral += error;
        lastError = error;
        return clampValue(p+i+d, outputMin, outputMax);
    }

    /**
     * Clamps a value to a given range.
     * @param value The value to clamp.
     * @param min The min clamp.
     * @param max The max clamp.
     * @return The clamped value.
     */
    public static double clampValue(double value, double min, double max) {
        return Math.min(max, Math.max(min, value));
    }

    public void setOutputBounds(double min, double max) {
        outputMin = min; outputMax = max;
    }

}