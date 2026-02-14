package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Intake {
    DcMotorEx intakeMotor;
    LinearOpMode opMode;
    // isOn хранит состояние intakeMotor работает.не работает
    boolean isOn = false;
    // хранит направление вращения intakeMotor
    boolean isIn = true;
    // absMotorPower хранит модуль мощности intakeMotor
    public static double motorPower = 1;

    public Intake(LinearOpMode opMode) {
        intakeMotor = opMode.hardwareMap.get(DcMotorEx.class, "intakeMotor");
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        this.opMode = opMode;
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
