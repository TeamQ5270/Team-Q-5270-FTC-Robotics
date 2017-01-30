package org.firstinspires.ftc.teamcode;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;


//DECLARE TELEOP
@Autonomous(name="Teleop Driving Playback Blue", group="Linear Opmode")  // @Autonomous(...) is the other common choice
public class BasicTankDriveReplayBlue extends LinearOpMode {


    //TIMEOUT
    private int timeout = 0; //Timeout flag, to make sure that inversion does not happen over and over

    //LIMITING FLAG FOR SPEED
    private int speedLimitingFlag = 2; //The code DIVIDES by this

    //INVERSION
    private int inverted = 1; //Inversion flag, to make the motors run inverted on the press of a button

    //TIMER
    private ElapsedTime runtime = new ElapsedTime(); //Timer

    //DRIVING
    private DcMotor leftMotorDriving = null; //This is the left driving motor
    private DcMotor rightMotorDriving = null; //This is the right driving motor

    float nextTime;


    String line;
    String[] lineParsed;
    BufferedReader br;


    @Override
    public void runOpMode() throws InterruptedException {
        try
        {
            File sdCard = Environment.getExternalStorageDirectory();
            File file = new File(sdCard,"recorderBlue.txt");
            br = new BufferedReader(new FileReader(file));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //DECLARE TELEMETRY
        telemetry.addData("Status", "Program Initialized");
        telemetry.update();

        //DRIVING MOTORS
        //set variables to motors
        leftMotorDriving  = hardwareMap.dcMotor.get("left motor"); //This is the left driving motor
        rightMotorDriving = hardwareMap.dcMotor.get("right motor"); //This is the right driving motor
        //set variables to mode
        leftMotorDriving.setDirection((DcMotor.Direction.FORWARD)); //This is the left motor driving being set to Forwards mode
        rightMotorDriving.setDirection((DcMotor.Direction.FORWARD)); //This is the right driving motor being set to Forwards mode



        //OPMODE CODE
        waitForStart();  //Wait for the game to start
        runtime.reset(); //Reset the timer before it is supposed to start



        nextTime = (int)runtime.milliseconds()+10;

        //OPMODE RUNNING
        while (opModeIsActive()) { //While opmode is running
            if (runtime.milliseconds()>nextTime) {
                nextTime = (int)runtime.milliseconds()+30;

                try {
                    line = br.readLine();
                    lineParsed = line.split(":");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                //DRIVING CONTROL CODE
                //This will make the robot move
                leftMotorDriving.setPower((-inverted*Double.valueOf(lineParsed[0]))/speedLimitingFlag);  //Set the left motor's power to be that of the gamepad's left stick
                rightMotorDriving.setPower((inverted*Double.valueOf(lineParsed[1]))/speedLimitingFlag); //Set the right motor's power to be that of the gamepad's right stick
                telemetry.update();
            }


            //OPMODE DEFAULT CODE
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
            //Datalog
            telemetry.update();                                             //Pushes to terminal
        }
    }
}
