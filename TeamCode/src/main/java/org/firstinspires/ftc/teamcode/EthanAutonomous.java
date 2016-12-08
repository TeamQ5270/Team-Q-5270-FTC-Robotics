package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.regex.*;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;

@Autonomous(name="Ethan Auto", group ="Autonomous")
public class EthanAutonomous extends LinearOpMode {

    public static final String TAG = "Vuforia Sample";

    OpenGLMatrix lastLocation = null;

    VuforiaLocalizer vuforia;

    Scanner fr = null;

    public void logger(String whatToLog) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter("log.txt");
        pw.println(whatToLog);
        pw.close();
    }

    @Override public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(com.qualcomm.ftcrobotcontroller.R.id.cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "Ae4bn1n/////AAAAGUB2l6bUfkZVny2Q0jaLYIIKoUC+uWr6iohCfs1afyuNIj+MEulVW0XJYqxXl+uxWNp7NhbPu4JpnvE0ihnUCT+Zop08Zs2xxzOHOZpbhiVN9qIXypUAzJjj2fIGsjfhgzxRRlcU1di6VtRRjINxBV9d1HXtR67wB4OdYEmHiqLDh0fZ3uXaNIKG6tISezLaa32TJJXOIIlkTTxlCf2ER+kTUYfBn8AKKMy/FQ+bIFkl+6zWVC95qfOc1+WybBBgBYcYL966AzUZWAi38sgw1TS7jymeLQNiJRt7RCDJ+aiVgFM3WZhkbu0pDu0e1sehKTsJejwkBWYLMVz8UeEGuesc6hrhiVjZdUYRZjJoOmD1";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables stonesAndChips = this.vuforia.loadTrackablesFromAsset("StonesAndChips");
        VuforiaTrackable redTarget = stonesAndChips.get(0);
        redTarget.setName("RedTarget");  // Stones

        VuforiaTrackable blueTarget  = stonesAndChips.get(1);
        blueTarget.setName("BlueTarget");  // Chips

        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(stonesAndChips);

        float mmPerInch        = 25.4f;
        float mmBotWidth       = 18 * mmPerInch;            // ... or whatever is right for your robot
        float mmFTCFieldWidth  = (12*12 - 2) * mmPerInch;   // the FTC field is ~11'10" center-to-center of the glass panels

        OpenGLMatrix redTargetLocationOnField = OpenGLMatrix
                .translation(-mmFTCFieldWidth/2, 0, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 90, 0));
        redTarget.setLocation(redTargetLocationOnField);
        RobotLog.ii(TAG, "Red Target=%s", format(redTargetLocationOnField));
        OpenGLMatrix blueTargetLocationOnField = OpenGLMatrix
                .translation(0, mmFTCFieldWidth/2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 0, 0));
        blueTarget.setLocation(blueTargetLocationOnField);
        RobotLog.ii(TAG, "Blue Target=%s", format(blueTargetLocationOnField));
        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                .translation(mmBotWidth/2,0,0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.YZY,
                        AngleUnit.DEGREES, -90, 0, 0));
        RobotLog.ii(TAG, "phone=%s", format(phoneLocationOnRobot));
        ((VuforiaTrackableDefaultListener)redTarget.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)blueTarget.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);

        /**
         * A brief tutorial: here's how all the math is going to work:
         *
         * C = phoneLocationOnRobot  maps   phone coords -> robot coords
         * P = tracker.getPose()     maps   image target coords -> phone coords
         * L = redTargetLocationOnField maps   image target coords -> field coords
         *
         * So
         *
         * C.inverted()              maps   robot coords -> phone coords
         * P.inverted()              maps   phone coords -> imageTarget coords
         *
         * Putting that all together,
         *
         * L x P.inverted() x C.inverted() maps robot coords to field coords.
         *
         * @see VuforiaTrackableDefaultListener#getRobotLocation()
         */

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();

        /** Start tracking the data sets we care about. */
        stonesAndChips.activate();

        while (opModeIsActive()) {

            for (VuforiaTrackable trackable : allTrackables) {
                /**
                 * getUpdatedRobotLocation() will return null if no new information is available since
                 * the last time that call was made, or if the trackable is not currently visible.
                 * getRobotLocation() will return null if the trackable is not currently visible.
                 */
                telemetry.addData(trackable.getName(), ((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible() ? "Visible" : "Not Visible");    //

                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;
                }
            }
            /**
             * Provide feedback as to where the robot was last located (if we know).
             */
            if (lastLocation != null) {
                //  RobotLog.vv(TAG, "robot=%s", format(lastLocation));
                telemetry.addData("Pos", format(lastLocation));
            } else {
                telemetry.addData("Pos", "Unknown");
            }
            telemetry.update();
            DcMotor leftMotor = null;
            DcMotor rightMotor = null;

            leftMotor  = hardwareMap.dcMotor.get("left motor");
            rightMotor = hardwareMap.dcMotor.get("right motor");


            long endTime = System.currentTimeMillis() + 30000;
            while(System.currentTimeMillis() < endTime){
                String coordinatesString = format(blueTargetLocationOnField);
                Pattern p = Pattern.compile("[^\\[\\]\\\\s]+");
                Matcher m = p.matcher(coordinatesString);
                String[] coordinatesStringArray = m.group().split(",");
                int[] coordinates = new int[coordinatesStringArray.length];
                for (int i = 0; i < coordinatesStringArray.length; i++) {
                    coordinates[i] = Integer.parseInt(coordinatesStringArray[i]);
                }
                if(coordinates[2] > 95) {         //Turn Right
                   // logger("Right");
                    leftMotor.setPower(0.75);
                    rightMotor.setPower(0.25);
                }
                else if (coordinates[2]<85) {    //Turn Left
                    //logger("Left");
                    leftMotor.setPower(0.25);
                    rightMotor.setPower(0.75);
                }
                else{                            //Go Straight
                 //   logger("Straight");
                    leftMotor.setPower(0.5);
                    rightMotor.setPower(0.5);
                }

            }
            idle();
        }
    }

    /**
     * A simple utility that extracts positioning information from a transformation matrix
     * and formats it in a form palatable to a human being.
     */
    String format(OpenGLMatrix transformationMatrix) {
        return transformationMatrix.formatAsTransform();
    }
}
