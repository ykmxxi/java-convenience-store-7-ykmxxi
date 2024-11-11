package store.presentation.view;

public enum Command {

    Y(true),
    N(false);

    private final boolean yesOrNo;

    Command(final boolean yesOrNo) {
        this.yesOrNo = yesOrNo;
    }

    public static boolean from(final String commandInput) {
        return Command.valueOf(commandInput.toUpperCase())
                .yesOrNo;
    }

}
