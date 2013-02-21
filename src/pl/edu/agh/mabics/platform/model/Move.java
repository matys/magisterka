package pl.edu.agh.mabics.platform.model;

import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;

public class Move {
    Coordinates point;
    Vector velocity;
    MoveState state = MoveState.NOT_PROCESSED;

    //states notProcessed, Failed, Accepted
    //flow: najpierw wszystkie sa not processed, jak wszystkie zastana wybrane przez agentow, controller sprawdza czy
    //sa powtarzajace sie. Jesli nie, wszystkie accepted. Jesli tak wszystkie notProcessed, poza tymi,ktore sie pokrywaja - failed
    //agent w takim momencie musi podjac decyzje jeszcze raz i poprosic o accept controller, controller powtarza

    public Move(Coordinates point, Vector velocity) {
        this.point = point;
        this.velocity = velocity;
    }

    public Coordinates getPoint() {
        return point;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public MoveState getState() {
        return state;
    }

    public void setState(MoveState state) {
        this.state = state;
    }
}
