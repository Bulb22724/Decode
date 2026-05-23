package org.firstinspires.ftc.teamcode.modules;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static java.lang.Math.abs;
import static java.lang.Math.signum;

import android.graphics.Path;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class PID{
    LinearOpMode opMode;
    private ElapsedTime timer = new ElapsedTime();
    public  static  double kp = 0.03;
    public static double ki = 0;
    public  static double kd = 0;
    public static  double kf = 0;
    double tNow = 0;
    double tLast = 0;
    double p = 0;
    double i = 0;
    double d = 0;
    double f = 0;
    double e = 0;
    double elast = 0;
    double u = 0;
    double minU = 0.2;
    double maxI = 20;
    double tp = 0;
    double tDiscr = 0.25;
    public PID(LinearOpMode opMode){
        this.opMode = opMode;
        telemetry = opMode.telemetry;
    }
    public void setTargetPosition(double tp) {this.tp = tp;}
    public double getPower(double curPos) {

        tNow = timer.seconds();
        tDiscr = tNow - tLast;
        tLast = tNow;

        e = tp - curPos;

        telemetry.addData("ошибка", e);


        p = kp*e;
        i += ki*e*tDiscr;
        telemetry.addData("p", p);
        telemetry.addData("i", i);

        if (abs(i) > maxI) {i = maxI * signum(i);}

        u = p+i+d;
        if (abs(u) < minU) {u = 0;}

        return u;
    }
}

