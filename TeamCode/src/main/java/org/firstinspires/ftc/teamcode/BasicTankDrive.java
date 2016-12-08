package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Teleop Driving", group="Linear Opmode")  // @Autonomous(...) is the other common choice
public class BasicTankDrive extends LinearOpMode {



    int timeout = 0; //Timeout flag, to make sure that inversion does not happen over and over

    private ElapsedTime runtime = new ElapsedTime(); //Timer


    DcMotor leftMotorDriving = null; //This is the left driving motor
    DcMotor rightMotorDriving = null; //This is the right driving motor

    DcMotor capBallMotor1 = null; //This is one of the cap ball motors
    DcMotor capBallMotor2 = null; //This is one of the cap ball motors
    Servo leftServoCapBall = null; //This is one of the cap ball SERVOS
    Servo rightServoCapBall = null; //This is one of the cap ball SERVOS

    DcMotor ballShooterShooterMotor = null; //This is the ball shooter's shooter motor
    DcMotor ballShooterLiftMotor = null; //This is the ball shooter's lifter motor
    DcMotor ballShooterIntakeMotor = null; //This is the ball shooter's intake box motor

    BeaconButtonPushers leftServoBeacon; //This is the left beacon button pusher
    BeaconButtonPushers rightServoBeacon; //This is the right beacon button pusher



    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.addData("Status", "Initialized");
        telemetry.update();



        leftMotorDriving  = hardwareMap.dcMotor.get("left motor"); //This is the left driving motor
        rightMotorDriving = hardwareMap.dcMotor.get("right motor"); //This is the right driving motor

        leftMotorDriving.setDirection((DcMotor.Direction.FORWARD)); //This is the left motor driving being set to Forwards mode
        rightMotorDriving.setDirection((DcMotor.Direction.FORWARD)); //This is the right driving motor being set to Forwards mode



        capBallMotor1 = hardwareMap.dcMotor.get("cap ball motor 1"); //This is a capball motor
        capBallMotor2 = hardwareMap.dcMotor.get("cap ball motor 2"); //This is a capball motor
        leftServoCapBall = hardwareMap.servo.get("left servo"); //This is the left capball servo
        rightServoCapBall = hardwareMap.servo.get("right servo"); //This is the right capball servo

        capBallMotor1.setDirection((DcMotor.Direction.FORWARD)); //This is the first capball motor being set to forwards mode
        capBallMotor2.setDirection((DcMotor.Direction.FORWARD)); //This is the second capball motor being set to forwards mode
        leftServoCapBall.setPosition(0); //This is the left servo cap ball being set to starting position
        rightServoCapBall.setPosition(0); //This is the right servo cap ball being set to starting position



        ballShooterShooterMotor = hardwareMap.dcMotor.get("ball shooter shooter motor"); //This is the ball shooter's shooter motor
        ballShooterLiftMotor = hardwareMap.dcMotor.get("ball shooter lifter motor"); //Ball shooter lifter motor
        ballShooterIntakeMotor = hardwareMap.dcMotor.get("ball shooter intake motor"); //Ball shooter intake motor

        ballShooterShooterMotor.setDirection((DcMotor.Direction.FORWARD)); //This is the ball shooter's SHOOTER motor being set to Forwards mode
        ballShooterLiftMotor.setDirection((DcMotor.Direction.FORWARD)); //This is the ball shooter's LIFT motor being set to Forwards mode
        ballShooterIntakeMotor.setDirection((DcMotor.Direction.FORWARD)); //This is the ball shooter's INTAKE motor being set to Forwards mode


        leftServoBeacon = new BeaconButtonPushers("beacon left servo",0,150); //This is the left servo on the beacon button pusher
        rightServoBeacon = new BeaconButtonPushers("beacon right servo",0,150); //This is the right servo on the beacon button pusher



        boolean inverted = false; //Set up the inverted control flag. Defaults to false.

        waitForStart();  //Wait for the game to start
        runtime.reset(); //Reset the timer before it is supposed to start

        while (opModeIsActive()) { //While opmode is running

            //Datalogging!
            telemetry.addData("Status", "Run Time: " + runtime.toString()); //Tell the user how long the code has been running
            telemetry.update();                                             //Pushes to terminal

/*
            //Movement
            if (!inverted) { //Perform normally if not inverted
                leftMotor.setPower(-gamepad1.left_stick_y);  //Set the left motor's power to be that of the gamepad's left stick
                rightMotor.setPower(gamepad1.right_stick_y); //Set the right motor's power to be that of the gamepad's right stick
            }

            if (inverted) { //Perform with reverse inversion if inverted
                leftMotor.setPower(gamepad1.left_stick_y);    //Set the left motor's power to be that of the gamepad's left stick, except inverted.
                rightMotor.setPower(-gamepad1.right_stick_y); //Set the light motor's power to be that of the gamepad's right stick, except inverted.
            }

            //Toggle inversion if the invert button is pushed and the timeout is in the area that it needs to be in so that it can not be spammed.
            if (gamepad1.x&&timeout>=75) {         //If the inversion button is pushed
                inverted = !inverted; //make the inverted flag the oppposite of the inversion flag. INVERTS
                timeout=0; //This resets the timeout
            }

            //If the left servo toggle (the left trigger) is pushed
            if (gamepad1.left_trigger) {
              //toggle the servo's position
              leftServoBeacon.togglePosition();
            }
            //If the right servo toggle (the right trigger) is pushed
            if (gamepad1.right_trigger) {
              //toggle the servo's position
              rightServoBeacon.togglePosition();
            }


*/
            //This line increments the timeout for the inversion mode
            timeout++;

            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
    }
}
