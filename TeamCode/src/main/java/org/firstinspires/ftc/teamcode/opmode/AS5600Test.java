package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.AS5600;

@Autonomous
public class AS5600Test extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        AS5600 encoder = hardwareMap.get(AS5600.class, "encoder");

        waitForStart();

        telemetry.addData("угол в градусах", encoder::getDegreesAngle);
        telemetry.addData("угол в радианах", encoder::getRadiansAngle);
        telemetry.addData("угол в сырых единицах", encoder::getRawAngle);

        while (opModeIsActive());
    }
}
