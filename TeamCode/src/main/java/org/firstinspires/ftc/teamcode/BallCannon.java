package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class BallCannon {
    /* активировать моторы по кнопочке
     */ DcMotorEx shootingMotor;
    DcMotorEx ballPushingMotor;

    //
    double motorPower = 0;
    boolean modeShootingMotor = false;
    boolean stateButtonB = false;
    boolean stateButtonA = false;

    /**
     * HardwareMap это карта устройств
     * <p>
     * BallCannon это конструктор который принимает как аргумент карту устройств
     *
     * @param hard
     */
    public BallCannon(HardwareMap hard) {
        shootingMotor = hard.get(DcMotorEx.class, "shootingMotor");
        shootingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        ballPushingMotor = hard.get(DcMotorEx.class, "ballPushingMotor");
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
    public double velosityMotor() {
        return shootingMotor.getVelocity() / 384.5;

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
        ballPushingMotor.setPower(1);
        if (g2x) {
            ballPushingMotor.setTargetPosition(72);
            ballPushingMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while (ballPushingMotor.isBusy()) {
                delay(10);
            }
            ballPushingMotor.setPower(0);
        } else {
            ballPushingMotor.setTargetPosition(0);
            ballPushingMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            while (ballPushingMotor.isBusy()) {
                delay(10);
            }
            ballPushingMotor.setPower(0);
        }
    }


    /**
     * delay позволяет сделать задержку между действиями
     *
     * @param seconds время сколько ждать в секундах
     */
    public void delay(double seconds) {
        ElapsedTime time = new ElapsedTime();

        double startTime = time.seconds();
        while (time.seconds() - startTime < seconds) {
            //ждем
        }
    }
    public void rotatePushingMotor(){
        ballPushingMotor.setPower(1);
    }
    public void stopPushingMotor(){
        ballPushingMotor.setPower(0);
    }

}
