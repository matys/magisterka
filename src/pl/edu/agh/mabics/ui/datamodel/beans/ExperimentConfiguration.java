package pl.edu.agh.mabics.ui.datamodel.beans;

import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 24.12.12
 * Time: 14:38
 */
@Component
public class ExperimentConfiguration {

    private boolean plotTimeOfIntersectionCrossingAverage;
    private boolean plotNumberOfCollision;
    private boolean plotTimeOfIntersection;
    private Integer numberOfSeries;
    private Integer samplingFrequency;
    private Integer numberOfGames;

    public boolean isPlotTimeOfIntersectionCrossingAverage() {
        return plotTimeOfIntersectionCrossingAverage;
    }

    public void setPlotTimeOfIntersectionCrossingAverage(boolean plotTimeOfIntersectionCrossingAverage) {
        this.plotTimeOfIntersectionCrossingAverage = plotTimeOfIntersectionCrossingAverage;
    }

    public boolean isPlotNumberOfCollision() {
        return plotNumberOfCollision;
    }

    public void setPlotNumberOfCollision(boolean plotNumberOfCollision) {
        this.plotNumberOfCollision = plotNumberOfCollision;
    }

    public boolean isPlotTimeOfIntersection() {
        return plotTimeOfIntersection;
    }

    public void setPlotTimeOfIntersection(boolean plotTimeOfIntersection) {
        this.plotTimeOfIntersection = plotTimeOfIntersection;
    }

    public Integer getNumberOfSeries() {
        return numberOfSeries;
    }

    public void setNumberOfSeries(Integer numberOfSeries) {
        this.numberOfSeries = numberOfSeries;
    }

    public Integer getSamplingFrequency() {
        return samplingFrequency;
    }

    public void setSamplingFrequency(Integer samplingFrequency) {
        this.samplingFrequency = samplingFrequency;
    }

    public Integer getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(Integer numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public void copyDataTo(ExperimentConfiguration experimentConfiguration) {
        experimentConfiguration.setNumberOfGames(getNumberOfGames());
        experimentConfiguration.setNumberOfSeries(getNumberOfSeries());
        experimentConfiguration.setPlotNumberOfCollision(isPlotNumberOfCollision());
        experimentConfiguration.setPlotTimeOfIntersection(isPlotTimeOfIntersection());
        experimentConfiguration.setPlotTimeOfIntersectionCrossingAverage(isPlotTimeOfIntersectionCrossingAverage());
        experimentConfiguration.setSamplingFrequency(getSamplingFrequency());
    }
}

