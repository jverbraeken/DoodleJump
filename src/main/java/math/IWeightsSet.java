package math;

import objects.IGameObject;

/**
 * An interface for the WeightsSet.
 */
/* package */ interface IWeightsSet {

    /**
     * Return a random IGameObject taken from this weightedSet.
     * @return a random IGameObject.
     */
    IGameObject getRandomElement();

}
