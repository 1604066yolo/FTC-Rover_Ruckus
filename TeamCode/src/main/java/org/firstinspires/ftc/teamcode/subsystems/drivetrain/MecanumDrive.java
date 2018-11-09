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

    private final double END_TOLERANCE = 5;

    private ElapsedTime turnTime;
    private ElapsedTime distanceTime;

    Telemetry telemetry;

    public MecanumDrive(List<DcMotor> motors, IIMU imu, Telemetry telemetry) {
        this.motors = motors;
        this.imu = imu;
        this.imu.init();
        this.telemetry = telemetry;

        turnTime = new ElapsedTime();
        distanceTime = new ElapsedTime();
    }

    public void setAllPowers(double rf, double lf, double rb, double lb) {
        motors.get(0).setPower(rf);
        motors.get(1).setPower(lf);
        motors.get(2).setPower(rb);
        motors.get(3).setPower(lb);
    }

    @Override
    public boolean move(double distance, double angle, double speed, boolean slowStartAndStop) {
        return false;
    }

    @Override
    public boolean rotate(double angle, double speed, boolean slowStartAndStop) {
        return false;
    }

    @Override
    public void stop() {

    }

}
