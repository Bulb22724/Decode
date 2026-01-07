package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.AprilTagWebcam;

@Autonomous
public class AutoAprilTagDetection extends LinearOpMode {
    AprilTagWebcam aprilTagWebcam;
    public void runOpMode() throws InterruptedException {
        aprilTagWebcam = new AprilTagWebcam(this);
        waitForStart();
        while (opModeIsActive()){
            aprilTagWebcam.addData();
            telemetry.addLine(String.format("Текущая последовательность %s", aprilTagWebcam.getColorSequense()));
            telemetry.update();
        }
        aprilTagWebcam.stop();

    }
}
