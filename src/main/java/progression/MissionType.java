package progression;

/**
 * Represents the types of missions we have.
 */
@SuppressWarnings("checkstyle:javadocvariable")
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

    /**
     * Creates a new mission type.
     * @param preText The text prepended to its message drawn at the pause menu
     * @param postText The text appended to its message drawn at the pause menu
     */
    MissionType(final String preText, final String postText) {
        this.preText = preText;
        this.postText = postText;
    }

    /**
     * Returns the message as it should be drawn at the pause menu
     * @param number The number of times the missio type should be executed
     * @return A String with the correct message of the mission type
     */
    public String getMessage(final int number) {
        return preText + number + postText;
    }
}
