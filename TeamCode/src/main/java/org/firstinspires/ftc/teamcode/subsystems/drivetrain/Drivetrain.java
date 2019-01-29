package org.firstinspires.ftc.teamcode.subsystems.drivetrain;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.filters.LeviColorFilter;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.imu.BoschIMU;
import org.firstinspires.ftc.teamcode.subsystems.vision.GoldMineralDetector;

public class Drivetrain {

    HardwareMap hardwareMap;
    public Telemetry telemetry;
    Gamepad gamepad1;
    Gamepad gamepad2;


    public Servo marker;
    /*public Servo box;
    public Servo spindle;*/

   /* public DcMotor lFlip, rFlip, extend;*/

    public DcMotor frontLeft;
    public DcMotor backLeft;
    public DcMotor frontRight;
    public DcMotor backRight;

    public DcMotor rp;

    public BoschIMU imu;

    public GoldMineralDetector detector;

    public Drivetrain (HardwareMap hmap, Gamepad g1, Gamepad g2, Telemetry telemetry) {
        this.hardwareMap = hmap;
        this.telemetry = telemetry;
        this.gamepad1 = g1;
        this.gamepad2 = g2;
    }

    public void initParts() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");


        marker = hardwareMap.servo.get("marker");

        /*lFlip = hardwareMap.dcMotor.get("lFlip");
        rFlip = hardwareMap.dcMotor.get("rFlip");
        extend = hardwareMap.dcMotor.get("extend");

        lFlip.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rFlip.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        box = hardwareMap.servo.get("box");
        spindle = hardwareMap.servo.get("spindle");*/

        imu = new BoschIMU(hardwareMap.get(BNO055IMU.class, "imu"));
        imu.init();

        rp = hardwareMap.dcMotor.get("rp");
        rp.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void initDetector() {
        detector = new GoldMineralDetector();
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        detector.colorFilter = new LeviColorFilter(LeviColorFilter.ColorPreset.YELLOW);
    }

    public void startDetector() {
        detector.enable();
    }

    public void stopDetector() {
        detector.disable();
    }

    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch(InterruptedException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public void climbDown(){
        rp.setPower(-1);
        sleep(1250);
        rp.setPower(0);
        marker.setPosition(.5);
        sleep(200);
        moveBackward(8);
        sleep(200);
        strafe("left",20);
        moveForward(10);
        sleep(200);
    }

    public void bringRPDown() {
        rp.setPower(1);
        sleep(1250);
        rp.setPower(0);
    }

    public void moveForward(double inches){
        double mult = 40*0.615;
        int time = (int)(mult * inches);
        frontLeft.setPower(-.25);
        frontRight.setPower(.25);
        backRight.setPower(.25);
        backLeft.setPower(-.25);
        sleep(time);
        stopMotors();
    }

    public void moveBackward(double inches){
        double mult = 40*0.615;
        int time = (int)(mult * inches);
        frontLeft.setPower(.25);
        frontRight.setPower(-.25);
        backRight.setPower(-.25);
        backLeft.setPower(.25);
        sleep(time);
        stopMotors();
    }

    public void turnLeft(int degrees) {
        double mult = 5.5*0.615*2;
        int time = (int)(degrees * mult);
        frontLeft.setPower(.5);
        frontRight.setPower(.5);
        backLeft.setPower(.5);
        backRight.setPower(.5);
        sleep(time);
        stopMotors();
    }

    public void turnRight(int degrees) {
        double mult = 5.5*0.615*2;
        int time = (int)(degrees * mult);
        frontLeft.setPower(-.5);
        frontRight.setPower(-.5);
        backLeft.setPower(-.5);
        backRight.setPower(-.5);
        sleep(time);
        stopMotors();
    }

    public void gyroTurnLeft(int degrees) {
        imu.setCurrentPosToZero();
        double tolerance = 4;
        frontLeft.setPower(.25);
        frontRight.setPower(.25);
        backLeft.setPower(.25);
        backRight.setPower(.25);
        while (imu.getZAngle() > -degrees) {
            continue;
        }
        stopMotors();
    }

    public void gyroTurnRight(int degrees) {
        imu.setCurrentPosToZero();
        double tolerance = 4;
        frontLeft.setPower(-.25);
        frontRight.setPower(-.25);
        backLeft.setPower(-.25);
        backRight.setPower(-.25);
        while (imu.getZAngle() < degrees) {
            continue;
        }
        stopMotors();
    }

    public void stopMotors() {
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);
    }

    public void strafe(String direction, double distance){
        double mult = 75*0.615;
        double strafe;
        int time = (int)(mult * distance);
        if(direction.equals("left")){
            strafe = -.7;
        }
        else{
            strafe = .7;
        }
        frontLeft.setPower(-strafe);
        backLeft.setPower(strafe);
        frontRight.setPower(-strafe);
        backRight.setPower(strafe);
        sleep(time);
        stopMotors();
    }

    public void print(String caption, Object value) {
        telemetry.addData(caption, value);
    }

}
