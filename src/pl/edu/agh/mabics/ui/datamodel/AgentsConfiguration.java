package pl.edu.agh.mabics.ui.datamodel;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 24.12.12
 * Time: 14:50
 */
public class AgentsConfiguration {
    private boolean performParametersSearch;
    private boolean generateForEveryGame;
    private OneSideConfiguration leftSideConfiguration = new OneSideConfiguration();
    private OneSideConfiguration downSideConfiguration = new OneSideConfiguration();

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
}
