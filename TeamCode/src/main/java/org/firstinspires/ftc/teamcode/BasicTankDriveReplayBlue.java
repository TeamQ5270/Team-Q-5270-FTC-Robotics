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
    private int inverted = -1; //Inversion flag, to make the motors run inverted on the press of a button

    //TIMER
    private ElapsedTime runtime = new ElapsedTime(); //Timer

    //DRIVING
    private DcMotor leftMotorDriving = null; //This is the left driving motor
    private DcMotor rightMotorDriving = null; //This is the right driving motor

    //CAPPING
    private DcMotor capBallMotor1 = null; //This is one of the cap ball motors
    private DcMotor capBallMotor2 = null; //This is one of the cap ball motors
    private Servo leftServoCapBall = null; //This is one of the cap ball SERVOS
    private Servo rightServoCapBall = null; //This is one of the cap ball SERVOS
    private double leftServoCapBallMin = 0.5d;
    private double leftServoCapBallMax = 0.1d;
    private boolean leftServoCapBallExtended = false;
    private double rightServoCapBallMin = 0.5d;
    private double rightServoCapBallMax = 0.1d;
    private boolean rightServoCapBallExtended = false;
    private float cappingSpeed = 1.00f; //This is the speed at which the capping motors will move
    private int cappingTimeout = 0;
    private boolean changed = true;


    //SHOOTING
    private DcMotor shooterMotor = null;
    private float shooterMotorPower = 1.0f;
    private DcMotor intakeMotorOne = null;
    private DcMotor intakeMotorTwo = null;
    private float shooterIntakePower = 0.5f;

    float nextTime;


    //BEACONBUTTONPUSHERS
    private Servo leftServoBeacon; //This is the left beacon button pusher
    private Servo rightServoBeacon; //This is the right beacon button pusher
    private double leftServoBeaconMin = 0.0d;
    private double leftServoBeaconMax = 1.0d;
    private boolean leftServoBeaconExtended = false;
    private double rightServoBeaconMin = 0.0d;
    private double rightServoBeaconMax = 1.0d;
    private boolean rightServoBeaconExtended = false;
    private int beaconTimeout = 0;
    private boolean beaconRan = false;

    Scanner readingFile;
    File readingFileFile;
    String line;
    String[] lineParsed;
    File sdCard;
    File file;
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
        /*

        //CAPPING MOTORS
        //set up capping motors
        capBallMotor1 = hardwareMap.dcMotor.get("cap ball motor 1"); //This is a capball motor
        capBallMotor2 = hardwareMap.dcMotor.get("cap ball motor 2"); //This is a capball motor
        //set up capping servos
        leftServoCapBall = hardwareMap.servo.get("cap ball left servo"); //This is the left capball servo
        rightServoCapBall = hardwareMap.servo.get("cap ball right servo"); //This is the right capball servo
        //set variables to mode
        capBallMotor1.setDirection((DcMotor.Direction.FORWARD)); //This is the first capball motor being set to forwards mode
        capBallMotor2.setDirection((DcMotor.Direction.FORWARD)); //This is the second capball motor being set to forwards mode



        //BALL SHOOTER MOTORS
        shooterMotor = hardwareMap.dcMotor.get("ball shooter shooter motor");
        intakeMotorOne = hardwareMap.dcMotor.get("ball shooter intake motor 1");
        //intakeMotorTwo = hardwareMap.dcMotor.get("ball shooter intake motor 2");



        //BEACONBUTTONPUSHERS
        //set beaconbuttonpushers to be their variables
        leftServoBeacon = hardwareMap.servo.get("left servo"); //This is the left servo on the beacon button pusher
        rightServoBeacon = hardwareMap.servo.get("right servo"); //This is the right servo on the beacon button pusher
        */


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
