package duke.parser;

import duke.command.*;
import duke.exception.InvalidCommandFormatException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static duke.common.Strings.*;

public class Parser {
    public static Command parseCommand(String[] input) {
        final String command = input[0];
        final String args = input[1];
        switch (command) {
        case COMMAND_BYE:
            return prepareBye(args);
        case COMMAND_LIST:
            return prepareList(args);
        case COMMAND_TODO:
            return prepareTodo(args);
        case COMMAND_DEADLINE:
            return prepareDeadline(args);
        case COMMAND_EVENT:
            return prepareEvent(args);
        case COMMAND_MARK:
            return prepareMark(args);
        case COMMAND_UNMARK:
            return prepareUnmark(args);
        case COMMAND_DELETE:
            return prepareDelete(args);
        default:
            return new InvalidCommand();
        }
    }

    private static Command prepareBye(String args) {
        try {
            if (!args.equals("")) {
                throw new InvalidCommandFormatException();
            }
            return new ByeCommand();
        } catch (InvalidCommandFormatException e) {
            return new InvalidCommand(COMMAND_BYE, USAGE_BYE);
        }
    }

    private static Command prepareList(String args) {
        try {
            if (args.equals("")) {
                return new ListCommand();
            }
            try {
                LocalDate queryDate = LocalDate.parse(args, DateTimeFormatter.ofPattern(FORMAT_DATE));
                return new ListCommand(queryDate);
            } catch (DateTimeParseException e) {
                throw new InvalidCommandFormatException();
            }
        } catch (InvalidCommandFormatException e) {
            return new InvalidCommand(COMMAND_LIST, USAGE_LIST, USAGE_LIST_DATE);
        }
    }

    private static Command prepareTodo(String args) {
        try {
            if (args.equals("")) {
                throw new InvalidCommandFormatException();
            }
            return new TodoCommand(args.trim());
        } catch (InvalidCommandFormatException e) {
            return new InvalidCommand(COMMAND_TODO, USAGE_TODO);
        }
    }

    private static Command prepareDeadline(String args) {
        try {
            String[] parsedArgs = args.trim().split(DEADLINE_SEPARATOR, 2);
            if (parsedArgs.length != 2) {
                throw new InvalidCommandFormatException();
            }
            final String taskDescription = parsedArgs[0].trim();
            final String taskDeadline = parsedArgs[1].trim();
            return new DeadlineCommand(taskDescription, taskDeadline);
        } catch (InvalidCommandFormatException e) {
            return new InvalidCommand(COMMAND_DEADLINE, USAGE_DEADLINE, true);
        }
    }

    private static Command prepareEvent(String args) {
        try {
            String[] parsedArgs = args.trim().split(EVENT_SEPARATOR, 2);
            if (parsedArgs.length != 2) {
                throw new InvalidCommandFormatException();
            }
            final String eventDescription = parsedArgs[0].trim();
            final String eventTime = parsedArgs[1].trim();
            return new EventCommand(eventDescription, eventTime);
        } catch (InvalidCommandFormatException e) {
            return new InvalidCommand(COMMAND_EVENT, USAGE_EVENT, true);
        }
    }

    public static Command prepareMark(String args) {
        try {
            int index = Integer.parseInt(args);
            return new MarkCommand(index);
        } catch (NumberFormatException e) {
            return new InvalidCommand(COMMAND_MARK, USAGE_MARK);
        }
    }

    public static Command prepareUnmark(String args) {
        try {
            int index = Integer.parseInt(args);
            return new UnmarkCommand(index);
        } catch (NumberFormatException e) {
            return new InvalidCommand(COMMAND_UNMARK, USAGE_UNMARK);
        }
    }

    public static Command prepareDelete(String args) {
        try {
            int index = Integer.parseInt(args);
            return new DeleteCommand(index);
        } catch (NumberFormatException e) {
            return new InvalidCommand(COMMAND_DELETE, USAGE_DELETE);
        }
    }
}
