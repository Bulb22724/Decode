package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.modules.MechTrain;

@Autonomous

public class AutoMove extends LinearOpMode {
    public void runOpMode() {
        waitForStart();
        MechTrain mechTrain = new MechTrain(this);
        mechTrain.moveBack(0.5,18);
        }

}

