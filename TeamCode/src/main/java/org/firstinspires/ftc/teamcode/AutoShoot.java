package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.modules.AprilTagWebcam;
import org.firstinspires.ftc.teamcode.modules.MechTrain;

@Autonomous
@Config
public class AutoShoot extends LinearOpMode {
    double timeA = 1;
    public static double k = 1.7;
    public static double k2 = 0.8;
    public static double timeB = 0.41;
    private ElapsedTime timer = new ElapsedTime();

    public void runOpMode() throws InterruptedException {
        waitForStart();
        BallCannon ballCannon = new BallCannon(this);
        MechTrain mechTrain = new MechTrain(this);
        Filter filter = new Filter(this);
        AprilTagWebcam camera = new AprilTagWebcam((this));
        // стреляем


        double shootingTime = 3;
        double pushingTime = 1;
        mechTrain.moveBack(0.45, 7); //0.5

        ballCannon.Shoot();
        timer.reset();
        while (timeA > timer.seconds() && opModeIsActive());
        filter.autoFilter(-0.4, 0, timeB);
        ballCannon.Shoot();
        timer.reset();
        while (timeA > timer.seconds() && opModeIsActive());
        filter.autoFilter(-0.4, 0, timeB);

        ballCannon.Shoot();
        timer.reset();
        while (timeA > timer.seconds() && opModeIsActive());

        //mechTrain.moveBack(0.45, 7); //0.5
//        timer.reset();
//        while (timeA > timer.seconds());
//        AprilTagWebcam.ColorSequense colors = camera.getColorSequense();
//        switch (camera.getColorSequense()) {
//            case GPP:
//                filter.autoFilter(-0.4, 0, timeB * k);
//                ballCannon.Shoot();
//                timer.reset();
//                while (timeA > timer.seconds() && opModeIsActive()) ;
//                filter.autoFilter(-0.4, 0, timeB);
//                ballCannon.Shoot();
//                timer.reset();
//                while (timeA > timer.seconds() && opModeIsActive()) ;
//                filter.autoFilter(-0.4, 0, timeB);
//                ballCannon.Shoot();
//                timer.reset();
//                while (timeA > timer.seconds() && opModeIsActive()) ;
//                break;
//
//            case PGP:
//                filter.autoFilter(-0.4, 0, timeB * 0.77);
//                ballCannon.Shoot();
//                timer.reset();
//                while (timeA > timer.seconds() && opModeIsActive()) ;
//                filter.autoFilter(-0.4, 0, timeB);
//                ballCannon.Shoot();
//                timer.reset();
//                while (timeA > timer.seconds() && opModeIsActive()) ;
//                filter.autoFilter(-0.4, 0, timeB);
//                ballCannon.Shoot();
//                timer.reset();
//                while (timeA > timer.seconds() && opModeIsActive()) ;
//                break;
//            case PPG:
//                filter.autoFilter(-0.4, 0, timeB * k2);
//                ballCannon.Shoot();
//                timer.reset();
//                while (timeA > timer.seconds() && opModeIsActive()) ;
//                filter.autoFilter(+0.4, 0, timeB);
//                ballCannon.Shoot();
//                timer.reset();
//                while (timeA > timer.seconds() && opModeIsActive()) ;
//                filter.autoFilter(+0.4, 0, timeB);
//                ballCannon.Shoot();
//                timer.reset();
//                while (timeA > timer.seconds() && opModeIsActive());
//                break;
//            case NOTFOUND:
//                filter.autoFilter(-0.4, 0, timeB * k2);
//                ballCannon.Shoot();
//                timer.reset();
//                while (timeA > timer.seconds() && opModeIsActive()) ;
//                filter.autoFilter(+0.4, 0, timeB);
//                ballCannon.Shoot();
//                timer.reset();
//                while (timeA > timer.seconds() && opModeIsActive()) ;
//                filter.autoFilter(+0.4, 0, timeB);
//                ballCannon.Shoot();
//                timer.reset();
//        }
//        ballCannon.Shoot();
//        timer.reset();
//        while (timeA > timer.seconds() && opModeIsActive()) ;
//        filter.autoFilter(-0.4, 0, timeB);
//        ballCannon.Shoot();
//        timer.reset();
//        while (timeA > timer.seconds() && opModeIsActive()) ;
//        filter.autoFilter(-0.4, 0, timeB);
//
//        ballCannon.Shoot();
//        timer.reset();
//        while (timeA > timer.seconds() && opModeIsActive()) ;

        //mechTrain.moveBackRight(1,Math.sqrt(2)*48);
//            MechTrain mechTrain = new MechTrain(this);
//            mechTrain.moveBack(1, 48);
//            mechTrain.moveLeft(1, 120);
//            mechTrain.moveBack(1, 48);
    }

}

