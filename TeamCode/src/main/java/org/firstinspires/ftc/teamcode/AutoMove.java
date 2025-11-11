package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.modules.MechTrain;

@Autonomous
@Config
public class AutoMove extends LinearOpMode {
    public static double tP = 18;

    public void runOpMode() {
        waitForStart();
        MechTrain mechTrain = new MechTrain(this);
        mechTrain.moveBack(0.5,tP);
        }

}

