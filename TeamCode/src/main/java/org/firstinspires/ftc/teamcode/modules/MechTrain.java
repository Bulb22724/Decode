package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class MechTrain {
    DcMotor frontLeft, frontRight, backLeft, backRight;

    public MechTrain(LinearOpMode opMode) {
        frontLeft = opMode.hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = opMode.hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = opMode.hardwareMap.get(DcMotor.class, "backLeft");
        backRight = opMode.hardwareMap.get(DcMotor.class, "backRight");
    }

    /**
     * Подает мощность на моторы по трем осям
     *
     * @param g1x  движение по x
     * @param g1y  движение по y
     * @param g1tr поворот
     */
    public void setPowerOnMecanumBase(double g1x, double g1y, double g1tr) {
        frontLeft.setPower(-g1x + g1y + g1tr);
        frontRight.setPower(-g1x - g1y + g1tr);
        backLeft.setPower(g1x + g1y + g1tr);
        backRight.setPower(g1x - g1y + g1tr);
    }

    /**
     * rideTic едет с мощностью motorPower до позиции targetPosition
     *
     * @param motorPower
     * @param targetPosition > 0 - целевая позиция
     */
    public void rideTic(double motorPower, int targetPosition) {
        targetPosition = Math.abs(targetPosition);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setPowerOnMecanumBase(0, motorPower, 0);
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

}