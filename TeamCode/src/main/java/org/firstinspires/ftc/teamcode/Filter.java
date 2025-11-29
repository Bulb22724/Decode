package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@Config
public class Filter {
    Servo valveServo;
    Servo fanServo;
    LinearOpMode opMode;
    public static double valveOpenPosition;
    public static double valveClosedPosition;
    boolean isValveOpen = false;
    public static double maxPosition;
    public static double minPosition;
    double step=(maxPosition-minPosition)/6;
    int numberPosition = 0;
    enum Position{B1, T3, B2, T1, B3, T2}
    Position currentPosition = Position.T3;


    public Filter(LinearOpMode opMode) {
        valveServo = opMode.hardwareMap.get(Servo.class, "valveServo");
        fanServo = opMode.hardwareMap.get(Servo.class, "fanServo");
        this.opMode = opMode;
        fanServo.setPosition(minPosition);
        valveServo.setPosition(valveClosedPosition);
    }

    /**
     * @param numberPosition принимает номер позиции от 0 до 5
     */
    public void setPosition(int numberPosition) {
        fanServo.setPosition(minPosition + (numberPosition) * step);
    }

    public void valveOn() {
        if (!isValveOpen) {
            valveServo.setPosition(valveOpenPosition);
            isValveOpen = true;
        }
    }

    public void vaveOff() {
        if (isValveOpen) {
            valveServo.setPosition(valveClosedPosition);
            isValveOpen = false;
        }
    }

    public void nextPosition() {
        if (numberPosition < 5) {
            numberPosition += 1;
            fanServo.setPosition(minPosition + numberPosition * step);
        }
    }

    public void previousPosition() {
        if (numberPosition > 0) {
            numberPosition -= 1;
            fanServo.setPosition(minPosition + numberPosition * step);
        }
    }

    public void addData() {
        Telemetry telemetry = opMode.telemetry;
        telemetry.addData("номер позиции барабана", numberPosition + 1);
        telemetry.addData("", "Позиция барабана:макс %.2f мин %.2f текущая %.2f", maxPosition, minPosition, fanServo.getPosition());
        telemetry.addData("", "Позиция заслонки:открыто %.2f закрыто %.2f текущая %.2f", valveOpenPosition, valveClosedPosition, valveServo.getPosition());
        telemetry.addData("заслонка открыта", isValveOpen);

    }
//    public void leapNextPosition() {
//        if(currentPosition == Position.B1){
//            setPosition(Position.B2.ordinal());
//        }
//        else if(currentPosition == Position.B2){
//            setPosition(Position.B3.ordinal());
//        }
//        else if(currentPosition == Position.B3){
//            setPosition(Position.T1.ordinal());
//        }
//        else if(currentPosition == Position.T1){
//            setPosition(Position.T2.ordinal());
//        }
//        else if(currentPosition == Position.T2){
//            setPosition(Position.T3.ordinal());
//        }
//    }


}
