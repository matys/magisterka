package pl.edu.agh.mabics.agents.implementation.classifying;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 26.07.13
 * Time: 21:14
 */
public interface IStatesComparator<T extends IReducedStatesEnum> {

    public double compare(T state1, double state1value, T state2, double state2value);
}
