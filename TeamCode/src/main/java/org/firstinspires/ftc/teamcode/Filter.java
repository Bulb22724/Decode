package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Config
public class Filter {
    Servo valveServo;
    DcMotorEx godlyMotor;
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
    int numberPosition = 0;
    double t = 0;
    public static int target;
    public static int koef = 10;
    double tp = 1488;

    enum Position {B1, T3, B2, T1, B3, T2}

    Position currentPosition = Position.T3;


    public Filter(LinearOpMode opMode) {
        valveServo = opMode.hardwareMap.get(Servo.class, "valveServo");
        godlyMotor = opMode.hardwareMap.get(DcMotorEx.class, "godlyMotor");
        this.opMode = opMode;
//        fanServo.setPosition(minPosition);
        valveServo.setPosition(valveClosedPosition);
        telemetry = opMode.telemetry;
    }

    /**
     * @param numberPosition принимает номер позиции от 0 до 5
     */
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
    public void easyFilter(double powerL, double powerR){
        godlyMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        godlyMotor.setPower(powerL - powerR);
    }

    public void difficultFilter() {
        godlyMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        tp = godlyMotor.getCurrentPosition() - step;
        timer.reset();
        while ((godlyMotor.getCurrentPosition() < tp + koef || godlyMotor.getCurrentPosition() > tp - koef) && opMode.opModeIsActive() && timer.seconds() < timeA) {
            godlyMotor.setPower((tp - godlyMotor.getCurrentPosition()) / 10);
            telemetry.addData("мощность на фильтр", godlyMotor.getPower() / 10);
            telemetry.update();
        }
        t = timer.seconds();
        godlyMotor.setPower(0);
    }
//    public void left() {
////        if (fanServo.getPosition() < 0.2777777777777778) {
////            fanServo.setPosition(fanServo.getPosition() + step * 2);
////        } else if (fanServo.getPosition() == 0.2777777777777778) {
////            fanServo.setPosition(fanServo.getPosition() + step);
////        } else if (fanServo.getPosition() <= 0.5555555555555556) {
////            fanServo.setPosition(fanServo.getPosition() + step * 2);
////        } else {
////            fanServo.setPosition(0);
////        }
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
