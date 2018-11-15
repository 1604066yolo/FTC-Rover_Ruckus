package org.firstinspires.ftc.teamcode.actions;

import org.firstinspires.ftc.teamcode.subsystems.drivetrain.TankDrive;

public class TankDriveAction implements IAction {

    private TankDrive drivetrain;
    private double x;
    private double y;
    private double startAngle;
    private double distance;
    private double angle;
    private double endAngle;
    private boolean endAngleIndifference;



    public TankDriveAction(TankDrive drivetrain, double x, double y, double startAngle, double distance, double angle, double endAngle, boolean endAngleIndifference) {
        this.drivetrain = drivetrain;
        this.x = x;
        this.y = y;
        this.startAngle = startAngle;
        this.distance = distance;
        this.angle = angle;
        this.endAngle = endAngle;
        this.endAngleIndifference = endAngleIndifference;
    }

    public boolean execute() {
        drivetrain.moveToPos(x, y, distance * Math.sin(angle), distance * Math.cos(angle), startAngle, endAngle, endAngleIndifference);
        return true;
    }

    public void stop() {

    }

}
