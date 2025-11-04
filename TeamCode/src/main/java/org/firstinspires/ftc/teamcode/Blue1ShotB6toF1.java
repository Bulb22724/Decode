package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.modules.MechTrain;

@Autonomous

public class Blue1ShotB6toF1 extends LinearOpMode {
    private ElapsedTime timer = new ElapsedTime();

    public void runOpMode() {
        waitForStart();
        BallCannon ballCannon = new BallCannon(hardwareMap);
        MechTrain mechTrain = new MechTrain(this);
        if (opModeIsActive()) {
            // стреляем
            
            double ShootingTime = 3;
            double pushingTime = 1;

            mechTrain.moveBack(0.5,18);
            
            ballCannon.rotate(0.75);
            timer.reset();
            while (ShootingTime > timer.seconds() && opModeIsActive()) {
            }
            ballCannon.controlBallPushingMotor(false, true);

            timer.reset();
            while (pushingTime > timer.seconds() && opModeIsActive()) {
            }
            ballCannon.controlBallPushingMotor(true, false);
            timer.reset();
            while (pushingTime > timer.seconds() && opModeIsActive()) {
            }
            ballCannon.controlBallPushingMotor(false, false);
            ballCannon.stop();
            //mechTrain.moveBackRight(1,Math.sqrt(2)*48);
//            MechTrain mechTrain = new MechTrain(this);
//            mechTrain.moveBack(1, 48);
//            mechTrain.moveLeft(1, 120);
//            mechTrain.moveBack(1, 48);
        }
    }

}

