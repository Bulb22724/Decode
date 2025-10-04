package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ballCannon  {
    /* активировать моторы по кнопочке
    */
    DcMotor ShootingMotor;

    public void ballCannon(LinearOpMode opMode) {
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