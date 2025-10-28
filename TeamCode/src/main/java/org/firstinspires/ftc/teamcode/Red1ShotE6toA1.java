package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.MechTrain;

@Autonomous

public class Red1ShotE6toA1 extends LinearOpMode {

    public void runOpMode() {
        MechTrain mechTrain = new MechTrain(this);
        waitForStart();
        // стреляем
        //mechTrain.moveBackRight(1,Math.sqrt(2)*48);
        mechTrain.moveBack(1, 48);
        mechTrain.moveRight(1, 120);
        mechTrain.moveBack(1, 48);

    }

}
