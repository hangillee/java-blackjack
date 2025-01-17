package view;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class InputView {
    public static final String INVALID_COMMAND_MESSAGE = "잘못된 명령어 입력입니다.";
    private static final String INVALID_DELIMITER_MESSAGE = "잘못된 구분자 입력입니다.";
    private static final Scanner scanner = new Scanner(System.in);

    private InputView() {
    }

    public static List<String> enterPlayerNames() {
        System.out.println("게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)");
        String input = scanner.nextLine();
        validatePlayerNames(input);
        return Arrays.asList(input.split(","));
    }

    private static void validatePlayerNames(final String input) {
        isValidDelimiter(input);
        List<String> names = Arrays.asList(input.split(","));
        isNamesDuplicated(names);
    }

    private static void isValidDelimiter(final String input) {
        isInputStartWithComma(input);
        isInputEndWithComma(input);
        isInputWithEmptyName(input);
    }

    private static void isInputStartWithComma(final String input) {
        if (input.startsWith(",")) {
            throw new IllegalArgumentException(INVALID_DELIMITER_MESSAGE);
        }
    }

    private static void isInputEndWithComma(final String input) {
        if (input.endsWith(",")) {
            throw new IllegalArgumentException(INVALID_DELIMITER_MESSAGE);
        }
    }

    private static void isInputWithEmptyName(final String input) {
        if (input.contains(",,")) {
            throw new IllegalArgumentException(INVALID_DELIMITER_MESSAGE);
        }
    }

    private static void isNamesDuplicated(final List<String> names) {
        Set<String> distinctNames = new HashSet<>(names);
        if (names.size() != distinctNames.size()) {
            throw new IllegalArgumentException("중복된 플레이어 이름입니다.");
        }
    }

    public static String decideToGetMoreCard(final String name) {
        System.out.println(name + "는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)");
        String commandValue = scanner.nextLine();
        validateCommandValue(commandValue);

        return commandValue;
    }

    private static void validateCommandValue(final String commandValue) {
        isCommandValueNotNull(commandValue);
        isCommandValueNotEmpty(commandValue);
        isCommandValueNotBlank(commandValue);
        isCommandValueValidInput(commandValue);
    }

    private static void isCommandValueNotNull(final String commandValue) {
        if (commandValue == null) {
            throw new IllegalArgumentException(INVALID_COMMAND_MESSAGE);
        }
    }

    private static void isCommandValueNotEmpty(final String commandValue) {
        if (commandValue.isEmpty()) {
            throw new IllegalArgumentException(INVALID_COMMAND_MESSAGE);
        }
    }

    private static void isCommandValueNotBlank(final String commandValue) {
        if (commandValue.isBlank()) {
            throw new IllegalArgumentException(INVALID_COMMAND_MESSAGE);
        }
    }

    private static void isCommandValueValidInput(final String commandValue) {
        if (!commandValue.equals("y") && !commandValue.equals("n")) {
            throw new IllegalArgumentException(INVALID_COMMAND_MESSAGE);
        }
    }

    public static int enterGamerBettingAmounts(final String name) {
        System.out.println();
        System.out.println(name + "의 배팅 금액은?");
        int betAmount = Integer.parseInt(scanner.nextLine());
        validateBetAmount(betAmount);
        return betAmount;
    }

    private static void validateBetAmount(final int betAmount) {
        if (betAmount <= 0) {
            throw new IllegalArgumentException("배팅 금액은 0보다 많아야 합니다.");
        }
    }
}
