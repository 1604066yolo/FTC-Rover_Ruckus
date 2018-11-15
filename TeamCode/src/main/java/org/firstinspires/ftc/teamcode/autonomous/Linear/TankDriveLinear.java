package org.firstinspires.ftc.teamcode.autonomous.Linear;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.actions.ActionCaller;
import org.firstinspires.ftc.teamcode.actions.TankDriveAction;
import org.firstinspires.ftc.teamcode.subsystems.drivetrain.IDrivetrain;
import org.firstinspires.ftc.teamcode.subsystems.drivetrain.TankDrive;
import org.firstinspires.ftc.teamcode.subsystems.imu.BoschIMU;
import org.firstinspires.ftc.teamcode.subsystems.imu.IIMU;

import java.util.ArrayList;
import java.util.List;

@Autonomous(group = "test-linear", name = "tank-drive")
@Disabled
public class TankDriveLinear extends LinearOpMode {

    double x = 0, y = 0, angle = 0;

    TankDrive drivetrain;
    DcMotor r, l;
    List motors;
    ActionCaller actions;

    IIMU imu;
    BNO055IMU bimu;

    ElapsedTime timer = new ElapsedTime();
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime centerTimer;

    @Override
    public void runOpMode() throws InterruptedException {
        initMotors();

        actions = new ActionCaller();
        centerTimer = new ElapsedTime();

        bimu = hardwareMap.get(BNO055IMU.class, "imu");
        imu = new BoschIMU(bimu);
        imu.init();
        imu.setOffset(0);

        drivetrain = new TankDrive(motors, imu, telemetry);

        actions.addAction(new TankDriveAction(drivetrain, x, y, angle, 15, 45, 0, true));

        waitForStart();
        runtime.reset();


    }

    private void initMotors() {
        r = hardwareMap.dcMotor.get("r");
        l = hardwareMap.dcMotor.get("l");

        r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        r.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        l.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        l.setDirection(DcMotorSimple.Direction.REVERSE);

        motors = new ArrayList<DcMotor>();
        motors.add(r);
        motors.add(l);
    }

}
