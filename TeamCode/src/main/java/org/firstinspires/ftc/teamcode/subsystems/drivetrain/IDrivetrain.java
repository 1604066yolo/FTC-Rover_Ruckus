package org.firstinspires.ftc.teamcode.subsystems.drivetrain;

public interface IDrivetrain {

    boolean move(double distance, double angle /*deg*/, double speed, boolean slowStartAndStop);

    boolean rotate(double angle, double speed, boolean slowStartAndStop);

    void stop();

}
