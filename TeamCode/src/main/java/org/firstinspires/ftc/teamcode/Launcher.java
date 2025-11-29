package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.ftc.localization.localizers.DriveEncoderLocalizer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Mat;

@Config
public class Launcher {
    DcMotorEx shootingMotor;
    Servo ballPushingServo;
    LinearOpMode opMode;

    public static double motorPower = 0.75;
    public boolean isMotorOn = false;
    public static double pushPos = 0.3;
    public static double closePos = 0;
    public static double rotationtime = 0.84;
    double delay = rotationtime * Math.abs(closePos - pushPos);
    public static double shootTime = 2;

    public void launcher(LinearOpMode opMode) {
        shootingMotor = opMode.hardwareMap.get(DcMotorEx.class, "shootingMotor");
        shootingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ballPushingServo = opMode.hardwareMap.get(Servo.class, "ballPushingServo");
        this.opMode = opMode;
    }

    public void shootOn() {
        shootingMotor.setPower(motorPower);
        isMotorOn = true;
    }

    public void shootOff() {
        shootingMotor.setPower(0);
        isMotorOn = false;
    }

    public void pushServo() {
        ballPushingServo.setPosition(pushPos);
    }

    public void closeServo() {
        ballPushingServo.setPosition(closePos);
    }

    // Метод толкает и возвращает серву
    public void autoLaunch() {
        shootOn();
        ElapsedTime timer = new ElapsedTime();
        while (opMode.opModeIsActive() && timer.seconds() < shootTime);
        pushServo();
        while (opMode.opModeIsActive() && timer.seconds() < delay) ;
        closeServo();
        shootOff();
    }

    public void addData() {
        Telemetry telemetry = opMode.telemetry;
        telemetry.addData("Текущая позиция сервы", ballPushingServo.getPosition());
        telemetry.addData("Позиция сервы для запуска", pushPos);
        telemetry.addData("Позиция закрытия сервы", closePos);
        telemetry.addData("Время для разгона", shootTime);
        telemetry.addData("Мощность мотора запуска", motorPower);
        telemetry.addData("Время для вращения сервы", "%3.2f %3.2f", rotationtime, delay);

    }
}
