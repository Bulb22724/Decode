package org.firstinspires.ftc.teamcode.modules;

import android.graphics.Path;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@Config
public class MechTrain extends Thread {

    DcMotor frontLeft, frontRight, backLeft, backRight;
    double encoderResolution = 751.8;
    int wheelDiameterMM = 104;
    public static double koefp = 0.01;
    public static double koefi = 1;
    public static double koefd = 1;
    public static double koefPd = 0;
    public static double koefPi = 0;
    public static double tDiscr = 0.025;
    double koefIdiscr = koefp * koefPi * tDiscr;
    double koefDdiscr = koefp * koefPd / tDiscr;
    static double p = 10;
    double timeMax = 5;
    double time1 = 0;
    double time2 = 0;
    private ElapsedTime timer = new ElapsedTime();

    LinearOpMode opMode;

    public MechTrain(LinearOpMode opMode) {
        frontLeft = opMode.hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = opMode.hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = opMode.hardwareMap.get(DcMotor.class, "backLeft");
        backRight = opMode.hardwareMap.get(DcMotor.class, "backRight");

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        timer.startTime();
        this.opMode = opMode;
    }

    public void telem() {
        Telemetry telemetry = opMode.telemetry;
        time1 = timer.seconds();
        telemetry.addLine("мощности, позиции и растояние моторов 1-передний левый мотор 2-передний правый мотор 3-задний левый мотор 4-задний правый мотор");
        for (DcMotor motor : new DcMotor[]{frontLeft, frontRight, backLeft, backRight}) {
            telemetry.addData("Мощность", motor.getPower());
            telemetry.addData("Позиция", motor.getCurrentPosition());
            telemetry.addData("Растояние", (motor.getCurrentPosition() / encoderResolution) * wheelDiameterMM * Math.PI);
            telemetry.addData("Время дискретизации", time1-time2);
            time2 = time1;
        }
    }

    /**
     * Подает мощность на моторы по трем осям
     *
     * @param g1x  мощность движения по оси Y тоесть при движении вперед или назад
     * @param g1y  мощьность движения по оси X тоесть при движении в право или лево
     * @param g1tr мощьность поворота
     */
    public void setPowerOnMecanumBase(double g1x, double g1y, double g1tr) {
        frontLeft.setPower(g1x - g1y + g1tr);
        frontRight.setPower(-g1x - g1y + g1tr);
        backLeft.setPower(g1x + g1y + g1tr);
        backRight.setPower(g1x - g1y + g1tr);
    }


    public void run() {
        Telemetry telemetry = opMode.telemetry;
        frontLeft.setPower(opMode.gamepad1.left_stick_x - opMode.gamepad1.left_stick_y + (opMode.gamepad1.left_trigger - opMode.gamepad1.right_trigger));
        frontRight.setPower(-opMode.gamepad1.left_stick_x - opMode.gamepad1.left_stick_y + (opMode.gamepad1.left_trigger - opMode.gamepad1.right_trigger));
        backLeft.setPower(opMode.gamepad1.left_stick_x + opMode.gamepad1.left_stick_y + (opMode.gamepad1.left_trigger - opMode.gamepad1.right_trigger));
        backRight.setPower(opMode.gamepad1.left_stick_x - opMode.gamepad1.left_stick_y + (opMode.gamepad1.left_trigger - opMode.gamepad1.right_trigger));
        telemetry.addLine("Поток работает");
        telemetry.update();
    }

    /**
     * rideTic едет с мощностью motorPowerY вперед или назад до позиции targetPosition
     * и с мощиностью motorPowerX вправо или лево до позиции targetPosition
     * targetPosition измеряется в дюймах
     *
     * @param motorPowerY    мощность движения по оси Y тоесть при движении вперед или назад
     * @param motorPowerX    мощьность движения по оси X тоесть при движении в право или лево
     * @param targetPosition > 0 - целевая позиция
     */
    public void rideTic(double motorPowerX, double motorPowerY, double targetPosition) {

        targetPosition = Math.abs((targetPosition * encoderResolution * 25.4) / (Math.PI * wheelDiameterMM));
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setPowerOnMecanumBase(motorPowerX, motorPowerY, 0);
        while (((frontLeft.getPower() != 0 || frontRight.getPower() != 0 || backLeft.getPower() != 0 || backRight.getPower() != 0)) && opMode.opModeIsActive()) {
//            if (Math.abs(frontRight.getCurrentPosition()) > targetPosition) {
//                frontRight.setPower(0);
//
//            }
//            if (Math.abs(frontLeft.getCurrentPosition()) > targetPosition) {
//                frontLeft.setPower(0);
//            }
            if (Math.abs(backRight.getCurrentPosition()) > targetPosition) {
                backRight.setPower(0);
                frontRight.setPower(0);
                frontLeft.setPower(0);
                backLeft.setPower(0);
            }
//            if (Math.abs(backLeft.getCurrentPosition()) > targetPosition) {
//                backLeft.setPower(0);
//            }
        }


        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void rideTicPID(double tp) {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double efl = tp - frontLeft.getCurrentPosition();
        double efr = tp - frontRight.getCurrentPosition();
        double ebl = tp - backLeft.getCurrentPosition();
        double ebr = tp - backRight.getCurrentPosition();

        double efl1 = 0;
        double efr1 = 0;
        double ebl1 = 0;
        double ebr1 = 0;

        double efl2 = 0;
        double efr2 = 0;
        double ebl2 = 0;
        double ebr2 = 0;

        timer.startTime();
        while (opMode.opModeIsActive() && (((-p < efl && efl < p) || (-p < efr && efr < p) || (-p < ebl && ebl < p) || (-p < ebr && ebr < p)) || timer.seconds() < timeMax)) {
            time1 = timer.seconds();
            efl = tp - frontLeft.getCurrentPosition();
            efr = tp + frontRight.getCurrentPosition();
            ebl = tp - backLeft.getCurrentPosition();
            ebr = tp - backRight.getCurrentPosition();
            tDiscr = time1-time2;
            koefDdiscr = koefp * koefPd / tDiscr;
            koefIdiscr = koefp * koefPi * tDiscr;

            frontLeft.setPower(koefp*efl);
            frontRight.setPower(koefp*efr);
            backLeft.setPower(koefp*ebl);
            backRight.setPower(koefp*ebr);

            time2 = time1;

            efl2 = efl1;
            efr2 = efr1;
            ebl2 = ebl1;
            ebr2 = ebr1;

            efl1 = efl;
            efr1 = efr;
            ebl1 = ebl;
            ebr1 = ebr;


        }
    }

    /**
     * moveForward движение вперед с мощьностью motorPower до позиции targetPosition
     * moveBack движение назад с мощьностью motorPower до позиции targetPosition
     * moveRight движение вправо с мощьностью motorPower до позиции targetPosition
     * moveLeft движение влево с мощьностью motorPower до позиции targetPosition
     * moveForvard движение вперед с мощьностью motorPower до позиции targetPosition
     *
     * @param motorPower
     * @param targetPosition
     */
    public void moveForward(double motorPower, double targetPosition) {
        rideTic(motorPower, 0, targetPosition);
    }

    public void moveBack(double motorPower, double targetPosition) {
        rideTic(0, motorPower, targetPosition);
    }

    public void moveRight(double motorPower, double targetPosition) {
        rideTic(-motorPower, 0, targetPosition);
    }

    public void moveLeft(double motorPower, double targetPosition) {
        rideTic(motorPower, 0, targetPosition);
    }

    public void moveForwardRight(double motorPower, double targetPosition) {
        rideTic(motorPower, motorPower, targetPosition);
    }

    public void moveForwardLeft(double motorPower, double targetPosition) {
        rideTic(motorPower, -motorPower, targetPosition);
    }

    public void moveBackRight(double motorPower, double targetPosition) {
        rideTic(-motorPower, motorPower, targetPosition);
    }

    public void moveBackLeft(double motorPower, double targetPosition) {
        rideTic(-motorPower, -motorPower, targetPosition);
    }
}