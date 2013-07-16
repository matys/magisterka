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

    public <E extends IReducedStatesEnum> MyClassifier(Class<E> enumData, WekaClassifier wekaClassifier,
                                                       Integer numberOfStateAttributes,
                                                       Integer updateFrequencyInNbrOfExamples) {
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
        indexToClassMap = new PositiveNegativeReducedStates[enumData.getEnumConstants().length];
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

    public void usePreviousExamplesAs(PositiveNegativeReducedStates className) {
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
            return indexToClassMap[getIndexOfTheMostProbable(classifier.distributionForInstance(testInstance))];
        } catch (Exception e) {
            throw new ClassifierException("Classifier not ready yet");
        }
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


    public static void main(String[] args) {
        MyClassifier myClassifier = new MyClassifier(PositiveNegativeReducedStates.class, WekaClassifier.C45, 4, 7);
        myClassifier.addState(new Double[]{1.0, 1.0, 1.0, 0.0});
        myClassifier.addState(new Double[]{1.0, 1.0, 1.0, 0.0});
        myClassifier.addState(new Double[]{1.0, 1.0, 1.0, 0.0});
        myClassifier.usePreviousExamplesAs(PositiveNegativeReducedStates.NEGATIVE);
        myClassifier.addState(new Double[]{1.0, 1.0, 1.0, 1.0});
        myClassifier.addState(new Double[]{1.0, 1.0, 1.0, 1.0});
        myClassifier.addState(new Double[]{1.0, 1.0, 1.0, 1.0});
        myClassifier.addState(new Double[]{1.0, 1.0, 1.0, 1.0});
        myClassifier.usePreviousExamplesAs(PositiveNegativeReducedStates.POSITIVE);
        IReducedStatesEnum reducedState = null;
        try {
            reducedState = myClassifier.reduce(new Double[]{1.0, 1.0, 1.0, 0.0});
            System.out.println(reducedState.getStringRepresentation());
        } catch (ClassifierException e) {
            e.printStackTrace();
        }
    }


    public class ClassifierException extends Exception {
        public ClassifierException(String s) {
            super(s);
        }

    }
}
