package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class BallCannon {
    /* активировать моторы по кнопочке
     */ DcMotorEx shootingMotor;
    DcMotorEx ballPushingMotor;

    //
    double motorPower = 0;
    double radius = 0.082;
    boolean modeShootingMotor = false;
    boolean stateButtonB = false;
    boolean stateButtonA = false;

    LinearOpMode opMode;

    /**
     * HardwareMap это карта устройств
     * <p>
     * BallCannon это конструктор который принимает как аргумент карту устройств
     *
     * @param opMode
     */
    public BallCannon(LinearOpMode opMode) {
        shootingMotor = opMode.hardwareMap.get(DcMotorEx.class, "shootingMotor");
        shootingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        ballPushingMotor = opMode.hardwareMap.get(DcMotorEx.class, "ballPushingMotor");
        ballPushingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    /**
     * setPower изменяет мощность мотора при изменении положения по оси y правого джостика второго геймпада
     * g2RaghtSticY передаёт значение положения правого джостика по оси y
     *
     * @param g2RaghtSticY
     * @return
     */
    public void setPower(float g2RaghtSticY) {
        if (g2RaghtSticY > 0 && motorPower <= 1) {
            motorPower += 0.005;
        }
        if (g2RaghtSticY < 0 && motorPower >= 0) {
            motorPower -= 0.005;
        }
    }

    /**
     * Метод для вращения мотора пушки по часовой на максимальной мощиности
     */
//    public void rotate(double motorPower) {
//        shootingMotor.setPower(motorPower);
//    }
//
//    /**
//     * метод для остановки мотора
//     */
//    public void stop() {
//        shootingMotor.setPower(0);
//    }
//
//    /**
//     * метод для вращения мотора против часовой на максимальной мощности
//     */
//    public void inverse(double motorPower) {
//        shootingMotor.setPower(-motorPower);
//    }
    public double velocityMotor() {
        return radius * 3.14 * 2 * (shootingMotor.getVelocity() / 384.5);

    }

    public void telem() {
        Telemetry telemetry = opMode.telemetry;
        telemetry.addData("скорость колеса пушки", velocityMotor());
    }

    public void Shoot(boolean g2a) {
        if (stateButtonA && !g2a) {
            modeShootingMotor = !modeShootingMotor;
        }
        if (modeShootingMotor) {
            shootingMotor.setPower(motorPower);
        } else {
            shootingMotor.setPower(0);
        }
        stateButtonA = g2a;
    }

    public void inverseDirection(boolean g2b) {
        if (stateButtonB && !g2b) {
            motorPower = -motorPower;
        }
        stateButtonB = g2b;
    }

    /**
     * @param g2x состояние кнопки d на 2-м джойстике
     */
    public void controlBallPushingMotor(boolean g2x) {
        if (g2x) {
            ballPushingMotor.setPower(1);
        } else {
            ballPushingMotor.setPower(0);
        }
//        ballPushingMotor.setPower(1);
//        if (g2x) {
//            ballPushingMotor.setTargetPosition(72);
//            ballPushingMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            while (ballPushingMotor.isBusy());
//            ballPushingMotor.setPower(0);
//        } else {
//            ballPushingMotor.setTargetPosition(0);
//            ballPushingMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            while (ballPushingMotor.isBusy());
//            ballPushingMotor.setPower(0);
//        }
    }


    public void rotatePushingMotor() {
        ballPushingMotor.setPower(1);
    }

    public void stopPushingMotor() {
        ballPushingMotor.setPower(0);
    }

    public void rotateShootingMotor() {
        shootingMotor.setPower(1);
    }

    public void stopShootingMotor() {
        shootingMotor.setPower(0);
    }

}
