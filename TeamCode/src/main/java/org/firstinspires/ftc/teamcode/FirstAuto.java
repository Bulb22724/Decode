package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.modules.MechTrain;
@Autonomous
public class FirstAuto extends LinearOpMode{
    private ElapsedTime timer = new ElapsedTime();

    public void runOpMode(){
        MechTrain MT = new MechTrain(this);
        double  power = 0.1;
        waitForStart();
            timer.reset();

            double durationForDriveNs = 5e9;
            double durationForShootNs = 1e9;
            double durationForRotationNs = 2e9;
            while ((timer.nanoseconds()  < durationForDriveNs) && opModeIsActive()){
                MT.setPowerOnMecanumBase(0, power, 0);
            }
            MT.setPowerOnMecanumBase(0, 0, 0);
            timer.reset();

            while ((timer.nanoseconds()  < durationForRotationNs) && opModeIsActive()){
                MT.setPowerOnMecanumBase(0, 0, power);
            }
            MT.setPowerOnMecanumBase(0, 0, 0);
            timer.reset();

            while ((timer.nanoseconds()  < durationForShootNs) && opModeIsActive()){

            }

        }

    }

