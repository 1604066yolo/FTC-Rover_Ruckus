package org.firstinspires.ftc.teamcode.subsystems.drivetrain;

public interface IDrivetrain {

    void move(double distance, double angle /*deg*/, double speed, boolean slowStartAndStop);

    void turn(double angle, double speed, boolean slowStartAndStop);

    void stop();

}
