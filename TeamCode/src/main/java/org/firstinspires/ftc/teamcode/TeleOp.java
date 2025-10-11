package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "Linear OpMode")
public class TeleOp extends LinearOpMode {
    BallCannon ballCannon;
    boolean stateButtonB;
    int mode=0;
    @Override
    public void runOpMode() throws InterruptedException {
        ballCannon = new BallCannon(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            if (mode==1) {
                ballCannon.rotate();
            } else if (mode==0) {
                ballCannon.stop();
            } else {
                ballCannon.inverse();
            }
            if(stateButtonB && !gamepad2.b){
                mode=(mode+1)%3;

            }
            stateButtonB = gamepad2.b;
        }

    }
}
