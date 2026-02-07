package utils;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Validation utility class using lambda expressions.
 * Demonstrates functional programming with Predicates.
 * Follows SRP - only handles validation logic.
 */
public class ValidationUtils {

    // Lambda expressions for common validations
    public static final Predicate<String> IS_NOT_EMPTY = s -> s != null && !s.trim().isEmpty();

    public static final Predicate<String> IS_VALID_USERNAME = username ->
            username != null && username.length() >= 3 && username.length() <= 50
                    && username.matches("^[a-zA-Z0-9_]+$");

    public static final Predicate<String> IS_VALID_PASSWORD = password ->
            password != null && password.length() >= 4;

    public static final Predicate<Double> IS_POSITIVE_PRICE = price ->
            price != null && price > 0;

    public static final Predicate<Integer> IS_POSITIVE_QUANTITY = quantity ->
            quantity != null && quantity > 0;

    public static final Predicate<Integer> IS_NON_NEGATIVE = value ->
            value != null && value >= 0;

    public static final Predicate<String> IS_VALID_ITEM_NAME = name ->
            name != null && name.length() >= 2 && name.length() <= 100;

    /**
     * Validates input using a predicate and throws exception if invalid.
     */
    public static <T> void validate(T value, Predicate<T> validator, String errorMessage) throws ValidationException {
        if (!validator.test(value)) {
            throw new ValidationException(errorMessage);
        }
    }

    /**
     * Validates multiple conditions using lambda expressions.
     */
    public static void validateAll(ValidationRule... rules) throws ValidationException {
        for (ValidationRule rule : rules) {
            if (!rule.validator.getAsBoolean()) {
                throw new ValidationException(rule.errorMessage);
            }
        }
    }

    /**
     * Helper class for validation rules.
     */
    public static class ValidationRule {
        private final java.util.function.BooleanSupplier validator;
        private final String errorMessage;

        public ValidationRule(java.util.function.BooleanSupplier validator, String errorMessage) {
            this.validator = validator;
            this.errorMessage = errorMessage;
        }
    }

    /**
     * Custom validation exception.
     */
    public static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }
}