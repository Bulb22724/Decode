package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;

@Config
public class PID{

    public  static  double kp = 0.03;
    public static double ki = 0.01;
    public  static double kd = 0;
    public static  double kf = 0;

    double p = 0;
    double i = 0;
    double d = 0;
    double f = 0;
    double e = 0;
    double elast = 0;
    double u = 0;
    double minU = 0.2;
    double tp = 0;
    double tDiscr = 0.25;
    public void setTargetPosition(double tp) {this.tp = tp;}
    public double getPower(double curPos) {
        e = tp - curPos;
        p = kp*e;
//        i += ki*e*tDiscr;
        u = p+i+d;
        if (Math.abs(u) < minU) {u = 0;}

        return u;
    }
}

