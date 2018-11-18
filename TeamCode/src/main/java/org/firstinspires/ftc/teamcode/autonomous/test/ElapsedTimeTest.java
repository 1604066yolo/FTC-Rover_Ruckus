package org.firstinspires.ftc.teamcode.autonomous.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(group = "test", name = "TEST ElapsedTime")
public class ElapsedTimeTest extends OpMode {

    private DcMotor r;
    private DcMotor l;

    private ElapsedTime timer;
    private double lastTime = 0;

    int step = 0;

    @Override
    public void init() {
        r = hardwareMap.dcMotor.get("right");
        l = hardwareMap.dcMotor.get("left");

        l.setDirection(DcMotorSimple.Direction.REVERSE);

        timer = new ElapsedTime();
    }

    @Override
    public void start() {
        super.start();
        timer.reset();
    }

    @Override
    public void loop() {
        if (step == 0 && driveForward(10, .5, timer))
            step++;
        else if (step == 1 && driveForward(10, .75, timer))
            step++;
        else if (step == 2 && driveForward(10, 1, timer))
            step++;
        else stopMotors();
    }

    @Override
    public void stop() {
        stopMotors();
    }

    public boolean driveForward(double distance, double speed, ElapsedTime timer) {
        int time = (int) (distance * 25 / speed + lastTime);
        r.setPower(speed);
        l.setPower(speed);
        if (timer.milliseconds() < time)
            return false;
        lastTime += timer.milliseconds();
        stopMotors();
        return true;
    }

    public void stopMotors() {
        r.setPower(0);
        l.setPower(0);
    }
}
