package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.MechTrain;

@Autonomous

public class Blue1ShotB6toF1 extends LinearOpMode {

    public void runOpMode() {
        MechTrain mechTrain = new MechTrain(this);
        waitForStart();
        // стреляем
        //mechTrain.moveBackRight(1,Math.sqrt(2)*48);
        mechTrain.moveBack(1, 48);
        mechTrain.moveLeft(1, 120);
        mechTrain.moveBack(1, 48);

    }

}
