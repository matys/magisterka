package pl.edu.agh.mabics.agents;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.agents.implementation.AgentType;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 09.12.12
 * Time: 17:20
 */
@Service
public class AgentFactory implements BeanFactoryAware {

    private BeanFactory beanFactory;
    private Integer portCounter = 8000;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public AbstractAgent createAgent(AgentType type) {
        portCounter++;
        AbstractAgent newAgent = (AbstractAgent) beanFactory.getBean(type.getBeanName());
        newAgent.startAgent(portCounter);
        return newAgent;
    }

}
