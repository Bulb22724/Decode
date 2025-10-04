package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class TeleOp extends LinearOpMode {
    BallCannon ballCannon;
    @Override
    public void runOpMode() throws InterruptedException {
        ballCannon = new BallCannon();
        waitForStart();
        while (opModeIsActive()){
            ballCannon.Shoot(gamepad2.b);

        }

    }
}
