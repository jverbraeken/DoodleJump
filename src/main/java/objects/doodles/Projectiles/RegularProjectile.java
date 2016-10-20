package objects.doodles.Projectiles;

import objects.AGameObject;
import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import system.IServiceLocator;

class RegularProjectile extends AGameObject {


    RegularProjectile(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y, sL.getSpriteFactory().getRegularProjectileSprite(), RegularProjectile.class);
    }

    @Override
    public void collidesWith(IDoodle doodle) {

    }

    @Override
    public void render() {
        getServiceLocator().getRenderer().drawSprite(this.getSprite(), (int) this.getXPos(), (int) this.getYPos());
    }

    @Override
    public void update(final double delta) {
        setYPos(getYPos() - 5);
    }
}
