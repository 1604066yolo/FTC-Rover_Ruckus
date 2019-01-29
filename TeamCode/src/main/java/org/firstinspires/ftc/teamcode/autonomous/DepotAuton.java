package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.internal.ftdi.eeprom.FT_EEPROM_232H;
import org.firstinspires.ftc.teamcode.subsystems.drivetrain.Drivetrain;

@Autonomous
public class DepotAuton extends OpMode {

    DepotSource depotSource;

    int step = 0;

    public void init() {
        Drivetrain dt = new Drivetrain(hardwareMap, gamepad1, gamepad2, telemetry);
        depotSource = new DepotSource();
        depotSource.init(dt);
    }

    public void loop() {
        if (step == 0) {
            depotSource.landing();
            step++;
        } else if (step == 1) {
            depotSource.gyroPositioning();
            step++;
        } else if (step == 2) {
            depotSource.sampleMiddle();
            step++;
        } else if (step == 3){
            depotSource.claimMiddle();
            step++;
        } else {
            depotSource.stopMotors();
        }
    }

}
