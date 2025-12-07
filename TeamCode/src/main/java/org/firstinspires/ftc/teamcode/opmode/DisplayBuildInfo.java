package org.firstinspires.ftc.teamcode.opmode;

import org.firstinspires.ftc.teamcode.BuildConfig;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class DisplayBuildInfo extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        telemetry.addData("Дата сборки", BuildConfig.APP_BUILD_TIME);
        telemetry.addData("Незакоммиченная", BuildConfig.COMMIT_INFO.endsWith("-dirty") ? "да" : "нет");
        telemetry.addData("Коммит", BuildConfig.COMMIT_INFO);
        telemetry.update();
        while (opModeIsActive());
    }
}
