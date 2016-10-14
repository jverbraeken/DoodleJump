package math;

import objects.IGameObject;
import objects.blocks.platform.IPlatformFactory;
import objects.blocks.platform.PlatformFactory;
import objects.powerups.IPowerupFactory;
import system.IServiceLocator;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Keeps a List with weights and elements. Represented as
 * Doubles and Strings respectively. The getRandomElement
 * method will return a random element from this list,
 * according to the weights.
 */
public class GenerationSet implements IWeightsSet {

    private List<MyEntry<Double, String>> weights;
    private IServiceLocator serviceLocator;

    /**
     * Create and initialize a WeightsSet
     *
     * @param weights a set with the weights that have to be used.
     */
    public GenerationSet(IServiceLocator sL, List<Double> weights, List<String> elementType) {
        assert weights.size() == elementType.size();
        this.weights = sortWeightsMap(weights, elementType);

        this.serviceLocator = sL;
    }

    /**
     * Sort the weights by the key value double.
     */
    private List<MyEntry<Double, String>> sortWeightsMap(List<Double> weights, List<String> elementType) {
        double total = 0;
        List<MyEntry<Double, String>> sortedWeights = new ArrayList<>();
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
        IPlatformFactory platformFactory = serviceLocator.getPlatformFactory();
        IPowerupFactory powerupFactory = serviceLocator.getPowerupFactory();
        switch (objectName) {
            case ("normalPlatform"):
                return platformFactory.createPlatform(0, 0);
            case ("verticalMovingPlatform"):
                return platformFactory.createVertMovingPlatform(0, 0);
            case ("horizontalMovingPlatform"):
                return platformFactory.createHoriMovingPlatform(0, 0);
            case ("breakingPlatform"):
                return platformFactory.createBreakPlatform(0, 0);
            case ("spring"):
                return powerupFactory.createSpring(0,0);
            case ("trampoline"):
                return powerupFactory.createTrampoline(0,0);
            case ("jetpack"):
                return powerupFactory.createJetpack(0,0);
            case ("propellor"):
                return powerupFactory.createPropeller(0,0);
            case ("sizeUp"):
                return powerupFactory.createSizeUp(0,0);
            case ("sizeDown"):
                return powerupFactory.createSizeDown(0,0);
            case ("springShoes"):
                return powerupFactory.createSpringShoes(0,0);
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