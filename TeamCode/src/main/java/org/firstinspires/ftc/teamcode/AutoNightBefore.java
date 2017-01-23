package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Panic Mode Autonomous", group = "Thanks for making me stress")
public class AutoNightBefore extends LinearOpMode{

    private ElapsedTime runtime = new ElapsedTime(); //Timer

    private double driveDuration = 4.20d; //Duration to drive, in seconds

    //Motor objects
    private DcMotor leftMotor = null;
    private DcMotor rightMotor = null;

    int turnRate = 10; //Turn rate
    int turnTime = 0; //Turn time
    float waitTime = 0; //Wait time
    float waitingTime = 0.4f; //Waitingtime

    @Override
    public void runOpMode() throws InterruptedException {
        //Set motors to objects
        leftMotor = hardwareMap.dcMotor.get("left motor");
        rightMotor = hardwareMap.dcMotor.get("right motor");

        //Set motor directions
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);


        //While the opmode is running
        runtime.reset();
        waitForStart();
        turnTime = turnRate+(int)runtime.seconds();
        while (opModeIsActive()) {


            //If the time is up
            if (runtime.seconds()>=driveDuration) {

                //Stop the motorsl
                leftMotor.setPower(0);
                rightMotor.setPower(0);
            }
            else {
                //Set motor power to the starting powers

                if ((runtime.seconds()>turnTime)||(runtime.seconds()<waitTime)) {
                    leftMotor.setPower(0);
                    rightMotor.setPower(-1);
                    turnTime = turnRate+(int)runtime.seconds();
                    waitTime = (float)runtime.seconds()+waitingTime;
                }
                else {
                    leftMotor.setPower(-1);
                    rightMotor.setPower(1);
                }
            }

            idle();
        }
    }
}
