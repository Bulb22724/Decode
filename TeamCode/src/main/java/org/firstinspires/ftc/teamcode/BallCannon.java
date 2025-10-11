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
    public BallCannon(HardwareMap hard) {
        shootingMotor = hard.get(DcMotor.class, "shootingMotor");
        shootingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

    }

    public void rotate() {
        shootingMotor.setPower(1);
    }

    public void stop() {
        shootingMotor.setPower(0);
    }

    public void inverse() {
        shootingMotor.setPower(-1);
    }


}