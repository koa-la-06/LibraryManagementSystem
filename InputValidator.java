public class InputValidator {
    public String[] validateAndSplit(String command) throws InvalidInputException {
        String trimmed = command.trim();
        if (trimmed.isEmpty()) {
            throw new InvalidInputException("empty command");
        }
        return trimmed.split("\\|", -1);
    }
    public void validateCommand(String[] parts) throws InvalidInputException {
        int expected;
        switch (parts[0]) {
            case "ADD_BOOK":
            case "ADD_MAGAZINE":
            case "ADD_REFERENCE":
            case "ADD_MEMBER":
                expected = 4;
                break;
            case "BORROW":
                expected = 3;
                break;
            case "RETURN":
            case "ADVANCE":
            case "SEARCH":
            case "LOANS":
                expected = 2;
                break;
            case "OVERDUE":
            case "INVENTORY":
                expected = 1;
                break;
            default:
                throw new InvalidInputException("unknown command " + parts[0]);
        }
        if (parts.length != expected) {
            throw new InvalidInputException("wrong number of fields for " + parts[0]);
        }
    }
    public int validateInteger(String value) throws InvalidInputException {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("invalid number: " + value);
        }
    }
}