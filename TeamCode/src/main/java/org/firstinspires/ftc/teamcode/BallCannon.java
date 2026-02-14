package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.modules.AprilTagWebcam;
import org.firstinspires.ftc.teamcode.modules.MechTrain;

@Config
public class BallCannon {
    /* активировать моторы по кнопочке
     */
    DcMotorEx shootingMotor;
    DcMotorEx ballPushingMotor;
    Servo ballPushingServo;
    MechTrain mechTrain;

    //
    int ticks = 288;
    public static int phase = 0;
    double motorPower = 0;
    double radius = 0.019;
    boolean modeShootingMotor = false;
    boolean stateButtonB = false;
    boolean stateButtonA = false;
    public static double nullPosition = 0.7;
    public static double pushPower = 1;
    public static double shootPower = -1;
    public static double timerForShoot = 4;
    public static double timeForPush = 1;
    public static double timeB = 0.37;
    public boolean isMotorOn = false;
    public static double ballPushingPosition = 0;
    public static double maxVelocity = 1200;
    boolean xState = false;
    ElapsedTime timer = new ElapsedTime();
    LinearOpMode opMode;
    Filter filter;
    AprilTagWebcam camera;

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
        ballPushingMotor = opMode.hardwareMap.get(DcMotorEx.class, "ballPushingMotor");
        filter = new Filter(opMode);
        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(), opMode.telemetry);

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
        return radius * (shootingMotor.getVelocity(AngleUnit.RADIANS));

    }

    public void addData() {
        Telemetry telemetry = opMode.telemetry;
        telemetry.addData("скорость колеса пушки", shootingMotor.getVelocity(AngleUnit.RADIANS)*3.14);
    }

    /**
     * Метод для выстрела при нажатии кнопки A
     *
     * @param g2a кнопка A на втором геймпаде
     */
    public void Shoot1() {
        timer.reset();
        shootingMotor.setPower(shootPower);
        while ((timerForShoot > timer.seconds()) && opMode.opModeIsActive()) ;
        ballPushingServo.setPosition(ballPushingPosition);
        timer.reset();
        while ((timeForPush > timer.seconds()) && opMode.opModeIsActive()) ;
        ballPushingServo.setPosition(nullPosition);
        shootingMotor.setPower(0);
        timer.reset();
        while ((timeForPush > timer.seconds()) && opMode.opModeIsActive()) ;


    }

    /**
     * меняет направление вращения мотора для выстрела при нажатии кнопки B
     */
    public void servoUD(boolean g2x) {
        if (g2x) {
            ballPushingServo.setPosition(ballPushingPosition);
            ;
        } else {
            ballPushingServo.setPosition(nullPosition);
        }
    }

    public void pushMotor(double power) {
        ballPushingMotor.setPower(power);
    }

    /**
     * Метод для подталкивания мяча и выстреле при вызове
     */
    public void Shoot() {
        timer.reset();
        shootingMotor.setPower(shootPower);
        while ((timerForShoot > timer.seconds()) && opMode.opModeIsActive()) ;
        telemetry.addData("скорость колеса пушки", shootingMotor.getVelocity(AngleUnit.RADIANS));
        telemetry.update();
        ballPushingServo.setPosition(ballPushingPosition);
        timer.reset();
        while ((timeForPush > timer.seconds()) && opMode.opModeIsActive()) ;
        ballPushingServo.setPosition(nullPosition);
        timer.reset();
        telemetry.addData("скорость колеса пушки", shootingMotor.getVelocity(AngleUnit.RADIANS));
        telemetry.update();
        while ((timeForPush > timer.seconds()) && opMode.opModeIsActive()) ;
        telemetry.addData("скорость колеса пушки", shootingMotor.getVelocity(AngleUnit.RADIANS));
        telemetry.update();
        filter.nextPosition(1);

        ballPushingServo.setPosition(ballPushingPosition);
        timer.reset();
        while ((timeForPush > timer.seconds()) && opMode.opModeIsActive()) ;
        ballPushingServo.setPosition(nullPosition);
        timer.reset();
        while ((timeForPush > timer.seconds()) && opMode.opModeIsActive()) ;

        filter.nextPosition(1);

        ballPushingServo.setPosition(ballPushingPosition);
        timer.reset();
        while ((timeForPush > timer.seconds()) && opMode.opModeIsActive()) ;
        ballPushingServo.setPosition(nullPosition);
        timer.reset();
        while ((timeForPush > timer.seconds()) && opMode.opModeIsActive()) ;

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
