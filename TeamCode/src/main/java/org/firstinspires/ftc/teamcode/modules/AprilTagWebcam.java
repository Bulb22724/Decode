package org.firstinspires.ftc.teamcode.modules;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.List;


//@Autonomous
public class AprilTagWebcam {

    private AprilTagProcessor apriltagProcessor;
    private VisionPortal visionPortal;
    private Telemetry telemetry;

    public AprilTagWebcam(LinearOpMode opMode) {
        this.telemetry = opMode.telemetry;
        apriltagProcessor = new AprilTagProcessor.Builder()
                .setDrawCubeProjection(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES)
                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(opMode.hardwareMap.get(WebcamName.class, "Webcam 1"));
        builder.setCameraResolution(new Size(640, 480));
        builder.addProcessor(apriltagProcessor);

        visionPortal = builder.build();

    }

    public void displayDetectionTelemetry(AprilTagDetection detectedId) {
        if (detectedId == null) {
            return;
        }
        if (detectedId.metadata != null) {
            telemetry.addLine(String.format("\n==== (ID %d) %s", detectedId.id, detectedId.metadata.name));
            telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (cm", detectedId.ftcPose.x, detectedId.ftcPose.y, detectedId.ftcPose.z));
            telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detectedId.ftcPose.pitch, detectedId.ftcPose.roll, detectedId.ftcPose.yaw));
            telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (cm, deg, deg)", detectedId.ftcPose.range, detectedId.ftcPose.bearing, detectedId.ftcPose.elevation));
        } else {
            telemetry.addLine(String.format("\n==== (ID %d) Unknown", detectedId.id));
            telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detectedId.center.x, detectedId.center.y));
        }
    }

    public enum ColorSequense {GPP, PGP, PPG, NOTFOUND}

    public ColorSequense getColorSequense() {
        List<AprilTagDetection> detectedTags = apriltagProcessor.getDetections();
        for (AprilTagDetection detection : detectedTags) {
            switch (detection.id) {
                case 21:
                    return ColorSequense.GPP;
                case 22:
                    return ColorSequense.PGP;
                case 23:
                    return ColorSequense.PPG;
            }
        }
        return ColorSequense.NOTFOUND;
    }
    public void addData(){
        List<AprilTagDetection> detectedTags = apriltagProcessor.getDetections();
        for (AprilTagDetection detectedId : detectedTags) {
            if (detectedId.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", detectedId.id, detectedId.metadata.name));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (cm", detectedId.ftcPose.x, detectedId.ftcPose.y, detectedId.ftcPose.z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detectedId.ftcPose.pitch, detectedId.ftcPose.roll, detectedId.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (cm, deg, deg)", detectedId.ftcPose.range, detectedId.ftcPose.bearing, detectedId.ftcPose.elevation));
            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detectedId.id));
                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detectedId.center.x, detectedId.center.y));
            }
        }

    }


    public void stop() {
        if (visionPortal != null) {
            visionPortal.close();
        }
    }
}