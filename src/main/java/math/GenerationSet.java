package math;

import objects.IGameObject;
import system.IServiceLocator;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class GenerationSet implements IWeightsSet {

    private ArrayList<MyEntry<Double, String>> weights;
    private IServiceLocator serviceLocator;

    /**
     * Create and initialize a WeightsSet
     *
     * @param weights a set with the weights that have to be used.
     */
    public GenerationSet(IServiceLocator sL, ArrayList<Double> weights, ArrayList<String> elementType) {
        assert weights.size() == elementType.size();
        this.weights = sortWeightsMap(weights, elementType);

        this.serviceLocator = sL;
    }

    /**
     * Sort the weights by the key value double.
     */
    private ArrayList<MyEntry<Double, String>> sortWeightsMap(ArrayList<Double> weights, ArrayList<String> elementType) {
        double total = 0;
        ArrayList<MyEntry<Double, String>> sortedWeights = new ArrayList<>();
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat formatter = (DecimalFormat) numberFormat;
        formatter.applyPattern("#.####");

        for (int i = 0; i < weights.size(); i++) {
            String s = formatter.format(total + weights.get(i));
            total = Double.parseDouble(s);
            sortedWeights.add(new MyEntry<>(total, elementType.get(i)));
            assert total <= 1;
        }
        return sortedWeights;
    }

    @Override
    public IGameObject getRandomElement() {
        double randDouble = serviceLocator.getCalc().getRandomDouble(1);

        for (MyEntry<Double, String> entry : weights) {
            if (entry.getKey() >= randDouble) {
                return getGameObject(entry.getValue());
            }
        }
        return null;
    }

    /**
     * Returns an instantiation of an IGameObject
     * @param objectName the name of the object.
     * @return the wanted object as an IGameObject.
     */
    private IGameObject getGameObject(String objectName) {
        switch (objectName) {
            case ("normalPlatform"):
                return serviceLocator.getPlatformFactory().createPlatform(0, 0);
            case ("verticalMovingPlatform"):
                return serviceLocator.getPlatformFactory().createVertMovingPlatform(0, 0);
            case ("horizontalMovingPlatform"):
                return serviceLocator.getPlatformFactory().createHoriMovingPlatform(0, 0);
            case ("breakingPlatform"):
                return serviceLocator.getPlatformFactory().createBreakPlatform(0, 0);
            case ("spring"):
                return serviceLocator.getPowerupFactory().createSpring(0,0);
            case ("trampoline"):
                return serviceLocator.getPowerupFactory().createTrampoline(0,0);
            case ("jetpack"):
                return serviceLocator.getPowerupFactory().createJetpack(0,0);
            case ("propellor"):
                return serviceLocator.getPowerupFactory().createPropeller(0,0);
            case ("sizeUp"):
                return serviceLocator.getPowerupFactory().createSizeUp(0,0);
            case ("sizeDown"):
                return serviceLocator.getPowerupFactory().createSizeDown(0,0);
            case ("springShoes"):
                return serviceLocator.getPowerupFactory().createSpringShoes(0,0);
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