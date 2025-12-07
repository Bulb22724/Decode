package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class BallCannon {
    /* активировать моторы по кнопочке
     */ DcMotorEx shootingMotor;
    Servo ballPushingServo;

    //
    double motorPower = 0;
    double radius = 0.082;
    boolean modeShootingMotor = false;
    boolean stateButtonB = false;
    boolean stateButtonA = false;
    public static int nullPosition = 0;
    public static double pushPower = 1;
    public static double shootPower = 1;
    public static double timerForShoot = 4;
    public static double timeForPush = 2;
    public boolean isMotorOn = false;
    public static double ballPushingPosition = 0.1;

    ElapsedTime timer = new ElapsedTime();
    LinearOpMode opMode;

    /**
     * HardwareMap это карта устройств
     * <p>
     * BallCannon это конструктор который принимает как аргумент карту устройств
     *
     * @param opMode
     */
    public BallCannon(LinearOpMode opMode) {
        this.opMode = opMode;
        shootingMotor = opMode.hardwareMap.get(DcMotorEx.class, "shootingMotor");
        shootingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ballPushingServo = opMode.hardwareMap.get(Servo.class, "ballPushingServo");

    }
    public void shootOn() {
        shootingMotor.setPower(motorPower);
        isMotorOn = true;
    }


    /**
     * setPower изменяет мощность мотора при изменении положения по оси y правого джостика второго геймпада
     * g2RaghtSticY передаёт значение положения правого джостика по оси y
     *
     * @param g2RaghtSticY
     * @return
     */
//    public void setPower(float g2RaghtSticY) {
//        if (g2RaghtSticY > 0 && motorPower <= 1) {
//            motorPower += 0.005;
//        }
//        if (g2RaghtSticY < 0 && motorPower >= 0) {
//            motorPower -= 0.005;
//        }
//    }

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

    /**
     * Метод для выстрела при нажатии кнопки A
     *
     * @param g2a кнопка A на втором геймпаде
     */
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

    /**
     * меняет направление вращения мотора для выстрела при нажатии кнопки B
     *
     *      */
    public void inverseDirection() {
            shootingMotor.setPower(-shootPower + 0.5);
            timer.reset();
            while ((timerForShoot > timer.seconds()) && opMode.opModeIsActive());
            shootingMotor.setPower(0);

    }
    public void servoDown() {
        ballPushingServo.setPosition(nullPosition);
    }

    /**
     * Метод для подталкивания мяча и выстреле при вызове
     */
    public void Shoot() {
        timer.reset();
        shootingMotor.setPower(shootPower);
        while ((timerForShoot > timer.seconds()) && opMode.opModeIsActive()) ;
        ballPushingServo.setPosition(ballPushingPosition);
        timer.reset();
        while ((timeForPush > timer.seconds()) && opMode.opModeIsActive());
        ballPushingServo.setPosition(nullPosition);
        shootingMotor.setPower(0);




    }

    /**
     * @param /g2x состояние кнопки d на 2-м джойстике
     */
//    public void controlBallPushingMotor(boolean g2x) {
//        ballPushingServo.setPower(1);
//        if (g2x) {
//            ballPushingServo.setTargetPosition(72);
//            ballPushingServo.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            while (ballPushingServo.isBusy());
//            ballPushingServo.setPower(0);
//        } else {
//            ballPushingServo.setTargetPosition(0);
//            ballPushingServo.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            while (ballPushingServo.isBusy());
//            ballPushingServo.setPower(0);
//        }
//    }

//
//    public void rotatePushingMotor() {
//        ballPushingServo.setPower(1);
//    }
//
//    public void stopPushingMotor() {
//        ballPushingServo.setPower(0);
//    }
    public void rotateShootingMotor() {
        shootingMotor.setPower(1);
    }

    public void stopShootingMotor() {
        shootingMotor.setPower(0);
    }

}
