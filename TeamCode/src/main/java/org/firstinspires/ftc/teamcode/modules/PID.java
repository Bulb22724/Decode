package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class PID extends LinearOpMode {
    MechTrain mechTrain = new MechTrain(this);

    public  static  double kp = 0.25;
    public static double ki = 0;
    public  static double kd = 0;
    public static  double kf = 0;

    double p = 0;
    double i = 0;
    double d = 0;
    double f = 0;
    double e = 0;
    double elast = 0;
    double u = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        p = kp * e;
        u = p;

    }
}

