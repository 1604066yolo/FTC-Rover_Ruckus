package org.firstinspires.ftc.teamcode.subsystems.drivetrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.imu.IIMU;
import org.opencv.core.TermCriteria;

import java.util.List;

public class MecanumDrive implements IDrivetrain {

    private List<DcMotor> motors; //rf, lf, rb, lb
    private IIMU imu;

    private final double END_TOLERANCE_DEG = 5;

    private ElapsedTime timer;

    Telemetry telemetry;

    public MecanumDrive(List<DcMotor> motors, IIMU imu, Telemetry telemetry) {
        this.motors = motors;
        this.imu = imu;
        this.imu.init();
        this.telemetry = telemetry;

        timer = new ElapsedTime();
    }

    public void setAllPowers(double rf, double lf, double rb, double lb) {
        motors.get(0).setPower(rf);
        motors.get(1).setPower(lf);
        motors.get(2).setPower(rb);
        motors.get(3).setPower(lb);
    }

    @Override
    public boolean move(double distance, double speed) {
        double mult = 8 / speed;
        int time = (int) (mult * distance);
        double angle = Math.toRadians(0);
        double x = Math.cos(angle);
        double rfPower = speed * (Math.cos(angle) + x);
        double lfPower = speed * (Math.sin(angle) + x);
        double rbPower = speed * (Math.sin(angle) + x);
        double lbPower = speed * (Math.cos(angle) + x);
        setAllPowers(rfPower, lfPower, rbPower, lbPower);
        pause(time);
        stop();
        return false;
    }

    @Override
    public boolean turn(double angle, double speed) {
        imu.setCurrentPosToZero();
        if (angle <= 180) {
            setAllPowers(-speed, speed, -speed, speed);
        } else {
            setAllPowers(speed, -speed, speed, -speed);
        }
        checkAngle(angle);
        stop();
        return true;
    }

    @Override
    public void stop() {
        setAllPowers(0, 0, 0, 0);
    }

    public void pause(int millis) {
        timer.reset();
        while (timer.milliseconds() < millis)
            continue;
    }

    public void checkAngle(double target) {
        while (imu.getZAngle() < target - END_TOLERANCE_DEG || imu.getZAngle() > target + END_TOLERANCE_DEG);
    }

}
