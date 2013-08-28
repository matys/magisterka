package pl.edu.agh.mabics.agents.implementation.classifying;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 15.07.13
 * Time: 23:28
 */
public class MyClassifier {

    List<Double[]> previousExamples = new ArrayList<Double[]>();
    Classifier classifier;
    private FastVector stateFeatures;
    private int updateCounter;
    private Instances trainingSet;
    private IReducedStatesEnum[] indexToClassMap;
    private Integer updateFrequencyInNbrOfExamples;
    private Integer numberOfStateAttributes;
    private IStatesComparator statesComparator;

    public <E extends IReducedStatesEnum> MyClassifier(Class<E> enumData, WekaClassifiers wekaClassifier,
                                                       IStatesComparator<E> comparator, Integer numberOfStateAttributes,
                                                       Integer updateFrequencyInNbrOfExamples) {
        this.statesComparator = comparator;
        this.numberOfStateAttributes = numberOfStateAttributes;
        this.updateFrequencyInNbrOfExamples = updateFrequencyInNbrOfExamples;
        restartUpdateCounter();
        classifier = wekaClassifier.getClassifier();

        prepareFeatures(enumData, numberOfStateAttributes);

        trainingSet = new Instances("examples", stateFeatures, 1000);
        trainingSet.setClassIndex(numberOfStateAttributes);     //counting from 0
    }

    private <E extends IReducedStatesEnum> void prepareFeatures(Class<E> enumData, Integer numberOfStateAttributes) {
        stateFeatures = new FastVector(numberOfStateAttributes + 1);//plus one for class
        for (int i = 0; i < numberOfStateAttributes; i++) {
            stateFeatures.addElement(new Attribute("attribute" + i));
        }
        stateFeatures.addElement(prepareClasses(enumData));
    }

    private <E extends IReducedStatesEnum> Attribute prepareClasses(Class<E> enumData) {
        FastVector stateReductionClasses = new FastVector(enumData.getEnumConstants().length);
        int i = 0;
        indexToClassMap = new IReducedStatesEnum[enumData.getEnumConstants().length];
        for (E clazz : enumData.getEnumConstants()) {
            stateReductionClasses.addElement(clazz.getStringRepresentation());
            indexToClassMap[i] = clazz;
            i++;
        }
        return new Attribute("theClass", stateReductionClasses);
    }

    private void restartUpdateCounter() {
        updateCounter = updateFrequencyInNbrOfExamples;
    }

    public void addState(Double[] state) {
        previousExamples.add(state);
    }

    public void usePreviousExamplesAs(IReducedStatesEnum className) {
        if (className == PositiveNegativeReducedStates.POSITIVE) {
            System.out.println("as positive: ");
        } else {
            System.out.println("as negative: ");
        }
        for (Double[] e : previousExamples) {
            System.out.println("(" + e[0] + ", " + e[1] + ", " + e[2] + ", " + e[3] + ")");
        }
        addPreviousExamplesToTrainingSet(className.getStringRepresentation());
    }

    private void addPreviousExamplesToTrainingSet(String classLabel) {
        for (Double[] example : previousExamples) {
            trainingSet.add(makeInstance(classLabel, example, trainingSet));
        }
        updateCounter -= previousExamples.size();
        try {
            if (updateCounter <= 0) {
                classifier.buildClassifier(trainingSet);
                restartUpdateCounter();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        previousExamples.clear();
    }

    private Instance makeInstance(String classLabel, Double[] example, Instances set) {
        Instance instance = new Instance(numberOfStateAttributes + 1); //for class attribute
        for (int i = 0; i < numberOfStateAttributes; i++) {
            instance.setValue((Attribute) stateFeatures.elementAt(i), example[i]);
        }
        if (classLabel != null)
            instance.setValue((Attribute) stateFeatures.elementAt(numberOfStateAttributes), classLabel);
        instance.setDataset(set);
        return instance;
    }

    public IReducedStatesEnum reduce(Double[] state) throws ClassifierException {
        Instances testSet = trainingSet.stringFreeStructure();
        Instance testInstance = makeInstance(null, state, testSet);

        // Filter instance.
        try {
            double[] results = classifier.distributionForInstance(testInstance);
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + results[0] + "           " + results[1]);
            return getBestState(results);
        } catch (Exception e) {
            throw new ClassifierException("Classifier not ready yet");
        }
    }

    private IReducedStatesEnum getBestState(double[] results) {
        IReducedStatesEnum bestState = indexToClassMap[0];
        double bestResult = results[0];
        for (int i = 1; i < indexToClassMap.length; i++) {
            IReducedStatesEnum state_i = indexToClassMap[i];
            double value_i = results[i];
            if (statesComparator.compare(bestState, bestResult, state_i, value_i) < 0) {
                bestState = state_i;
                bestResult = value_i;
            }
        }
        return bestState;
    }

    private int getIndexOfTheMostProbable(double[] doubles) {
        int maxIndex = 0;
        double max = 0;
        for (int i = 0; i < doubles.length; i++) {
            if (doubles[i] > max) {
                max = doubles[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }


    public void removeNotClassifiedExamples() {
        System.out.println("clearing examples!!!");
        previousExamples.clear();
    }

    public static void main(String[] args) {
        MyClassifier myClassifier = new MyClassifier(PositiveNegativeReducedStates.class, WekaClassifiers.C45,
                new PositiveNegativeStateComparator(1), 1, 6);
        myClassifier.addState(new Double[]{0.1});
        myClassifier.addState(new Double[]{0.2});
        myClassifier.addState(new Double[]{0.15});
        myClassifier.addState(new Double[]{0.1});
        myClassifier.addState(new Double[]{0.2});
        myClassifier.addState(new Double[]{0.15});
        myClassifier.addState(new Double[]{0.1});
        myClassifier.addState(new Double[]{0.2});
        myClassifier.addState(new Double[]{0.15});
        myClassifier.addState(new Double[]{0.1});
        myClassifier.addState(new Double[]{0.2});
        myClassifier.addState(new Double[]{0.15});
        myClassifier.addState(new Double[]{0.1});
        myClassifier.addState(new Double[]{0.2});
        myClassifier.addState(new Double[]{0.15});
        myClassifier.usePreviousExamplesAs(PositiveNegativeReducedStates.NEGATIVE);
        myClassifier.addState(new Double[]{0.8});
        myClassifier.addState(new Double[]{0.9});
        myClassifier.addState(new Double[]{0.85});
        myClassifier.addState(new Double[]{0.8});
        myClassifier.addState(new Double[]{0.9});
        myClassifier.addState(new Double[]{0.85});
        myClassifier.addState(new Double[]{0.8});
        myClassifier.addState(new Double[]{0.9});
        myClassifier.addState(new Double[]{0.85});
        myClassifier.addState(new Double[]{0.8});
        myClassifier.addState(new Double[]{0.9});
        myClassifier.addState(new Double[]{0.85});
        myClassifier.addState(new Double[]{0.8});
        myClassifier.addState(new Double[]{0.9});
        myClassifier.addState(new Double[]{0.85});
        myClassifier.usePreviousExamplesAs(PositiveNegativeReducedStates.POSITIVE);
        IReducedStatesEnum reducedState = null;
        try {
            reducedState = myClassifier.reduce(new Double[]{0.19});
            System.out.println(reducedState.getStringRepresentation());
        } catch (ClassifierException e) {
            e.printStackTrace();
        }
    }

    public <E extends IReducedStatesEnum> Double[] chooseTheBest(List<Double[]> possibleDecisions, E requiredClass)
            throws Exception {
        Double[] bestDecision = null;
        double bestValue = -1;
        int requiredClassIndex = getIndexOfRequiredClass(requiredClass);
        for (Double[] decision : possibleDecisions) {
            Instances testSet = trainingSet.stringFreeStructure();
            Instance testInstance = makeInstance(null, decision, testSet);
            double requiredClassValue = classifier.distributionForInstance(testInstance)[requiredClassIndex];
            if (requiredClassValue > bestValue) {
                bestDecision = decision;
                bestValue = requiredClassValue;
            }
        }
        return bestDecision;
    }

    private <E extends IReducedStatesEnum> int getIndexOfRequiredClass(E requiredState) {
        int i = 0;
        for (IReducedStatesEnum clazz : indexToClassMap) {
            if (clazz == requiredState) {
                return i;
            }
            i++;
        }
        return i;
    }


    public class ClassifierException extends Exception {
        public ClassifierException(String s) {
            super(s);
        }

    }

    public void setComparator(IStatesComparator comparator) {
        this.statesComparator = comparator;
    }
}
