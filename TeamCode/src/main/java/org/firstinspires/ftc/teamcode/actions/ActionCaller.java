package org.firstinspires.ftc.teamcode.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActionCaller {

    private List<IAction> actions;

    public ActionCaller() {
        actions = new ArrayList<IAction>();
    }

    public void addAction(IAction action) {
        actions.add(action);
    }

    public void runActionsSeq() {
        Iterator<IAction> iter = actions.iterator();
        while (iter.hasNext()) {
            IAction action = iter.next();
            while (!action.execute());
            action.stop();
        }
    }

}
