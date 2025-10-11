package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class BallCannon {
    /* активировать моторы по кнопочке
     */
    DcMotor shootingMotor;

    boolean switchMotor = false;
    boolean protectionClampedButton = false;

    boolean altButton = false;

    //    public void setAltButton(&& altB){
//        a
//    }

    /**
     * HardwareMap это карта устройств
     *
     * BallCannon это конструктор который принимает как аргумент карту устройств
     * @param hard
     */
    public BallCannon(HardwareMap hard) {
        shootingMotor = hard.get(DcMotor.class, "shootingMotor");
        shootingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

    }
    /** Метод для вращения мотора пушки по часовой на максимальной мощиности
     *
      */
    public void rotate() {
        shootingMotor.setPower(1);
    }

    /**
     * метод для остаановки мотора
     */
    public void stop() {
        shootingMotor.setPower(0);
    }

    /**
     * метод для вращения мотора против часовой на максимальной мощиности
     */
    public void inverse() {
        shootingMotor.setPower(-1);
    }


}