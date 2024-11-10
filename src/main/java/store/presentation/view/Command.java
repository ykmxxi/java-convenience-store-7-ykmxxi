package store.presentation.view;

public enum Command {

    Y(true),
    N(false);

    private final boolean yesOrNo;

    Command(final boolean yesOrNo) {
        this.yesOrNo = yesOrNo;
    }

    public static boolean isYes(final String commandInput) {
        return Command.valueOf(commandInput.toUpperCase())
                .yesOrNo;
    }

    public static boolean isNo(final String commandInput) {
        return Command.valueOf(commandInput.toUpperCase())
                .yesOrNo;
    }
}
