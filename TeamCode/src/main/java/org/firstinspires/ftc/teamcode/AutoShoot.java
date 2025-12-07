package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.modules.MechTrain;

@Autonomous

public class AutoShoot extends LinearOpMode {
    double timeA = 4;

    private ElapsedTime timer = new ElapsedTime();

    public void runOpMode() throws InterruptedException {
        waitForStart();
        BallCannon ballCannon = new BallCannon(this);
        MechTrain mechTrain = new MechTrain(this);
        // стреляем

        double shootingTime = 3;
        double pushingTime = 1;

        mechTrain.moveBack(0.5, 9);

        ballCannon.Shoot();
        timer.reset();
        //while ((timeA > timer.seconds())) ;
        //mechTrain.moveBackRight(1,Math.sqrt(2)*48);
//            MechTrain mechTrain = new MechTrain(this);
//            mechTrain.moveBack(1, 48);
//            mechTrain.moveLeft(1, 120);
//            mechTrain.moveBack(1, 48);
    }

}

