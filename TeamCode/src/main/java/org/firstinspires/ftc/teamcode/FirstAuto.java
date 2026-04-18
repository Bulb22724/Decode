package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.modules.MechTrain;
@Autonomous
public class FirstAuto extends LinearOpMode{
    MechTrain mechTrain = new MechTrain(this);
    double tp = 16;


    public void runOpMode(){
        mechTrain.rideTicPID(tp);
        }
    }

