package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subsystems.drivetrain.IDrivetrain;
import org.firstinspires.ftc.teamcode.subsystems.drivetrain.TankDrive;
import org.firstinspires.ftc.teamcode.subsystems.imu.BoschIMU;

import java.util.Arrays;
import java.util.List;

public class Test extends OpMode {

    IDrivetrain drivetrain;

    @Override
    public void init() {
        DcMotor[] ms = {hardwareMap.dcMotor.get("right"), hardwareMap.dcMotor.get("left")};
        List<DcMotor> motors = Arrays.asList(ms);
        BoschIMU imu = new BoschIMU(hardwareMap.get(BNO055IMU.class, "imu"));
        drivetrain = new TankDrive(motors, imu, telemetry);
    }

    @Override
    public void loop() {

    }

}
