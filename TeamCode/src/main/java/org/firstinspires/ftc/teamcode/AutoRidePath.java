package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Curve;
import com.pedropathing.geometry.PedroCoordinates;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathConstraints;
import com.pedropathing.paths.PathChain;
public class AutoRidePath {
    private final Pose startPose = new Pose (24, 144, Math.toRadians(180));
    private final Pose scorePose = new Pose (24, 120, Math.toRadians(45));
    private final Pose middlePose = new Pose (48, 72, Math.toRadians(180));
    private  final Pose firstColumnPose = new Pose (36,84, Math.toRadians(180));
    private  final Pose secondColumnPose = new Pose (36,60, Math.toRadians(180));
    private  final Pose thirdColumnPose = new Pose (36,36, Math.toRadians(180));
    private  final Pose grabFirstPose = new Pose (12,84, Math.toRadians(180));
    private  final Pose grabSecondPose = new Pose (12,60, Math.toRadians(180));
    private  final Pose grabThirdPose = new Pose (12,36, Math.toRadians(180));
    private Follower follower;
    private PathChain firstScore, fromScoreToFirstColumn, fromScoreToSecondColumn, fromScoreToThirdColumn, toScore;
    PathBuilder path = follower.pathBuilder()


            .addPath(new BezierLine(startPose, scorePose))
            .setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading()
    public void pathBuild() {
        firstScore = follower.pathBuilder()
                .addPath(new BezierLine(startPose, scorePose))
                .setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading())
                .build();

        fromScoreToFirstColumn = follower.pathBuilder()
                .addPath(new BezierLine(scorePose, middlePose))
                .setLinearHeadingInterpolation(scorePose.getHeading(), middlePose.getHeading())
                .addPath(new BezierLine(middlePose, firstColumnPose))
                .setLinearHeadingInterpolation(middlePose.getHeading(), firstColumnPose.getHeading())
                .build();

        fromScoreToSecondColumn = follower.pathBuilder()
                .addPath(new BezierLine(scorePose, middlePose))
                .setLinearHeadingInterpolation(scorePose.getHeading(), middlePose.getHeading())
                .addPath(new BezierLine(middlePose, secondColumnPose))
                .setLinearHeadingInterpolation(middlePose.getHeading(), secondColumnPose.getHeading())
                .addPath(new BezierLine(secondColumnPose, grabSecondPose))
                .setLinearHeadingInterpolation(secondColumnPose.getHeading(), grabSecondPose.getHeading())
                .build();

        fromScoreToThirdColumn = follower.pathBuilder()
                .addPath(new BezierLine(scorePose, middlePose))
                .setLinearHeadingInterpolation(scorePose.getHeading(), middlePose.getHeading())
                .addPath(new BezierLine(middlePose, thirdColumnPose))
                .setLinearHeadingInterpolation(middlePose.getHeading(), thirdColumnPose.getHeading())
                .addPath(new BezierLine(thirdColumnPose, grabThirdPose))
                .setLinearHeadingInterpolation(thirdColumnPose.getHeading(), grabThirdPose.getHeading())
                .build();
        toScore = follower.pathBuilder()
                .addPath(new BezierLine(follower.getPose(), middlePose))
                .setLinearHeadingInterpolation(follower.getPose().getHeading(), middlePose.getHeading())
                .addPath(new BezierLine(middlePose, scorePose))
                .setLinearHeadingInterpolation(middlePose.getHeading(), scorePose.getHeading())
                .build();
        
    }

    public void AutoFollowPath() {
        if (follower.isBusy()) {
            follower.followPath(firstScore, true);
            follower.followPath(fromScoreToFirstColumn);
            follower.followPath(toScore);
        }

    }

}

