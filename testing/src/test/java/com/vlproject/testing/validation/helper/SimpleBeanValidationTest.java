package com.vlproject.testing.validation.helper;

import com.vlproject.testing.entities.TestEntity;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleBeanValidationTest extends AbstractBeanValidationTest<TestEntity> {

    public SimpleBeanValidationTest(TestObjectWrapper<TestEntity> testObjectWrapper) {
        super(testObjectWrapper);
    }

    @Parameterized.Parameters
    public static Collection<TestObjectWrapper<TestEntity>> createTestSet() {
        return Stream.of(
                new TestObjectWrapper.Builder<>(TestEntity::new)
                .build()
        ).collect(Collectors.toSet());
    }
}
