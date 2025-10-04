package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class BallCannon {
    /* активировать моторы по кнопочке
    */
    DcMotor ShootingMotor;



    public void BallCannon(LinearOpMode opMode) {
        ShootingMotor = opMode.hardwareMap.get(DcMotor.class, "ShootingMotor");

    }
    public void Shoot(boolean g2b) {
        if (g2b){
            ShootingMotor.setPower(1);
    }
        else{
            ShootingMotor.setPower(0);
        }
    }
}