package scenes;

import buttons.IButton;
import cucumber.api.java8.En;
import objects.doodles.IDoodle;
import org.powermock.reflect.Whitebox;
import system.Game;
import system.IServiceLocator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                    List<IButton> buttons = (List<IButton>) Whitebox.getInternalState(scene, "buttons");
                    Object action = Whitebox.getInternalState(buttons.get(0), "action");
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
            Set<IDoodle> doodles = (Set<IDoodle>) Whitebox.getInternalState(scene, "doodles");
            for (IDoodle doodle : doodles) {
                assertThat(Whitebox.getInternalState(doodle, "score"), is(scoreDouble));
            }
        });
    }
}
