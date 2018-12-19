package org.firstinspires.ftc.teamcode.subsystems.MotorControllers;

import com.disnodeteam.dogecv.math.MathFTC;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subsystems.imu.BoschIMU;

public class GPS {

    private final double ticksPerDegree = 10; //TODO tweak
    private final double ticksPerInch = 120; //TODO tweak

    private double x, y, angle;
    private double prevLeft, prevRight;
    private double prevHorizontal; //moving right -> positive, moving left -> negative

    private DcMotor left, right, horizontal;
    private BoschIMU imu;

    /**
     * Initializes the GPS with given x and y coordinates
     * x -> front (+) and back (-)
     * y -> left (+) and right (-)
     *
     * @param x initial x pos
     * @param y initial y pos
     * @param angle initial angle
     */
    public GPS(double x, double y, double angle, DcMotor left, DcMotor right, DcMotor horizontal, BoschIMU imu) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.left = left;
        this.right = right;
        this.horizontal = horizontal;
        this.imu = imu;
    }

    public void updatePos() {
        double encoderDistanceTraveled = ((right.getCurrentPosition() + left.getCurrentPosition()) / 2) / ticksPerInch; //TODO Use accelerometer readings if accurate
        double encoderAngleTraveled = ((right.getCurrentPosition() - left.getCurrentPosition()) / 2) / ticksPerDegree;
        double angleTraveled = (encoderAngleTraveled - imu.getZAngle()) / 2; //TODO Add Kalman Filter
        angle += angleTraveled;
        x += encoderDistanceTraveled * Math.cos(Math.toRadians(angle));
        y += encoderDistanceTraveled * Math.sin(Math.toRadians(angle));
    }


}
