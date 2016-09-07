package rendering;

import objects.powerups.PowerupFactory;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import scenes.IScene;
import system.IServiceLocator;

import static org.lwjgl.Sys.getTime;

public final class Renderer implements IRenderer {
    private static transient IServiceLocator serviceLocator;
    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        serviceLocator.provide(new Renderer());
    }

    /** position of quad */
    float x = 400, y = 300;
    /** angle of quad rotation */
    float rotation = 0;

    /** time at last frame */
    long lastFrame;

    /** frames per second */
    int fps;
    /** last fps time */
    long lastFPS;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private IScene currentScene;

    private Renderer() {

    }

    /** {@inheritDoc} */
    @Override
    public void setScene(IScene currentScene) {
        assert currentScene != null;
        this.currentScene = currentScene;
    }

    @Override
    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, WIDTH, 0, HEIGHT, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        getDelta();
        lastFPS = getTime();

        while (!Display.isCloseRequested()) {
            // TODO This should be more precise
            int delta = getDelta();

            update(delta);
            render();

            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }

    /**
      * Calculate how many milliseconds have passed
      * since last frame.
      *
      * @return milliseconds passed since last frame
      */
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    private void update(int delta) {

    }

    private void render() {
        // Clear The Screen And The Depth Buffer
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // R,G,B,A Set The Color To Blue One Time Only
        GL11.glColor3f(0.5f, 0.5f, 1.0f);

        // draw quad
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0);
        GL11.glRotatef(rotation, 0f, 0f, 1f);
        GL11.glTranslatef(-x, -y, 0);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x - 50, y - 50);
        GL11.glVertex2f(x + 50, y - 50);
        GL11.glVertex2f(x + 50, y + 50);
        GL11.glVertex2f(x - 50, y + 50);
        GL11.glEnd();
        GL11.glPopMatrix();
    }
}
