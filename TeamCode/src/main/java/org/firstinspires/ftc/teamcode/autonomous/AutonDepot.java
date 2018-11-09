package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subsystems.drivetrain.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.imu.BoschIMU;

import java.util.ArrayList;
import java.util.List;

@Autonomous
public class AutonDepot extends OpMode {

    MecanumDrive drive;
    BoschIMU imu;

    int step = 0;

    public void init() {
        DcMotor rf = hardwareMap.dcMotor.get("frontRight");
        DcMotor lf = hardwareMap.dcMotor.get("frontLeft");
        DcMotor rb = hardwareMap.dcMotor.get("backRight");
        DcMotor lb = hardwareMap.dcMotor.get("backLeft");
        List<DcMotor> motors = new ArrayList<>();
        motors.add(rf);
        motors.add(lf);
        motors.add(rb);
        motors.add(lb);

        imu = new BoschIMU(hardwareMap.get(BNO055IMU.class, "imu"));

        drive = new MecanumDrive(motors, imu, telemetry);
    }

    public void loop() {
        if (step == 0) {
            drive.move(20, 0, .5, false);
            step++;
        } else if (step == 1) {
            drive.move(20, 90, .5, false);
            step++;
        } else if (step == 2) {
            drive.move(20, 180, .5, false);
            step++;
        } else if (step == 3) {
            drive.move(20, 270, .5, false);
            step++;
        } else if (step == 4) {
            drive.turn(90, .5, false);
            step++;
        } else if (step == 5) {
            drive.turn(270, .25, false);
            step++;
        } else {
            drive.stop();
        }
    }

}
