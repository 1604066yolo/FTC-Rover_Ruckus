package org.firstinspires.ftc.teamcode.actions;

import com.qualcomm.robotcore.util.ElapsedTime;

public interface IAction {

    ElapsedTime actionRuntime = new ElapsedTime();

    boolean execute();

    void stop();

}
