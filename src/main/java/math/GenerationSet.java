package math;

import groovy.lang.Tuple2;
import objects.IGameObject;
import objects.blocks.ElementTypes;
import objects.blocks.WeightsMap;
import objects.blocks.platform.IPlatformFactory;
import objects.powerups.IPowerupFactory;
import system.IServiceLocator;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Keeps a List with weights and elements. Represented as
 * Doubles and Strings respectively. The getRandomElement
 * method will return a random element from this list,
 * according to the weights.
 */
public class GenerationSet implements IWeightsSet {

    /**
     * The list with weights, it uses Key-Value pairs in it.
     */
    private List<Tuple2<Double, ElementTypes>> weights;
    /**
     * The serviceLocator of this game.
     */
    private final IServiceLocator serviceLocator;

    /**
     * Create and initialize a WeightsSet.
     *
     * @param sL the serviceLocator this class should use.
     * @param setType a string with what type of GenerationSet this has to be.
     */
    public GenerationSet(final IServiceLocator sL, String setType) {
        this.serviceLocator = sL;

        if (setType.equals("platforms")) {
            createAndSortPlatformSet();
        }
        else if (setType.equals("powerups")) {
            createAndSortPowerupSet();
        }

    }


    private void createAndSortPlatformSet() {
        List<Double> platWeights = Arrays.asList(
                WeightsMap.getWeight(ElementTypes.normalPlatform),
                WeightsMap.getWeight(ElementTypes.verticalMovingPlatform),
                WeightsMap.getWeight(ElementTypes.horizontalMovingPlatform),
                WeightsMap.getWeight(ElementTypes.breakingPlatform));
        List<ElementTypes> platforms = Arrays.asList(ElementTypes.normalPlatform,
                ElementTypes.verticalMovingPlatform,
                ElementTypes.horizontalMovingPlatform,
                ElementTypes.breakingPlatform);
        this.weights = sortWeightsMap(platWeights, platforms);
    }

    private void createAndSortPowerupSet() {
        List<Double> powerupWeights = Arrays.asList(
                WeightsMap.getWeight(ElementTypes.spring),
                WeightsMap.getWeight(ElementTypes.trampoline),
                WeightsMap.getWeight(ElementTypes.jetpack),
                WeightsMap.getWeight(ElementTypes.propellor),
                WeightsMap.getWeight(ElementTypes.sizeUp),
                WeightsMap.getWeight(ElementTypes.sizeDown),
                WeightsMap.getWeight(ElementTypes.springShoes));
        List<ElementTypes> powerups = Arrays.asList(ElementTypes.spring,
                ElementTypes.trampoline,
                ElementTypes.jetpack,
                ElementTypes.propellor,
                ElementTypes.sizeUp,
                ElementTypes.sizeDown,
                ElementTypes.springShoes);
        this.weights = sortWeightsMap(powerupWeights, powerups);
    }

    /**
     * Sort the weights by the key value double.
     * @param weights A set with the weights that have to be used.
     * @param elementType The list with strings of the element types.
     * @return A list of MyEntry's.
     */
    private List<Tuple2<Double, ElementTypes>> sortWeightsMap(List<Double> weights, List<ElementTypes> elementType) {
        double total = 0;
        List<Tuple2<Double, ElementTypes>> sortedWeights = new ArrayList<>();
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat formatter = (DecimalFormat) numberFormat;
        formatter.applyPattern("#.####");

        for (int i = 0; i < weights.size(); i++) {
            String s = formatter.format(total + weights.get(i));
            total = Double.parseDouble(s);
            sortedWeights.add(new Tuple2<>(total, elementType.get(i)));
            assert total <= 1;
        }
        return sortedWeights;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    final public IGameObject getRandomElement() {
        double randDouble = serviceLocator.getCalc().getRandomDouble(1);

        for (Tuple2<Double, ElementTypes> entry : weights) {
            if (entry.getFirst() >= randDouble) {
                return getGameObject(entry.getSecond());
            }
        }
        return null;
    }

    /**
     * Returns an instantiation of an IGameObject
     * @param elementType the enum as type of the object.
     * @return the wanted object as an IGameObject.
     */
    private IGameObject getGameObject(final ElementTypes elementType) {
        IPlatformFactory platformFactory = serviceLocator.getPlatformFactory();
        IPowerupFactory powerupFactory = serviceLocator.getPowerupFactory();
        switch (elementType) {
            case normalPlatform:
                return platformFactory.createPlatform(0, 0);
            case verticalMovingPlatform:
                return platformFactory.createVerticalMovingPlatform(0, 0);
            case horizontalMovingPlatform:
                return platformFactory.createHorizontalMovingPlatform(0, 0);
            case breakingPlatform:
                return platformFactory.createBreakPlatform(0, 0);
            case spring:
                return powerupFactory.createSpring(0, 0);
            case trampoline:
                return powerupFactory.createTrampoline(0, 0);
            case jetpack:
                return powerupFactory.createJetpack(0, 0);
            case propellor:
                return powerupFactory.createPropeller(0, 0);
            case sizeUp:
                return powerupFactory.createSizeUp(0, 0);
            case sizeDown:
                return powerupFactory.createSizeDown(0, 0);
            case springShoes:
                return powerupFactory.createSpringShoes(0, 0);
            default:
                return null;
        }
    }
}
