package org.firstinspires.ftc.teamcode;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;


//DECLARE TELEOP
@TeleOp(name="Teleop Driving Recorder Red", group="Linear Opmode")  // @Autonomous(...) is the other common choice
public class BasicTankDriveRecorderRed extends LinearOpMode {

    PrintWriter pw;

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

    //PrintString
    String printingString = "";
    float nextTime = 0;




    @Override
    public void runOpMode() throws InterruptedException {

        try {
            //Open the HUG file
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath());
            dir.mkdirs();
            File file = new File(dir, "recorderRed.txt");
            FileOutputStream fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            // DECLARE TELEMETRY
            telemetry.addData("Status", "Program Initialized");
            telemetry.update();

            //DRIVING MOTORS
            //set variables to motors
            leftMotorDriving = hardwareMap.dcMotor.get("left motor"); //This is the left driving motor
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

                //DRIVING CONTROL CODE
                //This will make the robot move
                leftMotorDriving.setPower((-inverted * gamepad1.left_stick_y) / speedLimitingFlag);  //Set the left motor's power to be that of the gamepad's left stick
                rightMotorDriving.setPower((inverted * gamepad1.right_stick_y) / speedLimitingFlag); //Set the right motor's power to be that of the gamepad's right stick
                telemetry.addData("Left motor: ", -gamepad1.left_stick_y);
                telemetry.addData("Right motor: ", gamepad1.right_stick_y);
                telemetry.update();


                if (runtime.milliseconds()>nextTime) {
                    printingString = String.valueOf(gamepad1.left_stick_y) + ":" + String.valueOf(gamepad1.right_stick_y) + ":" + String.valueOf(gamepad1.left_bumper) + ":" + String.valueOf(gamepad1.right_bumper);
                    pw.println(printingString);
                    nextTime = (int)runtime.milliseconds()+30;
                }


                //OPMODE DEFAULT CODE
                idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
                //Datalog
                telemetry.update();                                             //Pushes to terminal

            }
        }
        catch(Exception e){
            //System.out.println("TH15 C0D3 H4S B33N H4CKE0 BY 12010");
            System.out.println("The error: " + e.toString());
        }
        pw.flush();
        pw.close();
    }
}
