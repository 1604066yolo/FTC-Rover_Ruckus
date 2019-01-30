package org.firstinspires.ftc.teamcode.autonomous.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.drivetrain.DcExDrivetrain;

@Autonomous
public class VisionTest extends LinearOpMode {

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

        drivetrain.startDetector();

        while (opModeIsActive()) {
            telemetry.addData("isFound: ", drivetrain.isFound());
            telemetry.addData("xPos: ", drivetrain.getDetector().getScreenPosition().x);
            telemetry.addData("area: ", drivetrain.getDetector().getFoundRect().area());
            telemetry.update();
        }

        drivetrain.stopDetector();
    }

    private void initialize() {
        drivetrain = new DcExDrivetrain(hardwareMap, gamepad1, gamepad2, telemetry);
        drivetrain.initDrivetrain();
    }

}
