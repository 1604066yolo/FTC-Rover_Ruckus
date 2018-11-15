package org.firstinspires.ftc.teamcode.subsystems.drivetrain;

public interface IDrivetrain {

    boolean move(double distance, double speed);

    boolean turn(double angle, double speed);

    void stop();

}
