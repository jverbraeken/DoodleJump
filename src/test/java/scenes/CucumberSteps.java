package scenes;

import buttons.IButton;
import cucumber.api.java8.En;
import objects.doodles.IDoodle;
import org.powermock.reflect.Whitebox;
import system.Game;
import system.IServiceLocator;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CucumberSteps implements En {

    private IServiceLocator sL;

    public CucumberSteps() {
        Given("^that the game is started$", () -> {
            try {
                Whitebox.invokeConstructor(Game.class);
                sL = Whitebox.getInternalState(Game.class, "serviceLocator");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Given("^that the scene is (.*)$", (String scene) -> {
            switch (scene) {
                case "Menu":
                    Game.setScene(
                            sL.getSceneFactory().createMainMenu());
                    break;
                case "ChooseModeScreen":
                    Game.setScene(
                            sL.getSceneFactory().newChooseMode());
                    break;
                case "ScoreScreen":
                    Game.setScene(
                            sL.getSceneFactory().createScoreScreen());
                    break;
            }
        });

        When("^I do nothing$", () -> {
        });

        When("^I press the (.*)-button$", (String button) -> {
            Object scene = Whitebox.getInternalState(Game.class, "scene");
            List<IButton> buttons = (List<IButton>) Whitebox.getInternalState(scene, "buttons");
            switch (button) {
                //MENU
                case "play":
                    Object action = Whitebox.getInternalState(buttons.get(0), "action");
                    ((Runnable) action).run();
                    break;
                case "mode":
                    Object action3 = Whitebox.getInternalState(buttons.get(3), "action");
                    ((Runnable) action3).run();
                    break;
                case "scorescreen":
                    Object action1 = Whitebox.getInternalState(buttons.get(1), "action");
                    ((Runnable) action1).run();
                    break;
                case "multiplayer":
                    Object action2 = Whitebox.getInternalState(buttons.get(2), "action");
                    ((Runnable) action2).run();
                    break;
                //CHOOSEMODE
                case "menu":
                    Object action00 = Whitebox.getInternalState(buttons.get(0), "action");
                    ((Runnable) action00).run();
                    break;
                case "darkness":
                    Object action01 = Whitebox.getInternalState(buttons.get(1), "action");
                    ((Runnable) action01).run();
                    break;
                case "story":
                    Object action02 = Whitebox.getInternalState(buttons.get(2), "action");
                    ((Runnable) action02).run();
                    break;
                case "space":
                    Object action03 = Whitebox.getInternalState(buttons.get(3), "action");
                    ((Runnable) action03).run();
                    break;
                case "underwater":
                    Object action04 = Whitebox.getInternalState(buttons.get(4), "action");
                    ((Runnable) action04).run();
                    break;
                case "invert":
                    Object action05 = Whitebox.getInternalState(buttons.get(5), "action");
                    ((Runnable) action05).run();
                    break;
                case "regular":
                    Object action06 = Whitebox.getInternalState(buttons.get(6), "action");
                    ((Runnable) action06).run();
                    break;
            }
        });


        Then("^the scene should be (.*)$", (String scene) -> {
            switch (scene) {
                case "World":
                    assertThat(Whitebox.getInternalState(Game.class, "scene") instanceof World, is(true));
                    break;
                case "ChooseModeScreen":
                    assertThat(Whitebox.getInternalState(Game.class, "scene") instanceof ChooseModeScreen, is(true));
                    break;
                case "ScoreScreen":
                    assertThat(Whitebox.getInternalState(Game.class, "scene") instanceof ScoreScreen, is(true));
                    break;
                case "Menu":
                    assertThat(Whitebox.getInternalState(Game.class, "scene") instanceof Menu, is(true));
                    break;
            }
        });

        Then("^the score should be (\\d+)", (String score) -> {
            double scoreDouble = Double.parseDouble(score);
            Object scene = Whitebox.getInternalState(Game.class, "scene");
            List<IDoodle> doodles = (List<IDoodle>) Whitebox.getInternalState(scene, "doodles");
            for (IDoodle doodle : doodles) {
                assertThat(Whitebox.getInternalState(doodle, "score"), is(scoreDouble));
            }
        });

        Then("^the mode should be (.*)$", (String mode) -> {
            switch (mode) {
                case "regular":
                    assertThat(Whitebox.getInternalState(Game.class, "mode"), is(Game.Modes.regular));
                    break;
                case "space":
                    assertThat(Whitebox.getInternalState(Game.class, "mode"), is(Game.Modes.space));
                    break;
                case "underwater":
                    assertThat(Whitebox.getInternalState(Game.class, "mode"), is(Game.Modes.underwater));
                    break;
                case "darkness":
                    assertThat(Whitebox.getInternalState(Game.class, "mode"), is(Game.Modes.darkness));
                    break;
                case "invert":
                    assertThat(Whitebox.getInternalState(Game.class, "mode"), is(Game.Modes.invert));
                    break;
                case "story":
                    assertThat(Whitebox.getInternalState(Game.class, "mode"), is(Game.Modes.story));
                    break;
            }
        });


    }
}
