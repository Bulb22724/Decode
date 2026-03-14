package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.MechTrain;

@Config
public class Intake {
    DcMotorEx intakeMotor;
    ColorDetector colorDetector;
    LinearOpMode opMode;
    MechTrain mechTrain;
    Filter filter;

    // isOn хранит состояние intakeMotor работает.не работает
    boolean isOn = false;
    boolean run = false;
    // хранит направление вращения intakeMotor
    boolean isIn = true;
    // absMotorPower хранит модуль мощности intakeMotor
    public static double motorPower = 1;
    public static double k = 0.75;
    public int greenPos = 0;
    public int rotateCycle = 0;

    public Intake(LinearOpMode opMode) {
        this.opMode = opMode;
        intakeMotor = opMode.hardwareMap.get(DcMotorEx.class, "intakeMotor");
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        mechTrain = new MechTrain(opMode);
        filter = new Filter(opMode);
        colorDetector = new ColorDetector();
        colorDetector.colorDetector(opMode);
    }

    /**
     * on включает intakeMotor с мощностью absMotorPower
     */
    public void onOrOff() {
        if (isOn) {
            isOn = false;
            intakeMotor.setPower(0);
        }
        else {
            intakeMotor.setPower(motorPower);
            isOn = true;
        }

    }
    public void threeBallsIntake() {
        run = true;
        filter.nextPosition(0.5);
    for (int i = 0; i < 3; i++) {
        intakeMotor.setPower(-1);
        while (!colorDetector.ballIsReady() && opMode.opModeIsActive() && !opMode.gamepad1.a) {
            mechTrain.setPowerOnMecanumBase(opMode.gamepad1.left_stick_x, opMode.gamepad1.left_stick_y, opMode.gamepad1.left_trigger-opMode.gamepad1.right_trigger);
            if (opMode.gamepad1.a){run = false;}
        }
        intakeMotor.setPower(0);
        if (run) {filter.nextPosition(1);}
        }
        filter.nextPosition(0.5);
    }
    public void threeBallsAndSortIntake() {
        filter.nextPosition(0.5);
        for (int i = 0; i < 3; i++) {
            intakeMotor.setPower(-1);
            while (!colorDetector.ballIsReady() && opMode.opModeIsActive() && !opMode.gamepad1.a);
            if (colorDetector.isGreen()) {greenPos = rotateCycle;}
            intakeMotor.setPower(0);
            filter.nextPosition(1);
            rotateCycle += 1;
        }
        filter.nextPosition(rotateCycle-2.5);

    }
    /**
     * off выключает intakeMotor
     */


    /**
     * in изменяет направление вращения intakeMotor
     */
    public void reversIntake() {
        motorPower=-motorPower;

    }



    /**
     * out изменяет направление вращения intakeMotor
     */
    public void addData() {
        Telemetry telemetry = opMode.telemetry;
        telemetry.addData("Направление захвата", intakeMotor.getDirection());
        telemetry.addData("мотор включен", isOn);
        telemetry.addData("мотор вращается внутрь", isIn);
        telemetry.addData("модуль мощности мотора", motorPower);
        telemetry.addData("скорость вращения мотора", intakeMotor.getVelocity());
    }
}
