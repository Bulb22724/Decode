package org.firstinspires.ftc.teamcode;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.modules.MechTrain;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "Linear OpMode")
public class TeleOp extends LinearOpMode {
    MechTrain mechTrain;

    BallCannon ballCannon;

    @Override
    public void runOpMode() throws InterruptedException {
        ballCannon = new BallCannon(hardwareMap);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        mechTrain = new MechTrain(this);
        waitForStart();
        while (opModeIsActive()) {
            mechTrain.setPowerOnMecanumBase(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.left_trigger - gamepad1.right_trigger);
        }
    }
}
