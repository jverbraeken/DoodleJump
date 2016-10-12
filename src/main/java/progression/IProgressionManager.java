package progression;

import java.util.List;

public interface IProgressionManager extends IHeightObserver, IJetpackUsedObserver, IPropellerUsedObserver, ISizeDownUsedObserver, ISizeUpUserObserver, ISpringUsedObserver, ISpringShoesUsedObserver, ITrampolineJumpedObserver {
    void init();

    void addHighScore(String name, double score);

    List<HighScore> getHighscores();
}
