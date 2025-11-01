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
        double motorPower = 1;
        if (opModeIsActive()) {
            // стреляем
            ballCannon.inverse(motorPower);
            double durationForPushingBallNs = 3.5e9;
            double durationForShootingBallNs = 3e9;
            double durationForPushingBalls = 1e9;
            timer.reset();
            ballCannon.controlShootingMotor(true);
            ballCannon.controlShootingMotor(true);
            while (!(durationForShootingBallNs <timer.nanoseconds())) {
            }
            ballCannon.controlBallPushingMotor(true);
            while (!(durationForPushingBallNs < timer.nanoseconds())) {
            }
            ballCannon.controlBallPushingMotor(false);
            ballCannon.controlShootingMotor(true);
            //mechTrain.moveBackRight(1,Math.sqrt(2)*48);
            MechTrain mechTrain = new MechTrain(this);
            mechTrain.moveBack(1, 48);
            mechTrain.moveLeft(1, 120);
            mechTrain.moveBack(1, 48);
        }

    }

}
