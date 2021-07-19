package learn.solar.ui;

public enum MenuOption {
    EXIT("EXIT"),
    DISPLAY_SOLARS("Display Solars"),
    CREATE_SOLAR("Create Solar"),
    UPDATE_SOLAR("Update Solar"),
    DELETE_SOLAR("Delete Solar");

    private final String title;

    MenuOption(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
