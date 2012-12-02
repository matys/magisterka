package pl.edu.agh.mabics;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.edu.agh.mabics.agents.RandomAgent;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        BeanFactory factory = context;
        RandomAgent randomAgent = (RandomAgent) factory.getBean("randomAgent");
//        new RandomAgent();
    }
}
