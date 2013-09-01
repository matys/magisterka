package pl.edu.agh.mabics.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 29.08.13
 * Time: 21:30
 */
public class RecoveryMain {

    public static void main(String[] args) {
        String directory = "zosia2\\serie0";
        String[] fullFilesToRecalculate = {"collisionsQuantityStatistic.txt", "timeAverageStatistic.txt", "timeFirstStatistic.txt", "timeLastStatistic.txt"};
        String[] serieFilesToRecalculate = {"collisionsQuantity.txt", "timeAverage.txt", "timeFirst.txt", "timeLast.txt"};
        String[] agentFilesToRecalculate = {"collisionsQuantityStatistic.txt", "timeStatistic.txt"};
        String[] agentSerieFilesToRecalculate = {"collisionsQuantity.txt", "time.txt"};
        Integer boxWidth = 200;
        String outputDirectory = "test";
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        StatisticsRecoverer statisticsRecoverer = (StatisticsRecoverer) context.getBean("statisticsRecoverer");
        statisticsRecoverer.recover(directory, boxWidth, directory, serieFilesToRecalculate);
    }
}
