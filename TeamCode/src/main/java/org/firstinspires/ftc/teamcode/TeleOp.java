package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TeleOp extends LinearOpMode {
    DcMotor frontLeft, frontRight, backLeft, backRight;

    /**
     * Подает мощность на моторы по трем осям
     * @param g1x движение по x
     * @param g1y движение по y
     * @param g1tr поворот
     */
    public void motorDrive(float g1x, float g1y, float g1tr) {
        frontLeft.setPower(-g1x + g1y + g1tr);
        frontRight.setPower(-g1x - g1y + g1tr);
        backLeft.setPower(g1x + g1y + g1tr);
        backRight.setPower(g1x - g1y + g1tr);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        waitForStart();
        while (opModeIsActive()) {
            motorDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.left_trigger - gamepad1.right_trigger);
        }
    }
}


