package org.firstinspires.ftc.teamcode.util;

public class Util {

    public static double roundTo2D(double d) {
        return .01d * Math.round(d * 100);
    }

    public static boolean inRange(double val, double a, double b) {
        return val > a && val < b || val > b && val < a;
    }

}
