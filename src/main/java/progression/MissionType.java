package progression;

/**
 * Represents the types of missions we have.
 */
public enum MissionType {
    jumpOnDisappearingPlatform("Jump on ", " disappearing platforms"),
    pickUpJetPack("Pick up ", " jetpacks"),
    pickupPropeller("Pick up ", " propellers"),
    pickupSizeDown("Pick up ", " Doodle Size Reducers"),
    pickupSizeUp("Pick up ", " Doodle Volumizers"),
    pickupSpringShoes("Pick up ", " spring shoes"),
    jumpOnSpring("Jump on ", " springs"),
    jumpOnTrampoline("Jump on ", " trampolines");

    private String preText;
    private String postText;
    MissionType(String preText, String postText) {
        this.preText = preText;
        this.postText = postText;
    }

    public String getMessage(int number) {
        return preText + number + postText;
    }
}
