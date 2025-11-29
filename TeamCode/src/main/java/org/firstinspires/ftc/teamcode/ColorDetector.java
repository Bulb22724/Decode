package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ColorDetector {
    LinearOpMode opMode;
    ColorSensor colorSensor;
    double v;
    double s;
    double h;
    static double greenMin = 60;
    static double greenMax = 180;
    static double violetMin = 240;
    static double violetMax = 340;
    double red = colorSensor.red() / 255;
    double blue = colorSensor.blue() / 255;
    double green = colorSensor.green() / 255;

    public void colorDetector(LinearOpMode opMode) {
        this.opMode = opMode;
        colorSensor = opMode.hardwareMap.get(ColorSensor.class, "colorSensor");

    }

    public void currentColor() {
        colorSensor.enableLed(true);
        double red = colorSensor.red() / 255;
        double blue = colorSensor.blue() / 255;
        double green = colorSensor.green() / 255;
        colorSensor.enableLed(false);
        double Cmax = Math.max(Math.max(red, blue), green);
        double Cmin = Math.min(Math.min(red, blue), green);
        v = Cmax;
        double delta = Cmax - Cmin;
        if (Cmax == 0) {
            s = 0;
        } else {
            s = delta / Cmax;
        }

        if (Cmax == red) {
            h = 60 * ((green - blue) / delta + 6);
        } else if (Cmax == green) {
            h = 60 * ((blue - red) / delta + 2);
        } else if (Cmax == blue) {
            h = 60 * ((red - green) / delta + 4);
        } else if (delta == 0) {
            h = 0;
        }


    }

    public boolean isGreen() {
        currentColor();
        return greenMin < h && h < greenMax;
    }
    public boolean ballIsReady() {
        currentColor();
        return isGreen() || isPurple();
    }

    private boolean isPurple() {
        return violetMin < h && h < violetMax;
    }

    public void addData() {
        Telemetry telemetry = opMode.telemetry;
        currentColor();
        telemetry.addData("RGB/255", "%3.2f %3.2f %3.2", red, green, blue);
        telemetry.addData("Промежуток зеленого в hsv", "%3.2 %3.2", greenMin, greenMax);
        telemetry.addData("Промежуток фиолетого в hsv", "%3.2 %3.2", violetMin, violetMax);
        telemetry.addData("текущий цвет по h в hsv", h);
        telemetry.addData("Мяч в барабане", ballIsReady());
        telemetry.addData("Мяч фиолетовый", isPurple());
        telemetry.addData("Мяч зеленый", isGreen());
    }
}
