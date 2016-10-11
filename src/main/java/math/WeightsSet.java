package math;

import objects.IGameObject;
import system.IServiceLocator;

import java.util.*;

public class WeightsSet implements IWeightsSet {

    private ArrayList<MyEntry<Double, IGameObject>> weights;
    private IServiceLocator serviceLocator;

    /**
     * Create and initialize a WeightsSet
     *
     * @param weights a set with the weights that have to be used.
     */
    public WeightsSet(IServiceLocator sL, ArrayList<Double> weights, ArrayList<IGameObject> elementType) {
        assert weights.size() == elementType.size();
        this.weights = sortWeightsMap(weights, elementType);

        this.serviceLocator = sL;
    }

    /**
     * Sort the weights by the key value double.
     */
    private ArrayList<MyEntry<Double, IGameObject>> sortWeightsMap(ArrayList<Double> weights, ArrayList<IGameObject> elementType) {
        double total = 0;
        ArrayList<MyEntry<Double, IGameObject>> sortedWeights = new ArrayList<>();

        for (int i = 0; i < weights.size(); i++) {
            total = total + weights.get(i);
            sortedWeights.add(new MyEntry<>(total, elementType.get(i)));
            assert total <= 1;
        }
        return sortedWeights;
    }

    /**
     * Return a random IGameObject taken from this weightedSet.
     * @return a random IGameObject.
     */
    public IGameObject getRandomElement() {
        double randDouble = serviceLocator.getCalc().getRandomDouble(1);

        for (MyEntry<Double, IGameObject> entry : weights) {
            if (entry.getKey() <= randDouble) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * A MyEntry class keeps a Key and Value.
     * @param <K> the key.
     * @param <V> the value.
     */
    private final class MyEntry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;

        private MyEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }
    }
}