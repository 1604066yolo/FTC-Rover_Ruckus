package org.firstinspires.ftc.teamcode.subsystems.drivetrain;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.filters.LeviColorFilter;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.imu.BoschIMU;
import org.firstinspires.ftc.teamcode.subsystems.vision.GoldMineralDetector;
import org.firstinspires.ftc.teamcode.util.Util;

public class DcExDrivetrain {

    private static final int END_TOLERANCE = 150;

    private static final double kp = .01, ki = .05, kd = .03;

    private final double TICKS_PER_ROTATION = 1440;
    private final double GEAR_RATIO = 2;
    private final double CIRCUMFERENCE = 4 * Math.PI;
    private double convert(double d) { return (d / CIRCUMFERENCE) * TICKS_PER_ROTATION / GEAR_RATIO; }

    private int frLastEnc, flLastEnc, brLastEnc, blLastEnc;

    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    private Gamepad gamepad1;
    private Gamepad gamepad2;
    private ElapsedTime timer = new ElapsedTime();

    private DcMotor rp;
    private DcMotorEx frontLeft, backLeft, frontRight, backRight;
    private Servo marker;

    private BoschIMU imu;

    private GoldMineralDetector detector;

    public DcExDrivetrain(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2ep, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;

        rp = hardwareMap.dcMotor.get("rp");

        marker = hardwareMap.servo.get("marker");

        imu = new BoschIMU(hardwareMap.get(BNO055IMU.class, "imu"));

        detector = new GoldMineralDetector();
    }

    public void initDrivetrain() {
        frontLeft = (DcMotorEx) hardwareMap.dcMotor.get("frontLeft");
        backLeft = (DcMotorEx) hardwareMap.dcMotor.get("frontRight");
        frontRight = (DcMotorEx) hardwareMap.dcMotor.get("backLeft");
        backRight = (DcMotorEx) hardwareMap.dcMotor.get("backRight");

        rp.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);
        frontRight.setDirection(DcMotorEx.Direction.REVERSE);
        backRight.setDirection(DcMotorEx.Direction.REVERSE);

        frontLeft.setTargetPositionTolerance(END_TOLERANCE);
        backLeft.setTargetPositionTolerance(END_TOLERANCE);
        frontRight.setTargetPositionTolerance(END_TOLERANCE);
        backRight.setTargetPositionTolerance(END_TOLERANCE);

        resetEncoders();

        imu.init();
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
    }

    public boolean strafeRight(double inches, int speed) {
        int ticks = (int) convert(inches);
        setWheelTargets(new int[] {ticks + flLastEnc, ticks + blLastEnc, -ticks + frLastEnc, -ticks + brLastEnc},
                new int[] {speed, speed, -speed, -speed});
        if (wheelsAtEndPos()) {
            updateLastEncCount();
            return true;
        }
        return false;
    }

    public boolean strafeLeft(double inches, int speed) {
        int ticks = (int) convert(inches);
        setWheelTargets(new int[] {-ticks + flLastEnc, -ticks + blLastEnc, ticks + frLastEnc, ticks + brLastEnc},
                new int[] {-speed, -speed, speed, speed});
        if (wheelsAtEndPos()) {
            updateLastEncCount();
            return true;
        }
        return false;
    }

    public boolean turnLeft(double degrees, int speed) {
        double mult = 14d;
        int ticks = (int) convert(mult * Math.toRadians(degrees));
        setWheelTargets(new int[] {-ticks + flLastEnc, -ticks + blLastEnc, -ticks + frLastEnc, -ticks + brLastEnc},
                new int[] {-speed, -speed, -speed, -speed});
        if (wheelsAtEndPos()) {
            updateLastEncCount();
            return true;
        }
        return false;
    }

    public boolean turnRight(double degrees, int speed) {
        double mult = 14d;
        int ticks = (int) convert(mult * Math.toRadians(degrees));
        setWheelTargets(new int[] {ticks + flLastEnc, ticks + blLastEnc, ticks + frLastEnc, ticks + brLastEnc},
                new int[] {speed, speed, speed, speed});
        if (wheelsAtEndPos()) {
            updateLastEncCount();
            return true;
        }
        return false;
    }

    public boolean moveBackward(double inches, int speed) {
        int ticks = (int) convert(inches);
        setWheelTargets(new int[] {-ticks + flLastEnc, ticks + blLastEnc, -ticks + frLastEnc, ticks + brLastEnc},
                new int[] {-speed, speed, -speed, speed});
        if (wheelsAtEndPos()) {
            updateLastEncCount();
            return true;
        }
        return false;
    }

    public boolean moveForward(double inches, int speed) {
        int ticks = (int) convert(inches);
        setWheelTargets(new int[] {ticks + flLastEnc, -ticks + blLastEnc, ticks + frLastEnc, -ticks + brLastEnc},
                new int[] {speed, -speed, speed, -speed});
        if (wheelsAtEndPos()) {
            updateLastEncCount();
            return true;
        }
        return false;
    }

    /**
     * Sets the target positions of all the wheel motors and starts them at a desired speed
     * @param t the target positions in order: FL, BL, FR, BR
     */
    private void setWheelTargets(int[] t, int[] v) {
        frontLeft.setTargetPosition(t[0]);
        backLeft.setTargetPosition(t[1]);
        frontRight.setTargetPosition(t[2]);
        backRight.setTargetPosition(t[3]);

        frontLeft.setVelocity(v[0]);
        backLeft.setVelocity(v[1]);
        frontRight.setVelocity(v[2]);
        backRight.setVelocity(v[3]);

        frontLeft.setMotorEnable();
        backLeft.setMotorEnable();
        frontRight.setMotorEnable();
        backRight.setMotorEnable();
    }

    private boolean wheelsAtEndPos() {
        printEncData();
        int count = 0;
        if (Util.inRange(frontLeft.getCurrentPosition(), frontLeft.getTargetPosition() - END_TOLERANCE,
                frontLeft.getTargetPosition() + END_TOLERANCE)) {
            frontLeft.setMotorDisable();
            count++;
        }
        if (Util.inRange(backLeft.getCurrentPosition(), backLeft.getTargetPosition() - END_TOLERANCE,
                backLeft.getTargetPosition() + END_TOLERANCE)) {
            backLeft.setMotorDisable();
            count++;
        }
        if (Util.inRange(frontRight.getCurrentPosition(), frontRight.getTargetPosition() - END_TOLERANCE,
                frontRight.getTargetPosition() + END_TOLERANCE)) {
            frontRight.setMotorDisable();
            count++;
        }
        if (Util.inRange(backRight.getCurrentPosition(), backRight.getTargetPosition() - END_TOLERANCE,
                backRight.getTargetPosition() + END_TOLERANCE)) {
            backRight.setMotorDisable();
            count++;
        }
        return count == 4;
    }

    private void updateLastEncCount() {
        flLastEnc = frontLeft.getCurrentPosition();
        blLastEnc = backLeft.getCurrentPosition();
        frLastEnc = frontRight.getCurrentPosition();
        brLastEnc = backRight.getCurrentPosition();
    }

    private void printEncData() {
        telemetry.addData("FL vel: ", frontLeft.getVelocity());
        telemetry.addData("FL enc: ", frontLeft.getCurrentPosition());
        telemetry.addData("FL target: ", frontLeft.getTargetPosition());
        telemetry.addData("BL vel: ", backLeft.getVelocity());
        telemetry.addData("BL enc: ", backLeft.getCurrentPosition());
        telemetry.addData("BL target: ", backLeft.getTargetPosition());
        telemetry.addData("FR vel: ", frontRight.getVelocity());
        telemetry.addData("FR enc: ", frontRight.getCurrentPosition());
        telemetry.addData("FR target: ", frontRight.getTargetPosition());
        telemetry.addData("BR vel: ", backRight.getVelocity());
        telemetry.addData("BR enc: ", backRight.getCurrentPosition());
        telemetry.addData("BR target: ", backRight.getTargetPosition());
        telemetry.update();
    }

    public void stopMotors() {
        frontLeft.setMotorDisable();
        backLeft.setMotorDisable();
        frontRight.setMotorDisable();
        backRight.setMotorDisable();
    }


    public void startDetector() {
        detector.enable();
    }

    public void stopDetector() {
        detector.disable();
    }

    public boolean isFound() {
        return detector.isFound();
    }

    public void sleep(int millis) {
        timer.reset();
        double t = timer.milliseconds();
        while (timer.milliseconds() < t + millis);
    }

    public void climbDown(){
        rp.setPower(-1);
        sleep(1250);
        rp.setPower(0);
        marker.setPosition(.5);
        /*sleep(200);
        moveBackward(8, 1000);
        sleep(200);
        strafeLeft(20, 1000);
        moveForward(10, 1000);
        sleep(200);*/
    }

    public void resetEncoders() {
        flLastEnc = frontLeft.getCurrentPosition();
        blLastEnc = backLeft.getCurrentPosition();
        frLastEnc = frontRight.getCurrentPosition();
        brLastEnc = backRight.getCurrentPosition();
    }

    public void bringRPDown() {
        rp.setPower(1);
        sleep(1250);
        rp.setPower(0);
    }

    public BoschIMU getImu() {
        return imu;
    }

    public Servo getMarker() {
        return marker;
    }

    public GoldMineralDetector getDetector() {
        return detector;
    }



}
