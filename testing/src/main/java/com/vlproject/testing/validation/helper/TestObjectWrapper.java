package com.vlproject.testing.validation.helper;

import java.util.*;
import java.util.function.Supplier;

/**
 * Object Wrapper for testing. This is used to easily create a new object and
 * register all violations that have to shown up
 *
 * @param <T> Any object type
 */
public class TestObjectWrapper<T> {

    private final T objectUnderTest;

    private final Collection<String[]> violations;

    private final Class<?>[] validationClasses;

    private TestObjectWrapper(final T objectUnderTest,
                              final Collection<String[]> violations,
                              final Class<?>[] validationClasses) {
        this.objectUnderTest = objectUnderTest;
        this.violations = violations;
        this.validationClasses = validationClasses;
    }

    public T getObjectUnderTest() {
        return objectUnderTest;
    }

    public int getNumberOfViolations() {
        return violations.size();
    }

    public Collection<String[]> getViolations() {
        return violations;
    }

    public Class<?>[] getValidationClasses() {
        return validationClasses;
    }

    public static class Builder<T> {

        private final T objectToTest;

        private final Collection<String[]> violations;

        private final Set<Class<?>> validationClasses;

        public Builder(final Supplier<T> objectSupplier) {
            this.objectToTest = objectSupplier.get();
            this.violations = new ArrayList<>();
            this.validationClasses = new HashSet<>();
        }

        public TestObjectWrapper.Builder<T> addValidationClass(Class<?>... validationClasses) {
            this.validationClasses.addAll(Arrays.asList(validationClasses));
            return this;
        }

        public TestObjectWrapper.Builder<T> addViolation(String path, String code) {
            violations.add(new String[]{path, code});
            return this;
        }

        public TestObjectWrapper<T> build() {
            return new TestObjectWrapper<>(this.objectToTest,
                    this.violations,
                    validationClasses.toArray(new Class<?>[validationClasses.size()]));
        }
    }
}
