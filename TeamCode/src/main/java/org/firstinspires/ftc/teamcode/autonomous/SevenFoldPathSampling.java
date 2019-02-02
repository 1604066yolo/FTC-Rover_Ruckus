package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.drivetrain.DcExDrivetrain;

@Autonomous
public class SevenFoldPathSampling extends LinearOpMode {

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

        //strafe to minerals
        while (!drivetrain.strafeLeft(20, 1000) && opModeIsActive());

        //check middle mineral
        if (!isFound)
            sleep(1000);
        isFound = drivetrain.isFound();
        if (isFound) markerState = markerState == -1 ? 2 : markerState;


        //move to left mineral
        while (!isFound && !drivetrain.moveBackward(12, 1000) && opModeIsActive());

        //check left mineral
        if (!isFound)
            sleep(1000);
        isFound |= drivetrain.isFound();
        if (isFound) markerState = markerState == -1 ? 1 : markerState;
        //move to right mineral
        while (!isFound && !drivetrain.moveForward(30, 1000) && opModeIsActive());

        //check front mineral
        if (!isFound)
            sleep(1000);
        isFound |= drivetrain.isFound();
        if (isFound) markerState = markerState == -1 ? 3 : markerState;
        //strafe into gold mineral
        while (isFound && !drivetrain.strafeLeft(13, 1000) && opModeIsActive());
        sleep(200);

        drivetrain.stopMotors();
        drivetrain.stopDetector();
    }

    private void initialize() {
        drivetrain = new DcExDrivetrain(hardwareMap, gamepad1, gamepad2, telemetry);
        drivetrain.initDrivetrain();
    }

}
