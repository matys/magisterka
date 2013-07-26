package pl.edu.agh.mabics.agents.implementation.classifying;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 26.07.13
 * Time: 21:27
 */
public class PositiveNegativeStateComparator implements IStatesComparator<PositiveNegativeReducedStates> {

    private double negativeStatePriority;


    public PositiveNegativeStateComparator(double negativeStatePriority) {
        this.negativeStatePriority = negativeStatePriority;
    }

    @Override
    public double compare(PositiveNegativeReducedStates state1, double state1value,
                          PositiveNegativeReducedStates state2, double state2value) {
        if (state1.equals(state2)) {
            return state1value - state2value;
        } else if (state1.equals(PositiveNegativeReducedStates.POSITIVE)) {
            return state1value - state2value * negativeStatePriority;
        } else {
            return state1value * negativeStatePriority - state2value;
        }
    }
}
