package org.firstinspires.ftc.teamcode;
//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.modules.MechTrain;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "Linear OpMode")

public class TeleOp extends LinearOpMode {
    MechTrain mechTrain;
    Filter filter;
    BallCannon ballCannon;

    boolean stateButtonA = false;
    boolean stateY = false;
    boolean stateRightBumper = false;
    boolean stateLeftBumper = false;
    @Override
    public void runOpMode() throws InterruptedException {
        ballCannon = new BallCannon(this);
//        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        mechTrain = new MechTrain(this);
        filter = new Filter(this);
        waitForStart();
        while (opModeIsActive()) {
           mechTrain.setPowerOnMecanumBase(0.75*gamepad1.left_stick_x, 0.75*gamepad1.left_stick_y, 0.75*(gamepad1.left_trigger - gamepad1.right_trigger));
//            ballCannon.setPower(-gamepad2.right_stick_y);
            if (stateButtonA && !gamepad2.a) {
                mechTrain.setPowerOnMecanumBase(0, 0, 0);
                ballCannon.Shoot();
            }
            stateButtonA = gamepad2.a;
            if (gamepad2.b) {
                ballCannon.inverseDirection();
            }
            if (gamepad2.x) {
                ballCannon.servoDown();
            }
            if (!gamepad2.y && stateY) {
                if (filter.isValveOpen) {
                    filter.valveOff();
                }
                else {
                    filter.valveOn();
                }
            }
            stateY = gamepad2.y;
            if (!gamepad2.left_bumper && stateLeftBumper) {
                filter.left();
            }
            stateLeftBumper = gamepad2.left_bumper;

            if (!gamepad2.right_bumper && stateRightBumper) {
                filter.right();
            }
            stateRightBumper = gamepad2.right_bumper;
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
            filter.addData();
            telemetry.update();


        }
    }
}
