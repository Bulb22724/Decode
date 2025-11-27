package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Intake {
    DcMotorEx intakeMotor;
    boolean oldStateg1a;
    boolean oldStateg1b;
    boolean oldStateg1x;
    boolean oldStateg1y;
    /**
     * stateIntake хранит состояние intakeMotor 1-включен,0-выключен
     */
    int stateIntake=0;
    /**
     * хранит направление вращения intakeMotor 1-вперед,2-назад
     */
    int directionIntake=1;
    /**
     * moduleIntakeMotorPower хранит модуль мощность intakeMotor
     */
    double moduleIntakeMotorPower=0.5;
    /**
     * IntakeMotorPower хранит мощность intakeMotor
     */
    double intakeMotorPower=moduleIntakeMotorPower;
    public Intake(LinearOpMode opMode) {
        intakeMotor = opMode.hardwareMap.get(DcMotorEx.class, "intakeMotor");
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    /**
     * intakeOn включает intakeMotor с мощьностью intakeMotorPover при переходе кнопки а 1 джостика из состояния выключенно в состояние включенно
     * @param g1a принимет значение кнопки а первого джостика
     * oldStateg1a преведущее значение состояния кнопки а первого джостика
     */
    public void intakeOn(boolean g1a){
        if(g1a && !oldStateg1a && stateIntake!=1){
            stateIntake=1;
            intakeMotor.setPower(intakeMotorPower);
        }
        oldStateg1a =g1a;
    }
    /**
     * intakeOff выключает intakeMotor при переходе кнопки b 1 джостика из состояния выключенно в состояние включенно
     * @param g1b принимет значение кнопки b первого джостика
     * oldStateg1b преведущее значение состояния кнопки b первого джостика
     */
    public void intakeOff(boolean g1b){
        if(g1b && !oldStateg1b && stateIntake!=0){
            stateIntake=0;
            intakeMotor.setPower(0);
        }
        oldStateg1b =g1b;
    }
    /**
     * intakeIn изменяет направление вращения intakeMotor  при переходе кнопки x 1 джостика из состояния выключенно в состояние включенно
     * @param g1x принимет значение кнопки x первого джостика
     * oldStateg1x преведущее значение состояния кнопки x первого джостика
     */
    public void intakeIn(boolean g1x){
        if(g1x && !oldStateg1x && directionIntake!=1){
            directionIntake=1;
            intakeMotorPower=moduleIntakeMotorPower;
            if(stateIntake==1){
                intakeMotor.setPower(intakeMotorPower);
            }
        }
        oldStateg1x =g1x;
    }
    /**
     * intakeOut изменяет направление вращения intakeMotor  при переходе кнопки y 1 джостика из состояния выключенно в состояние включенно
     * @param g1y принимет значение кнопки y первого джостика
     * oldStateg1y преведущее значение состояния кнопки y первого джостика
     */
    public void intakeOut(boolean g1y){
        if(g1y && !oldStateg1y && directionIntake!=2){
            directionIntake=2;
            intakeMotorPower=-moduleIntakeMotorPower;
            if(stateIntake==1){
                intakeMotor.setPower(intakeMotorPower);
            }
        }
        oldStateg1y =g1y;
    }
}
