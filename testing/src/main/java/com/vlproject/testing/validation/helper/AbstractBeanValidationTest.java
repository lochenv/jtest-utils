package com.vlproject.testing.validation.helper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(Parameterized.class)
public abstract class AbstractBeanValidationTest<T> {

    private Validator validator;

    private final TestObjectWrapper<T> testObjectWrapper;

    protected AbstractBeanValidationTest(TestObjectWrapper<T> testObjectWrapper) {
        this.testObjectWrapper = testObjectWrapper;
    }

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testCategoryBeanValidation() {
        Set<ConstraintViolation<T>> violations =
                validator.validate(
                        testObjectWrapper.getObjectUnderTest(),
                        testObjectWrapper.getValidationClasses());

        Assert.assertEquals(testObjectWrapper.getNumberOfViolations(), violations.size());

        Set<ConstraintViolationsEntry> violationsEntries
                = violations.stream()
                .map(this::createEntry)
                .collect(Collectors.toSet());
        Assert.assertTrue(testObjectWrapper.getViolations()
                .stream()
                .map(this::createEntry)
                .allMatch(violationsEntries::contains));
    }

    private ConstraintViolationsEntry createEntry(final String[] error) {
        return new ConstraintViolationsEntry(
                error[0],
                error[1]);
    }

    private ConstraintViolationsEntry createEntry(final ConstraintViolation<T> constraintViolation) {
        return new ConstraintViolationsEntry(
                constraintViolation.getPropertyPath().toString(),
                constraintViolation.getMessage());
    }

    static class ConstraintViolationsEntry {
        private final String path;

        private final String errorKey;

        ConstraintViolationsEntry(String path, String errorKey) {
            this.path = path;
            this.errorKey = errorKey;
        }

        public String getPath() {
            return path;
        }

        public String getErrorKey() {
            return errorKey;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ConstraintViolationsEntry that = (ConstraintViolationsEntry) o;
            return Objects.equals(path, that.path) &&
                    Objects.equals(errorKey, that.errorKey);
        }

        @Override
        public int hashCode() {
            return Objects.hash(path, errorKey);
        }
    }
}
