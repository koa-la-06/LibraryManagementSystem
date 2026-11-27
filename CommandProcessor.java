public class CommandProcessor {
    private LibrarySystem library;
    private InputValidator validator;

    public CommandProcessor() {
        library = new LibrarySystem();
        validator = new InputValidator();
    }
}