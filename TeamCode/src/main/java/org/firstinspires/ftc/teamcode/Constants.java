package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.ftc.localization.constants.DriveEncoderConstants;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {
    public static MecanumConstants driveConstants = new MecanumConstants()
            .leftFrontMotorName("frontLeft")
            .leftRearMotorName("backLeft")
            .rightFrontMotorName("frontRight")
            .rightRearMotorName("backRight")
            .leftFrontMotorDirection(Direction.FORWARD)
            .leftRearMotorDirection(Direction.REVERSE)
            .rightFrontMotorDirection(Direction.FORWARD)
            .rightRearMotorDirection(Direction.FORWARD)
            .maxPower(1);
    public static DriveEncoderConstants encoderConstants = new DriveEncoderConstants()
            .rightFrontMotorName("frontRight")
            .rightRearMotorName("backRight")
            .leftRearMotorName("backLeft")
            .leftFrontMotorName("frontLeft")
            .robotLength(0)
            .robotWidth(0)
            //В режиме настройки OpMode, в разделе «Локализация», выберите и запустите тюнер вперёд. Затем продвиньте робота вперёд на 48 дюймов (ровно на 2 клетки поля). Это расстояние можно настроить при необходимости. После того, как вы продвинете робота вперёд, на экране телеметрии отобразятся два числа:
            //
            //Расстояние, которое, по мнению робота, он преодолел
            //Множитель — это число, которое вам нужно.
            .forwardTicksToInches(1.644545981124804)
            //Боковой тюнер очень похож на передний, но расположен сбоку. В режиме настройки, в разделе локализации, выберите и запустите боковой тюнер. Сдвиньте робота влево на 48 дюймов (ровно на 2 клетки поля). Как и в случае с передним тюнером, это расстояние можно настроить.
            .strafeTicksToInches(0)
            //Поворотный тюнер, как и тюнеры прямого и бокового направления, работает аналогично поворотному. Разместите робота так, чтобы он был выровнен по фиксированной точке отсчёта (например, краю плитки поля). В режиме настройки, в разделе локализации, выберите и запустите боковой тюнер. Поверните робота против часовой стрелки на один полный оборот . Как и в случае с предыдущими тюнерами, этот угол можно настраивать.
            .turnTicksToInches(0)
            .leftFrontEncoderDirection(Encoder.FORWARD)
            .leftRearEncoderDirection(Encoder.FORWARD)
            .rightFrontEncoderDirection(Encoder.FORWARD)
            .rightRearEncoderDirection(Encoder.FORWARD);
    public static FollowerConstants followerConstants = new FollowerConstants().mass(0);
    /**
     * tValueConstraint
     * velocityConstraint
     * translationalConatraint
     * headingConstraint
     * timeoutConstraint
     * brakingStrengh
     * BEZIER_CURVE_SEARCH_LIMIT
     * brakingStart
     */
    public static PathConstraints pathConstraints = PathConstraints.defaultConstraints;


    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .driveEncoderLocalizer(encoderConstants)
                .build();
    }
}
