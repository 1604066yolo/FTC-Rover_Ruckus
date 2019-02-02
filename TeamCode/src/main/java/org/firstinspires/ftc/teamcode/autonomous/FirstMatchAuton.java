package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.drivetrain.DcExDrivetrain;

@Autonomous
public class FirstMatchAuton extends LinearOpMode {

    private DcExDrivetrain drivetrain;

    private boolean isFound = false;
    private int markerState = -1;

    @Override
    public void runOpMode() {
        initialize();

        waitForStart();

        /*
         *********************************************************
         *                      OPMODE START
         *********************************************************
         */

        //start gold mineral detector
        drivetrain.startDetector();

        //climb down and unlatch
        drivetrain.climbDown();
        while (!drivetrain.moveBackward(5, 1000) && opModeIsActive());
        sleep(200);
        while (!drivetrain.strafeLeft(5, 1000) && opModeIsActive());
        sleep(200);
        while (!drivetrain.moveForward(5, 1000) && opModeIsActive());

        //straighten using gyro
        double angle = drivetrain.getImu().getZAngle();
        while (!(angle > 0 ? drivetrain.turnLeft(angle, 300) : drivetrain.turnRight(-angle, 300)) && opModeIsActive());
        sleep(200);

        while (!drivetrain.strafeLeft(15, 1000) && opModeIsActive());
        sleep(200);

        while (!drivetrain.turnRight(190, 1000) && opModeIsActive());
        sleep(200);

        while (!drivetrain.moveForward(40, 1000) && opModeIsActive());
        sleep(200);

        while (!drivetrain.turnRight(40, 1000) && opModeIsActive());
        sleep(200);

        while (!drivetrain.strafeRight(60, 1000) && opModeIsActive());
        sleep(200);

        drivetrain.getMarker().setPosition(0);

        //strafe off of the marker and lift marker mechanism up
        while (!drivetrain.strafeLeft(9, 1000) && opModeIsActive());
        drivetrain.getMarker().setPosition(.5);
        sleep(500);

        drivetrain.stopMotors();
        drivetrain.stopDetector();
    }

    private void initialize() {
        drivetrain = new DcExDrivetrain(hardwareMap, gamepad1, gamepad2, telemetry);
        drivetrain.initDrivetrain();
    }

}
