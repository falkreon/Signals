package com.thoughtcomplex.test;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.*;

import com.thoughtcomplex.signals.Slot;
import com.thoughtcomplex.state.FiniteStateMachine;
import com.thoughtcomplex.state.InvalidStateException;

public class StateMachineTests {
    
    enum TestState {
        BEGIN,
        CONTINUE,
        END,
        EXCEPTION
    }
    
    FiniteStateMachine<TestState> fsm;
    
    @Before
    public void setUp() throws Exception {
        fsm = new FiniteStateMachine<TestState>(
                TestState.BEGIN,
                TestState.CONTINUE,
                TestState.END);
    }
    
    @Test
    public void getAndSetState() {
        fsm.setState(TestState.CONTINUE);
        assertEquals(fsm.getState(), TestState.CONTINUE);
        
        fsm.setState(TestState.END);
        assertEquals(fsm.getState(), TestState.END);
        
        fsm.setState(TestState.BEGIN);
        assertEquals(fsm.getState(), TestState.BEGIN);
    }
    
    @Test(expected=InvalidStateException.class)
    public void stateException() {
        fsm.setState(TestState.EXCEPTION);
    }
    
    
    boolean stateMessageTriggered = false;
    
    @Slot
    public void receiveMessage(Object oldState, Object newState) {
        stateMessageTriggered = true;
    }
    
    @Test
    public void stateMessage() {
        FiniteStateMachine<String> fsm2 = new FiniteStateMachine<String>("A","B");
        fsm2.onStateChanged.connectTo(this, "receiveMessage");
        fsm2.setState("B");
        assertTrue(stateMessageTriggered);
    }
    
    @Test
    /**
     * Verify that the FSM constructs properly from an enum class, and that after construction
     * it contains a valid set of states.
     */
    public void addAllEnum() {
        FiniteStateMachine<TestState> fsm3 = new FiniteStateMachine<TestState>(TestState.BEGIN, TestState.class);
        fsm3.setState(TestState.CONTINUE);
        assertEquals(fsm3.getState(), TestState.CONTINUE);
    }
}
