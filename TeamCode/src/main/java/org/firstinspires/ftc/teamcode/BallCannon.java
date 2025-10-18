package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class BallCannon {
    /* активировать моторы по кнопочке
     */ DcMotorEx shootingMotor;

    //
    double motorPower = 0;
    boolean stateButtonB = false;
    int mode = 0;

    /**
     * HardwareMap это карта устройств
     *
     * BallCannon это конструктор который принимает как аргумент карту устройств
     *
     * @param hard
     */
    public BallCannon(HardwareMap hard) {
        shootingMotor = hard.get(DcMotorEx.class, "shootingMotor");
        shootingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

    }

    /**
     * setPower изменяет мощность мотора при изменении положения по оси y правого джостика второго геймпада
     * g2RaghtSticY передаёт значение положения правого джостика по оси y
     * @param g2RaghtSticY
     * @return
     */
    public void setPower(float g2RaghtSticY) {
        if (g2RaghtSticY > 0 && motorPower < 1) {
            motorPower += 0.001;
        }
        if (g2RaghtSticY < 0 && motorPower > 0) {
            motorPower -= 0.001;
        }
    }

    /**
     * Метод для вращения мотора пушки по часовой на максимальной мощиности
     */
    public void rotate(double motorPower) {
        shootingMotor.setPower(motorPower);
    }

    /**
     * метод для остановки мотора
     */
    public void stop() {
        shootingMotor.setPower(0);
    }

    /**
     * метод для вращения мотора против часовой на максимальной мощности
     */
    public void inverse(double motorPower) {
        shootingMotor.setPower(-motorPower);
    }

    public double velosityMotor() {
        return shootingMotor.getVelocity() / 384.5;

    }

    public void controlShootingMotor(boolean g2b) {
        if (mode == 1) {
            rotate(motorPower);
        } else if (mode == 0) {
            stop();
        } else {
            inverse(motorPower);
        }
        if (stateButtonB && !g2b) {
            mode = (mode + 1) % 3;
        }
        stateButtonB = g2b;

    }

}