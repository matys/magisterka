package pl.edu.agh.mabics.ui.datamodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormBean {

    private CoordinatesConverter coordinatesConverter;
    private CoordinatesReverseConverter coordinatesReverseConverter;

    private ExperimentConfiguration experimentConfiguration = new ExperimentConfiguration();
    private IntersectionConfiguration intersectionConfiguration = new IntersectionConfiguration();

    private AgentsConfiguration agentsConfiguration = new AgentsConfiguration();

    public FormBean() {
    }

    @Autowired
    public void setCoordinatesConverter(CoordinatesConverter coordinatesConverter) {
        this.coordinatesConverter = coordinatesConverter;
    }

    @Autowired
    public void setCoordinatesReverseConverter(CoordinatesReverseConverter coordinatesReverseConverter) {
        this.coordinatesReverseConverter = coordinatesReverseConverter;
    }

    public boolean isPlotTimeOfIntersectionCrossingAverage() {
        return experimentConfiguration.isPlotTimeOfIntersectionCrossingAverage();
    }

    public void setPlotTimeOfIntersectionCrossingAverage(final boolean plotTimeOfIntersectionCrossingAverage) {
        experimentConfiguration.setPlotTimeOfIntersectionCrossingAverage(plotTimeOfIntersectionCrossingAverage);
    }

    public boolean isPlotNumberOfCollision() {
        return experimentConfiguration.isPlotNumberOfCollision();
    }

    public void setPlotNumberOfCollision(final boolean plotNumberOfCollision) {
        experimentConfiguration.setPlotNumberOfCollision(plotNumberOfCollision);
    }

    public String getNumberOfSeries() {
        if (experimentConfiguration.getNumberOfSeries() != null) {
            return experimentConfiguration.getNumberOfSeries().toString();
        }
        return null;
    }

    public void setNumberOfSeries(final String numberOfSeries) {
        if (numberOfSeries != null) {
            experimentConfiguration.setNumberOfSeries(new Integer(numberOfSeries));
        } else {
            experimentConfiguration.setNumberOfSeries(null);
        }
    }

    public String getSamplingFrequency() {
        if (experimentConfiguration.getSamplingFrequency() != null) {
            return experimentConfiguration.getSamplingFrequency().toString();
        }
        return null;
    }

    public void setSamplingFrequency(final String samplingFrequency) {
        if (samplingFrequency != null) {
            experimentConfiguration.setSamplingFrequency(new Integer(samplingFrequency));
        } else {
            experimentConfiguration.setSamplingFrequency(null);
        }
    }

    public String getNumberOfGames() {
        if (experimentConfiguration.getNumberOfGames() != null) {
            return experimentConfiguration.getNumberOfGames().toString();
        }
        return null;
    }

    public void setNumberOfGames(final String numberOfGames) {
        if (numberOfGames != null) {
            experimentConfiguration.setNumberOfGames(new Integer(numberOfGames));
        } else {
            experimentConfiguration.setNumberOfGames(null);
        }
    }

    public boolean isPlotTimeOfIntersection() {
        return experimentConfiguration.isPlotTimeOfIntersection();
    }

    public void setPlotTimeOfIntersection(final boolean plotTimeOfIntersection) {
        experimentConfiguration.setPlotTimeOfIntersection(plotTimeOfIntersection);
    }

    public String getIntersectionFilePath() {
        return intersectionConfiguration.getIntersectionFilePath();
    }

    public void setIntersectionFilePath(final String intersectionFilePath) {
        intersectionConfiguration.setIntersectionFilePath(intersectionFilePath);
    }

    public String getLeftTopCornerDownCoordinates() {
        return coordinatesReverseConverter.convert(agentsConfiguration.getDownSideConfiguration().getLeftTopCornerCoordinates());
    }

    public void setLeftTopCornerDownCoordinates(final String leftTopCornerDownCoordinates) {
        agentsConfiguration.getDownSideConfiguration().setLeftTopCornerCoordinates(coordinatesConverter.convert(leftTopCornerDownCoordinates));
    }

    public String getRightDownCornerDownCoordinates() {
        return coordinatesReverseConverter.convert(agentsConfiguration.getDownSideConfiguration().getRightDownCornerCoordinates());
    }

    public void setRightDownCornerDownCoordinates(final String rightDownCornerDownCoordinates) {
        agentsConfiguration.getDownSideConfiguration().setRightDownCornerCoordinates(coordinatesConverter.convert(rightDownCornerDownCoordinates));
    }

    public String getLeftTopCornerLeftCoordinates() {
        return coordinatesReverseConverter.convert(agentsConfiguration.getLeftSideConfiguration().getLeftTopCornerCoordinates());
    }

    public void setLeftTopCornerLeftCoordinates(final String leftTopCornerLeftCoordinates) {
        agentsConfiguration.getLeftSideConfiguration().setLeftTopCornerCoordinates(coordinatesConverter.convert(leftTopCornerLeftCoordinates));
    }

    public String getRightDownCornerLeftCoordinates() {
        return coordinatesReverseConverter.convert(agentsConfiguration.getLeftSideConfiguration().getRightDownCornerCoordinates());
    }

    public void setRightDownCornerLeftCoordinates(final String rightDownCornerLeftCoordinates) {
        this.agentsConfiguration.getLeftSideConfiguration().setRightDownCornerCoordinates(coordinatesConverter.convert(rightDownCornerLeftCoordinates));
    }

    public String getEndLineLeft() {
        if (this.agentsConfiguration.getLeftSideConfiguration().getEndLine() != null) {
            return this.agentsConfiguration.getLeftSideConfiguration().getEndLine().toString();
        }
        return null;
    }

    public void setEndLineLeft(final String endLineLeft) {
        if (endLineLeft != null) {
            agentsConfiguration.getLeftSideConfiguration().setEndLine(new Integer(endLineLeft));
        } else {
            agentsConfiguration.getLeftSideConfiguration().setEndLine(null);
        }
    }

    public String getEndLineDown() {
        if (agentsConfiguration.getDownSideConfiguration().getEndLine() != null) {
            return agentsConfiguration.getDownSideConfiguration().getEndLine().toString();
        }
        return null;
    }

    public void setEndLineDown(final String endLineDown) {
        if (endLineDown != null) {
            agentsConfiguration.getDownSideConfiguration().setEndLine(new Integer(endLineDown));
        } else {
            agentsConfiguration.getDownSideConfiguration().setEndLine(null);
        }

    }

    public String getNumberOfAgentsLeft() {
        if (agentsConfiguration.getLeftSideConfiguration().getNumberOfAgents() != null)
            return agentsConfiguration.getLeftSideConfiguration().getNumberOfAgents().toString();
        return null;
    }

    public void setNumberOfAgentsLeft(final String numberOfAgentsLeft) {
        if (numberOfAgentsLeft != null)
            agentsConfiguration.getLeftSideConfiguration().setNumberOfAgents(new Integer(numberOfAgentsLeft));
        else
            agentsConfiguration.getLeftSideConfiguration().setNumberOfAgents(null);
    }

    public String getNumberOfAgentsDown() {
        if (agentsConfiguration.getDownSideConfiguration().getNumberOfAgents() != null) {
            return agentsConfiguration.getDownSideConfiguration().getNumberOfAgents().toString();
        }
        return null;
    }

    public void setNumberOfAgentsDown(final String numberOfAgentsDown) {
        if (numberOfAgentsDown != null) {
            agentsConfiguration.getDownSideConfiguration().setNumberOfAgents(new Integer(numberOfAgentsDown));
        } else {
            agentsConfiguration.getDownSideConfiguration().setNumberOfAgents(null);
        }
    }

    public boolean isPerformParametersSearch() {
        return agentsConfiguration.isPerformParametersSearch();
    }

    public void setPerformParametersSearch(final boolean performParametersSearch) {
        agentsConfiguration.setPerformParametersSearch(performParametersSearch);
    }

    public boolean isGenerateForEveryGame() {
        return agentsConfiguration.isGenerateForEveryGame();
    }

    public void setGenerateForEveryGame(final boolean generateForEveryGame) {
        agentsConfiguration.setGenerateForEveryGame(generateForEveryGame);
    }
}