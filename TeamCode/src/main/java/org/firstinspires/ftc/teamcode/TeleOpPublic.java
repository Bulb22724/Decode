package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.MechTrain;

    @Config
@TeleOp
public class TeleOpPublic extends LinearOpMode {
    MechTrain mechTrain;
    Filter filter;
    BallCannon ballCannon;
    Launcher launcher;
    Intake intake;
    public static double k = 0.5;
    @Override
    public void runOpMode() throws InterruptedException {
        boolean xState = false, aState = false, bState = false,yState = false;
        ballCannon = new BallCannon(this);
        launcher = new Launcher(this);
        mechTrain = new MechTrain(this);
        filter = new Filter(this);
        intake = new Intake(this);
        waitForStart();
        while (opModeIsActive()) {
            mechTrain.setPowerOnMecanumBase(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.left_trigger - gamepad1.right_trigger);
            if (gamepad1.x && !xState) {
                intake.onOrOff();
            }
            xState = gamepad1.x;
            if (gamepad1.a && !aState) {
                ballCannon.shootOnOff();
            }
            aState = gamepad1.a;
            if (gamepad1.b && !bState) {
                ballCannon.Push();
            }
            bState = gamepad1.b;
            if (gamepad1.y && !yState) {
                ballCannon.music();
            }
            yState = gamepad1.y;
            filter.godlyMotor.setPower(gamepad1.right_stick_x*k);

        }
    }
}
