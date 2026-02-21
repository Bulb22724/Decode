package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.modules.MechTrain;

@Config
public class Filter {
    Servo valveServo;
    DcMotorEx godlyMotor;
    Servo rotateServo;
    MechTrain mechTrain;
    BallCannon ballCannon;
    LinearOpMode opMode;
    private ElapsedTime timer = new ElapsedTime();
    public static double valveOpenPosition = 0;

    public static double valveClosedPosition = 1;
    public static double timeA = 1;
    boolean isValveOpen = false;
    public static double maxPosition = 1;
    public static double minPosition = 0;
    public static int step = 96; //step=(maxPosition-minPosition)/6;
    public static double funPosition = 0.4;
    public static double k = 5;
    int numberPosition = 0;
    double t = 0;
    /*
    timeRecycling сколько времени после достижения цели будет работать мотор
     */
    public static double timeRecycling = 0.2;
    public static int target;
    public static int koef = 1;
    double tp = 1488;

    enum Position {B1, T3, B2, T1, B3, T2}

    Position currentPosition = Position.T3;


    public Filter(LinearOpMode opMode) {
        valveServo = opMode.hardwareMap.get(Servo.class, "valveServo");
        rotateServo = opMode.hardwareMap.get(Servo.class, "rotateServo");
        godlyMotor = opMode.hardwareMap.get(DcMotorEx.class, "godlyMotor");
        this.opMode = opMode;
//        fanServo.setPosition(minPosition);
        valveServo.setPosition(valveClosedPosition);
        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(), opMode.telemetry);
        double angle = rotateServo.getPosition();
        rotateServo.setPosition(angle);
        mechTrain = new MechTrain(opMode);
    }
//    public void setPosition(int numberPosition) {
//        fanServo.setPosition(minPosition + (numberPosition) * step);
//    }
//
//    public void setPosition(Position position) {
//        fanServo.setPosition(minPosition + position.ordinal() * step);
//    }
//
//    public Position getPosition() {
//        return Position.values()[(int) ((fanServo.getPosition() - minPosition) / step)];
//    }

    public void valveOn() {
        if (!isValveOpen) {
            valveServo.setPosition(valveOpenPosition);
            isValveOpen = true;
        }
    }

    public void valveOff() {
        if (isValveOpen) {
            valveServo.setPosition(valveClosedPosition);
            isValveOpen = false;
        }
    }

    public void autoFilter(double powerL, double powerR, double time) {
        godlyMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        timer.reset();
        while (time > timer.seconds() && opMode.opModeIsActive()) {
           godlyMotor.setPower(powerL - powerR);
        }
        godlyMotor.setPower(0);
    }

    public void setPowerFilter(double powerL, double powerR) {
        godlyMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        godlyMotor.setPower(powerL - powerR);
    }

    public void nextPosition(double pecentageTurnLenght) {
        godlyMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        tp = godlyMotor.getCurrentPosition() - step * pecentageTurnLenght;
        timer.reset();
        while ((godlyMotor.getCurrentPosition() > tp + koef || godlyMotor.getCurrentPosition() < tp - koef) && opMode.opModeIsActive() && timeA > timer.seconds()) {
            godlyMotor.setPower((tp - godlyMotor.getCurrentPosition()) / k);
            telemetry.addData("мощность на фильтр", godlyMotor.getPower());
            telemetry.update();
        }
        t = timer.seconds();
        while (opMode.opModeIsActive() && timeRecycling + t > timer.seconds()) {
            godlyMotor.setPower((tp - godlyMotor.getCurrentPosition()) / k);
        }
        while (((tp > godlyMotor.getCurrentPosition()) || tp < godlyMotor.getCurrentPosition()) && opMode.opModeIsActive()) {
            godlyMotor.setPower((tp - godlyMotor.getCurrentPosition()) / k);
        }
        t = timer.seconds();
        while (opMode.opModeIsActive() && timeRecycling + t > timer.seconds()) {
            godlyMotor.setPower((tp - godlyMotor.getCurrentPosition()) / k);
        }
        t = timer.seconds();
        godlyMotor.setPower(0);

    }

//    public void left() {

    /// /        if (fanServo.getPosition() < 0.2777777777777778) {
    /// /            fanServo.setPosition(fanServo.getPosition() + step * 2);
    /// /        } else if (fanServo.getPosition() == 0.2777777777777778) {
    /// /            fanServo.setPosition(fanServo.getPosition() + step);
    /// /        } else if (fanServo.getPosition() <= 0.5555555555555556) {
    /// /            fanServo.setPosition(fanServo.getPosition() + step * 2);
    /// /        } else {
    /// /            fanServo.setPosition(0);
    /// /        }
//    }
    public void fun() {
//        fanServo.setPosition(funPosition);
    }


    public void addData() {
        telemetry.addData("номер позиции барабана", numberPosition + 1);
//        telemetry.addData("", "Позиция барабана:макс %.2f мин %.2f текущая %.2f", maxPosition, minPosition, fanServo.getPosition());
        telemetry.addData("", "Позиция заслонки:открыто %.2f закрыто %.2f текущая %.2f", valveOpenPosition, valveClosedPosition, valveServo.getPosition());
        telemetry.addData("заслонка открыта", isValveOpen);
        telemetry.addData("Позиция сервы фильтра", godlyMotor.getCurrentPosition());
        telemetry.addData("нужная позиция сервы", tp);
        telemetry.addData("Время поворота", t);
    }

    public void leapNextPosition() {

    }


}
