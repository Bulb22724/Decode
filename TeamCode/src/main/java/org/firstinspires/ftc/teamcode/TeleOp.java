package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.MechTrain;
@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends LinearOpMode {
    MechTrain mechTrain;

    @Override
    public void runOpMode() throws InterruptedException {
        mechTrain = new MechTrain(this);
        waitForStart();
        while (opModeIsActive()) {
            mechTrain.setPowerOnMecanumBase(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.left_trigger - gamepad1.right_trigger);
        }
    }
}


