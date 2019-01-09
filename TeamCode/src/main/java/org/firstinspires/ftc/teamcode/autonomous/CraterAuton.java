package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.subsystems.drivetrain.Drivetrain;

@Autonomous
@Disabled
public class CraterAuton extends OpMode {

    CraterSource craterSource;

    int step = 0;

    public void init() {
        Drivetrain dt = new Drivetrain(hardwareMap, gamepad1, gamepad2, telemetry);
        craterSource = new CraterSource();
        craterSource.init(dt);
    }

    public void loop() {
        if (step == 0) {
            craterSource.landing();
        } else if (step == 1) {
            craterSource.gyroPositioning();
            step++;
        } else if (step == 2) {
            craterSource.sampling();
            step++;
        } else if (step == 3) {
            craterSource.claiming1();
            step++;
        }
        else if (step == 4) {
            craterSource.claiming2();
            step++;
        }
        else if (step == 5){
            craterSource.parking();
        }
        else {
            craterSource.stopMotors();
        }
    }
}