package org.firstinspires.ftc.teamcode.subsystems.drivetrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.PID;
import org.firstinspires.ftc.teamcode.subsystems.imu.IIMU;

import java.util.List;

public class TankDrive implements IDrivetrain {

    private final double ticksPerRevolution = 1440;
    private final double gearRatio = 3;
    private final double gearedTicksPerRevolution = ticksPerRevolution * gearRatio;

    private final double kp = 1;
    private final double ki = 1.0;
    private final double kd = 0.1;

    private final double drivePidIntMax = 1;
    private final double driveOutMax = 1.0;

    private double prevTime;
    private int prevLeftEncoderPos;
    private int prevRightEncoderPos;

    private PID leftDrive;
    private PID rightDrive;

    private List<DcMotor> motors; //r, l
    private IIMU imu;

    private ElapsedTime timer;

    Telemetry telemetry;

    public TankDrive(List<DcMotor> motors, IIMU imu, Telemetry telemetry) {
        this.motors = motors;
        this.imu = imu;
        this.telemetry = telemetry;

        timer = new ElapsedTime();
        rightDrive = new PID(kp, ki, kd, -drivePidIntMax, drivePidIntMax, -driveOutMax, driveOutMax);
        leftDrive = new PID(kp, ki, kd, -drivePidIntMax, drivePidIntMax, -driveOutMax, driveOutMax);

        prevTime = 0;
        prevRightEncoderPos = motors.get(0).getCurrentPosition();
        prevLeftEncoderPos = motors.get(1).getCurrentPosition();
    }

    @Override
    public boolean move(double distance, double speed) {
        double time = timer.milliseconds();
        double deltaTime = time - prevTime;

        double rightSpeed = (motors.get(0).getCurrentPosition() - prevRightEncoderPos) / deltaTime;
        double leftSpeed = (motors.get(1).getCurrentPosition() - prevLeftEncoderPos) / deltaTime;

        prevTime = time;
        prevRightEncoderPos = motors.get(0).getCurrentPosition();
        prevLeftEncoderPos = motors.get(1).getCurrentPosition();

        double right = rightDrive.update(speed, rightSpeed, deltaTime);
        double left = leftDrive.update(speed, leftSpeed, deltaTime);
        right = clampMotorPower(right);
        left = clampMotorPower(left);

        motors.get(0).setPower(right);
        motors.get(1).setPower(left);

        if (distance * gearedTicksPerRevolution > motors.get(0).getCurrentPosition()
                && distance * gearedTicksPerRevolution > motors.get(1).getCurrentPosition()) {
            stop();
            return true;
        }
        return false;
    }

    @Override
    public boolean turn(double angle, double speed) {
        double time = timer.milliseconds();
        double deltaTime = time - prevTime;

        double rightSpeed = (motors.get(0).getCurrentPosition() - prevRightEncoderPos) / deltaTime;
        double leftSpeed = (motors.get(1).getCurrentPosition() - prevLeftEncoderPos) / deltaTime;

        prevTime = time;
        prevRightEncoderPos = motors.get(0).getCurrentPosition();
        prevLeftEncoderPos = motors.get(1).getCurrentPosition();

        double right = rightDrive.update(speed, rightSpeed, deltaTime);
        double left = leftDrive.update(-speed, leftSpeed, deltaTime);
        right = clampMotorPower(right);
        left = clampMotorPower(left);

        motors.get(0).setPower(right);
        motors.get(1).setPower(left);

        if (angle * gearedTicksPerRevolution > motors.get(0).getCurrentPosition()
                && angle * gearedTicksPerRevolution < motors.get(1).getCurrentPosition()) {
            stop();
            return true;
        }
        return false;
    }

    public void moveToPos(double startX, double startY, double targetX, double targetY, double startAngle, double endAngle, boolean endAngleIndifference) {
        double targetAngle;
        try {
            targetAngle = Math.atan((targetY - startY) / (targetX - startX));
        } catch (Exception e) {
            if (targetY > startY)
                targetAngle = 90;
            else targetAngle = -90;
        }
        double alignToTargetAngle = targetAngle - startAngle;
        while (turn(alignToTargetAngle, .75));

        double distance = Math.sqrt(Math.pow(targetX - startX, 2) + Math.pow(targetY - startY, 2));
        while (move(distance, .75));
        if (!endAngleIndifference) {
            double alignToEndAngle = endAngle - alignToTargetAngle;
            turn(alignToEndAngle, .75);
        }
    }

    @Override
    public void stop() {
        setAllPowers(0, 0);
    }

    public void setAllPowers(double r, double l) {
        motors.get(0).setPower(r);
        motors.get(0).setPower(l);
    }

    public double clampMotorPower(double power) {
        return Math.max(-1, Math.min(1, power));
    }

}
