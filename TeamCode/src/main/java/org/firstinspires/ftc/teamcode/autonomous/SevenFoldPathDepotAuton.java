package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.drivetrain.DcExDrivetrain;

@Autonomous
public class SevenFoldPathDepotAuton extends LinearOpMode {

    private DcExDrivetrain drivetrain;

    private boolean isFound = false;

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
        while (!drivetrain.strafeLeft(15, 1000) && opModeIsActive());

        //check middle mineral
        if (!isFound)
            sleep(1000);
        isFound = drivetrain.isFound();

        //move to left mineral
        while (!isFound && !drivetrain.moveBackward(15, 1000) && opModeIsActive());

        //check back mineral
        if (!isFound)
            sleep(1000);
        isFound |= drivetrain.isFound();

        //move to right mineral
        while (!isFound && !drivetrain.moveForward(30, 1000) && opModeIsActive());

        //check front mineral
        if (!isFound)
            sleep(1000);
        isFound |= drivetrain.isFound();

        //strafe into gold mineral
        while (isFound && !drivetrain.strafeLeft(5, 1000) && opModeIsActive());
        sleep(200);
        while (isFound && !drivetrain.strafeRight(5, 1000) && opModeIsActive());

        //go to depot
        while (!drivetrain.turnRight(180, 1000) && opModeIsActive());
        sleep(200);
        while (!drivetrain.strafeRight(20, 1000) && opModeIsActive());
        sleep(200);

        //deposit marker
        drivetrain.getMarker().setPosition(0);

        drivetrain.stopMotors();
        drivetrain.stopDetector();
    }

    private void initialize() {
        drivetrain = new DcExDrivetrain(hardwareMap, gamepad1, gamepad2, telemetry);
        drivetrain.initDrivetrain();
    }

}
