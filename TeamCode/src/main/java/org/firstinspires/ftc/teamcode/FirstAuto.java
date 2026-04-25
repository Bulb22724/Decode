package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.ftc.FTCCoordinates;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.modules.MechTrain;
@Autonomous
public class FirstAuto extends LinearOpMode{
    public static double tp = 660;

    public void runOpMode(){
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        MechTrain mechTrain = new MechTrain(this);
        telemetry.addData("ошибка", 0);
        telemetry.addData("p", 0);
        telemetry.addData("i", 0);

        telemetry.update();

        waitForStart();


        mechTrain.rideTicPID(tp);
        }
    }

