package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Panic Mode Autonomous", group = "Thanks for making me stress")
public class AutoNightBefore extends LinearOpMode{

    private ElapsedTime runtime = new ElapsedTime(); //Timer

    private double driveDuration = 2.15d; //Duration to drive, in seconds

    //Motor objects
    private DcMotor leftMotor = null;
    private DcMotor rightMotor = null;

    @Override
    public void runOpMode() throws InterruptedException {
        //Set motors to objects
        leftMotor = hardwareMap.dcMotor.get("left motor");
        rightMotor = hardwareMap.dcMotor.get("right motor");

        //Set motor directions
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        //Set motor power to the starting powers
        leftMotor.setPower(1);
        rightMotor.setPower(1);

        //While the opmode is running
        while (opModeIsActive()) {

            //If the time is up
            if (runtime.seconds()>=driveDuration) {

                //Stop the motors
                leftMotor.setPower(0);
                rightMotor.setPower(0);
            }
        }
    }
}
