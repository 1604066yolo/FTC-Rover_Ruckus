package org.firstinspires.ftc.teamcode.autonomous.test;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.subsystems.imu.BoschIMU;

@Autonomous(group = "test", name = "TEST Accelerometer")
public class AccelerometerTest extends OpMode {

    BNO055IMU bimu;
    BoschIMU imu;

    @Override
    public void init() {
        bimu = hardwareMap.get(BNO055IMU.class, "imu");
        imu = new BoschIMU(bimu);
    }

    @Override
    public void loop() {
        telemetry.addData("X acceleration", imu.getXAcc());
        telemetry.addData("Y acceleration", imu.getYAcc());
        telemetry.addData("Z acceleration", imu.getZAcc());

        telemetry.addData("X velocity", imu.getXVel());
        telemetry.addData("Y velocity", imu.getYVel());
        telemetry.addData("Z velocity", imu.getZVel());

        telemetry.addData("X position", imu.getXPos());
        telemetry.addData("Y position", imu.getYPos());
        telemetry.addData("Z position", imu.getZPos());
    }

    @Override
    public void stop() {
        telemetry.clear();
    }

}
