package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This class acts as a wrapper for a DcMotor that you wish to control with PID.
 * The PID controller used runs with a default period of 50ms.
 */
public class PIDDcMotor extends EncoderDcMotor {

    private PID pid;
    private ElapsedTime timer;
    private double prevTime = 0d;
    private double period = 50;

    public PIDDcMotor(DcMotor motor, double circumference, double gearRatio, PID pid) {
        super(motor, circumference, gearRatio);
        this.pid = pid;

        timer = new ElapsedTime();
    }

    /**
     * Runs the motor to a desired position determining speed using
     * a PID controller
     *
     * @param target the position you want the motor to run to, in inches
     * @param speed the speed you want the motor to run at
     * @return whether the motor is at the target or not
     */
    @Override
    public boolean runToTarget(double target, double speed) {
        timer.reset();
        return super.runToTarget(target, getPIDOutput(speed));
    }

    private double getPIDOutput(double desiredVelocity) {
        double time = timer.milliseconds();
        double dt = time - prevTime;
        double actualVelocity = getVelocity(50);
        return pid.update(desiredVelocity, actualVelocity, dt);
    }

    /**
     * Sets a new period for the controller to run on
     *
     * @param period the new period, in milliseconds
     */
    public void setPeriod(double period) {
        this.period = period;
    }

}
