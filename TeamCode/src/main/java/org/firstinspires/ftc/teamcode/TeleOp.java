package org.firstinspires.ftc.teamcode;
//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.modules.MechTrain;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "Linear OpMode")
public class TeleOp extends LinearOpMode {
    MechTrain mechTrain;

    BallCannon ballCannon;

    @Override
    public void runOpMode() throws InterruptedException {
        ballCannon = new BallCannon(this);
//        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        mechTrain = new MechTrain(this);

        waitForStart();
        while (opModeIsActive()) {
           mechTrain.setPowerOnMecanumBase(0.75*gamepad1.left_stick_x, 0.75*gamepad1.left_stick_y, 0.75*(gamepad1.left_trigger - gamepad1.right_trigger));
            ballCannon.setPower(-gamepad2.right_stick_y);
            ballCannon.Shoot(gamepad2.a);
            ballCannon.inverseDirection(gamepad2.b);

            telemetry.addData("Velosity", ballCannon.velocityMotor());
            // 1. выведи все переменные в консол
            // 2. выведи в консоль управлять
            telemetry.addData("ballPushingMotorPos", ballCannon.ballPushingServo.getPosition());
            telemetry.addData("ballShootingMotorPos", ballCannon.shootingMotor.getCurrentPosition());



            telemetry.addLine("a gamepad 2-включение/выключение shootingMotor;" +
                    "b gamepad 2-изменение направления shootingMotor;" +
                    "x gamepad 2-толкание шара" +
                    "правый джойстик gamepad 2- вверх/вниз управление мощностью мотора срельбы "+
                    "левый джойстик gamepad 1- езда робота по соответствующим направлениям");
            mechTrain.telem();
            telemetry.update();


        }
    }
}
