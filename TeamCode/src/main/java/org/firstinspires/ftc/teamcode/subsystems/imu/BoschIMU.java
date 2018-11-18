package org.firstinspires.ftc.teamcode.subsystems.imu;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;

public class BoschIMU implements IIMU {

    BNO055IMU imu;
    double offset;

    public BoschIMU(BNO055IMU imu) {
        this.imu = imu;
    }

    @Override
    public double getXAngle() {
        return -imu.getAngularOrientation().thirdAngle - offset;
    }

    @Override
    public double getYAngle() {
        return -imu.getAngularOrientation().secondAngle - offset;
    }

    @Override
    public double getZAngle() {
        return -imu.getAngularOrientation().firstAngle - offset;
    }

    @Override
    public double getXAcc() {
        return imu.getAcceleration().xAccel;
    }

    @Override
    public double getYAcc() {
        return imu.getAcceleration().yAccel;
    }

    @Override
    public double getZAcc() {
        return imu.getAcceleration().zAccel;
    }

    @Override
    public double getXVel() {
        return imu.getVelocity().xVeloc;
    }

    @Override
    public double getYVel() {
        return imu.getVelocity().yVeloc;
    }

    @Override
    public double getZVel() {
        return imu.getVelocity().zVeloc;
    }

    public double getXPos() {
        return imu.getPosition().x;
    }

    public double getYPos() {
        return imu.getPosition().y;
    }

    public double getZPos() {
        return imu.getPosition().z;
    }

    @Override
    public void calibrateDefaults() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IIMU";
        imu.initialize(parameters);
        BNO055IMU.CalibrationData calibrationData = imu.readCalibrationData();
        String filename = "AdafruitIMUCalibration.json";
        File file = AppUtil.getInstance().getSettingsFile(filename);
        ReadWriteFile.writeFile(file, calibrationData.serialize());
    }

    @Override
    public void init() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu.initialize(parameters);
    }

    @Override
    public void setOffset(double offset) {
        this.offset = offset;
    }

    @Override
    public void setCurrentPosToZero() {
        offset = -imu.getAngularOrientation().firstAngle;
    }


}
