package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

@Autonomous
public class WebcamLimelightTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        while (!opModeIsActive());

        WebcamName cam = hardwareMap.get(WebcamName.class, "Webcam 1");
        cam.
    }
}