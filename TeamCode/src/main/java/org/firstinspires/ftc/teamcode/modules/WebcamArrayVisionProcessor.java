package org.firstinspires.ftc.teamcode.modules;

import android.graphics.Canvas;

import com.sun.tools.javac.util.ArrayUtils;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Mat;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WebcamArrayVisionProcessor implements VisionProcessor {

    @Override
    public void init(int width, int height, CameraCalibration calibration) {

    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        // это сплошной ужас. его цель - собрать картинку с камеры
        // в 2D-массив для последующей обработки в limelight (по идее).
        // на самом деле мы ещё не уверены, что он работает с массивом пикселей
        ArrayList<ArrayList<ArrayList<Double>>> pixels = new ArrayList<ArrayList<ArrayList<Double>>>();
        for (int row = 0; row < frame.rows(); row++) {
            for (int column = 0; column < frame.cols(); column++) {
                pixels.get(column).set(row, new ArrayList<Double>(Arrays.stream(frame.get(row, column)).boxed().collect(Collectors.toList())));
            }
        }
        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }
}
