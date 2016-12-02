/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Basic Tank Driving Mode", group="Linear Opmode")  // @Autonomous(...) is the other common choice
public class BasicTankDrive extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    DcMotor leftMotor = null;
    DcMotor rightMotor = null;
    DcMotor capBallMotor = null;
    DcMotor capBallMotor2 = null;
    DcMotor ballShooterMotor = null;
    Servo   leftServo = null;
    Servo   rightServo = null;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        leftMotor  = hardwareMap.dcMotor.get("left motor");
        rightMotor = hardwareMap.dcMotor.get("right motor");
        capBallMotor = hardwareMap.dcMotor.get("cap ball motor");
        capBallMotor2 = hardwareMap.dcMotor.get("cap ball motor2");
        ballShooterMotor = hardwareMap.dcMotor.get("ball shooter motor");
        leftServo = hardwareMap.servo.get("left servo");
        rightServo = hardwareMap.servo.get("right servo");

        leftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        capBallMotor.setDirection(DcMotor.Direction.FORWARD);
        capBallMotor2.setDirection(DcMotor.Direction.FORWARD);
        ballShooterMotor.setDirection(DcMotor.Direction.FORWARD);
        leftServo.setPosition(0);
        rightServo.setPosition(0);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

            leftMotor.setPower(-gamepad1.left_stick_y);
            rightMotor.setPower(gamepad1.right_stick_y);
            if(gamepad1.a) {
                capBallMotor.setPower(0.25f);
                capBallMotor2.setPower(0.25f);
            }
            if(gamepad1.b) {
                capBallMotor.setPower(0f);
                capBallMotor2.setPower(0f);
            }
            if(gamepad1.y) {
                ballShooterMotor.setPower(0.9f);
            }
            else {
                ballShooterMotor.setPower(0f);
            }
            if(gamepad1.left_trigger > 0.1) {
                leftServo.setPosition(0.9f);
            }else{
                leftServo.setPosition(0f);
            }
            if(gamepad1.right_trigger > 0.1) {
                rightServo.setPosition(0.9f);
            }else{
                rightServo.setPosition(0f);
            }
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
    }
}
