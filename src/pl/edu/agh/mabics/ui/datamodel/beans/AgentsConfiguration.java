package pl.edu.agh.mabics.ui.datamodel.beans;

import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 24.12.12
 * Time: 14:50
 */
@Component
public class AgentsConfiguration {
    private boolean performParametersSearch;
    private boolean generateForEveryGame;
    private OneSideConfiguration leftSideConfiguration;
    private OneSideConfiguration downSideConfiguration;
    private boolean leftSideOnlyStatistics;

    public void setPerformParametersSearch(boolean performParametersSearch) {
        this.performParametersSearch = performParametersSearch;
    }

    public void setGenerateForEveryGame(boolean generateForEveryGame) {
        this.generateForEveryGame = generateForEveryGame;
    }

    public boolean isPerformParametersSearch() {
        return performParametersSearch;
    }

    public boolean isGenerateForEveryGame() {
        return generateForEveryGame;
    }

    public OneSideConfiguration getLeftSideConfiguration() {
        return leftSideConfiguration;
    }

    public OneSideConfiguration getDownSideConfiguration() {
        return downSideConfiguration;
    }

    public void setLeftSideConfiguration(OneSideConfiguration leftSideConfiguration) {
        this.leftSideConfiguration = leftSideConfiguration;
    }

    public void setDownSideConfiguration(OneSideConfiguration downSideConfiguration) {
        this.downSideConfiguration = downSideConfiguration;
    }

    public void copyDataTo(AgentsConfiguration agentsConfiguration) {
        agentsConfiguration.setGenerateForEveryGame(isGenerateForEveryGame());
        agentsConfiguration.setPerformParametersSearch(isPerformParametersSearch());
        leftSideConfiguration.copyDataTo(agentsConfiguration.getLeftSideConfiguration());
        downSideConfiguration.copyDataTo(agentsConfiguration.getDownSideConfiguration());
        agentsConfiguration.setLeftSideOnlyStatistics(isLeftSideOnlyStatistics());
    }

    public boolean isLeftSideOnlyStatistics() {
        return leftSideOnlyStatistics;
    }

    public void setLeftSideOnlyStatistics(boolean leftSideOnlyStatistics) {
        this.leftSideOnlyStatistics = leftSideOnlyStatistics;
    }
}
