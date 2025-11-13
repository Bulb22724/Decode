package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.modules.MechTrain;

@Autonomous

public class AutoShoot extends LinearOpMode {
    private ElapsedTime timer = new ElapsedTime();

    public void runOpMode() {
        waitForStart();
        BallCannon ballCannon = new BallCannon(this);
        MechTrain mechTrain = new MechTrain(this);
        // стреляем

        double shootingTime = 3;
        double pushingTime = 1;

        //отезжаем назад для занимания огневой позиции
        mechTrain.moveBack(0.5, 18);
        //раскручивам колесо для стрельбы
        ballCannon.rotateShootingMotor();
        timer.reset();
        while (shootingTime > timer.seconds() && opModeIsActive()) {
        }
        //раскручивам колесо для подачи шариков
        ballCannon.rotatePushingMotor();

        timer.reset();
        while (pushingTime > timer.seconds() && opModeIsActive()) {
        }
        //останавливаем оба колеса
        ballCannon.stopShootingMotor();
        ballCannon.stopPushingMotor();
        //mechTrain.moveBackRight(1,Math.sqrt(2)*48);
//            MechTrain mechTrain = new MechTrain(this);
//            mechTrain.moveBack(1, 48);
//            mechTrain.moveLeft(1, 120);
//            mechTrain.moveBack(1, 48);
    }

}

