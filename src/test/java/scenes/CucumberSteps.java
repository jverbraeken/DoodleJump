package scenes;

import cucumber.api.java8.En;
import org.powermock.reflect.Whitebox;
import system.Game;
import system.IServiceLocator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CucumberSteps implements En {
    private IServiceLocator sL;

    public CucumberSteps() {


        Given("^that the game is started$", () -> {
            sL = Whitebox.getInternalState(Game.class, "serviceLocator");
        });
        Given("^that the scene is (.*)$", (String scene) -> {
            switch (scene) {
                case "Menu":
                    Game.setScene(
                            sL.getSceneFactory().createMainMenu());
                    break;
            }
        });


        When("^I press the (.*)-button$", (String button) -> {
            switch (button) {
                case "play":
                    Object scene = Whitebox.getInternalState(Game.class, "scene");
                    Object playButton = Whitebox.getInternalState(scene, "playButton");
                    Object action = Whitebox.getInternalState(playButton, "action");
                    ((Runnable) action).run();
                    break;
            }
        });


        Then("^the scene should be (.*)$", (String scene) -> {
            switch (scene) {
                case "World":
                    assertThat(Whitebox.getInternalState(Game.class, "scene") instanceof World, is(true));
            }
        });
        Then("^the score should be (\\d+)", (String score) -> {
            double scoreDouble = Double.parseDouble(score);
            Object scene = Whitebox.getInternalState(Game.class, "scene");
            Object doodle = Whitebox.getInternalState(scene, "doodle");
            assertThat(Whitebox.getInternalState(doodle, "score"), is(scoreDouble));
        });
    }
}
