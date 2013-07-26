package pl.edu.agh.mabics.agents.implementation.classifying;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 16.07.13
 * Time: 23:34
 */
public enum WekaClassifiers {
    C45 {
        @Override
        public Classifier getClassifier() {
            return new J48();
        }
    };

    abstract public Classifier getClassifier();
}
