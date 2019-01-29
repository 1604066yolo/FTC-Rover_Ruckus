package org.firstinspires.ftc.teamcode.autonomous.test;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.subsystems.drivetrain.Drivetrain;

@Autonomous
public class TimeBasedTest extends OpMode {

    Drivetrain drivetrain;

    public void init() {
        drivetrain = new Drivetrain(hardwareMap, gamepad1, gamepad2, telemetry);

    }

    public void loop() {

    }

}
