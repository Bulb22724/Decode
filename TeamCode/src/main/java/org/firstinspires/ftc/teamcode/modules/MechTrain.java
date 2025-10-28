package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class MechTrain {
    DcMotor frontLeft, frontRight, backLeft, backRight;
    double encoderResolution = 537.7;
    int wheelDiameterMM =104;
    public MechTrain(LinearOpMode opMode) {
        frontLeft = opMode.hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = opMode.hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = opMode.hardwareMap.get(DcMotor.class, "backLeft");
        backRight = opMode.hardwareMap.get(DcMotor.class, "backRight");
    }

    /**
     * Подает мощность на моторы по трем осям
     *
     * @param g1x  мощность движения по оси Y тоесть при движении вперед или назад
     * @param g1y  мощьность движения по оси X тоесть при движении в право или лево
     * @param g1tr мощьность поворота
     */
    public void setPowerOnMecanumBase(double g1x, double g1y, double g1tr) {
        frontLeft.setPower(-g1x + g1y + g1tr);
        frontRight.setPower(-g1x - g1y + g1tr);
        backLeft.setPower(g1x + g1y + g1tr);
        backRight.setPower(g1x - g1y + g1tr);
    }


    /**
     * rideTic едет с мощностью motorPowerY вперед или назад до позиции targetPosition
     * и с мощиностью motorPowerX вправо или лево до позиции targetPosition
     *targetPosition измеряется в дюймах
     * @param motorPowerY мощность движения по оси Y тоесть при движении вперед или назад
     * @param motorPowerX мощьность движения по оси X тоесть при движении в право или лево
     * @param targetPosition > 0 - целевая позиция
     */
    public void rideTic(double motorPowerX,double motorPowerY, double targetPosition) {

        targetPosition = Math.abs((targetPosition*encoderResolution*25.4)/(Math.PI* wheelDiameterMM));
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setPowerOnMecanumBase(motorPowerX, motorPowerY, 0);
        while (frontLeft.getPower() != 0 && frontRight.getPower() != 0 && backLeft.getPower() != 0 && backRight.getPower() != 0) {
            if (Math.abs(frontRight.getCurrentPosition()) < targetPosition) {
                frontRight.setPower(0);
            }
            if (Math.abs(frontLeft.getCurrentPosition()) < targetPosition) {
                frontLeft.setPower(0);
            }
            if (Math.abs(backRight.getCurrentPosition()) < targetPosition) {
                backRight.setPower(0);
            }
            if (Math.abs(backLeft.getCurrentPosition()) < targetPosition) {
                backLeft.setPower(0);
            }
        }
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /**
     * moveForward движение вперед с мощьностью motorPower до позиции targetPosition
     * moveBack движение назад с мощьностью motorPower до позиции targetPosition
     * moveRight движение вправо с мощьностью motorPower до позиции targetPosition
     * moveLeft движение влево с мощьностью motorPower до позиции targetPosition
     * moveForvard движение вперед с мощьностью motorPower до позиции targetPosition
     * @param motorPower
     * @param targetPosition
     */
    public void moveForward(double motorPower,double targetPosition){
        rideTic(motorPower,0,targetPosition);
    }
    public void moveBack(double motorPower,double targetPosition){
        rideTic(-motorPower,0,targetPosition);
    }
    public void moveRight(double motorPower,double targetPosition){
        rideTic(0,motorPower,targetPosition);
    }
    public void moveLeft(double motorPower,double targetPosition){
        rideTic(0,-motorPower,targetPosition);
    }
    public void moveForwardRight(double motorPower,double targetPosition){
        rideTic(motorPower,motorPower,targetPosition);
    }
    public void moveForwardLeft(double motorPower,double targetPosition){
        rideTic(motorPower,-motorPower,targetPosition);
    }
    public void moveBackRight(double motorPower,double targetPosition){
        rideTic(-motorPower,motorPower,targetPosition);
    }
    public void moveBackLeft(double motorPower,double targetPosition){
        rideTic(-motorPower,-motorPower,targetPosition);
    }
}