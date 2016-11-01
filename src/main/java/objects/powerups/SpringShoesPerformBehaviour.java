package objects.powerups;

public class SpringShoesPerformBehaviour {
    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends IDataOwner, IDataBoost, IDataMaxUses> void execute(final T data, final PowerupOccasion occasion) {
        if (this.owner == null) {
            throw new IllegalArgumentException("Owner cannot be null");
        }

        if (occasion == PowerupOccasion.collision) {
            this.uses += 1;
            this.owner.setVerticalSpeed(BOOST);

            if (this.uses >= MAX_USES) {
                this.owner.removePowerup(this);
                this.owner = null;
            }
        }
    }
}
