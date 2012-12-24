package pl.edu.agh.mabics.ui.datamodel;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 24.12.12
 * Time: 15:18
 */
public class OneSideConfiguration {
    private Coordinates leftTopCornerCoordinates;
    private Coordinates rightDownCornerCoordinates;
    private Integer endLine;
    private Integer numberOfAgents;

    public Coordinates getLeftTopCornerCoordinates() {
        return leftTopCornerCoordinates;
    }

    public void setLeftTopCornerCoordinates(Coordinates leftTopCornerCoordinates) {
        this.leftTopCornerCoordinates = leftTopCornerCoordinates;
    }

    public Coordinates getRightDownCornerCoordinates() {
        return rightDownCornerCoordinates;
    }

    public void setRightDownCornerCoordinates(Coordinates rightDownCornerCoordinates) {
        this.rightDownCornerCoordinates = rightDownCornerCoordinates;
    }

    public Integer getEndLine() {
        return endLine;
    }

    public void setEndLine(Integer endLine) {
        this.endLine = endLine;
    }

    public Integer getNumberOfAgents() {
        return numberOfAgents;
    }

    public void setNumberOfAgents(Integer numberOfAgents) {
        this.numberOfAgents = numberOfAgents;
    }
}
