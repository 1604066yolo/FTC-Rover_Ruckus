package org.firstinspires.ftc.teamcode.autonomous.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.drivetrain.DcExDrivetrain;

@Autonomous
public class SevenFoldPathTest extends LinearOpMode {

    private DcExDrivetrain drivetrain;

    @Override
    public void runOpMode() {
        initialize();

        waitForStart();

        /*
         *********************************************************
         *                      OPMODE START
         *********************************************************
         */

        while (!drivetrain.moveForward(20, 1000) && opModeIsActive());
        sleep(1000);
        while (!drivetrain.moveBackward(20, 1000) && opModeIsActive());
        sleep(1000);
        while (!drivetrain.turnLeft(90, 1000) && opModeIsActive());
        sleep(1000);
        while (!drivetrain.turnRight(90, 1000) && opModeIsActive());
        sleep(1000);
        while (!drivetrain.strafeLeft(20, 1000) && opModeIsActive());
        sleep(1000);
        while (!drivetrain.strafeRight(20, 1000) && opModeIsActive());
        sleep(1000);

    }

    private void initialize() {
        drivetrain = new DcExDrivetrain(hardwareMap, gamepad1, gamepad2, telemetry);
        drivetrain.initDrivetrain();
    }
}
