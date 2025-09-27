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
    public void setPowerOnMecanumBase(float g1x, float g1y, float g1tr) {
        frontLeft.setPower(-g1x + g1y + g1tr);
        frontRight.setPower(-g1x - g1y + g1tr);
        backLeft.setPower(g1x + g1y + g1tr);
        backRight.setPower(g1x - g1y + g1tr);
    }
}