package com.revhire.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MockitoSetupTest {

    @Mock
    List<String> list;

    @Test
    public void testMockitoWorking() {
        when(list.size()).thenReturn(10);

        assertEquals(10, list.size());
    }
}

