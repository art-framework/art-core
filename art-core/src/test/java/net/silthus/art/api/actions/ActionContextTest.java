package net.silthus.art.api.actions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.not;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("ActionContext")
public class ActionContextTest {

    private Action<String, String> action;
    private ActionContext<String, String> actionContext;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void beforeEach() {
        action = (Action<String, String>) mock(Action.class);
        this.actionContext = new ActionContext<>(String.class, action, new ActionConfig<>());
    }

    @Nested
    @DisplayName("execute(TTarget)")
    public class Execute {

        @Test
        @DisplayName("should call Action#execute(TTarget, TConfig)")
        public void shouldCallActionExecute() {

            assertThatCode(() -> actionContext.execute("foobar"))
                    .doesNotThrowAnyException();

            verify(action, times(1)).execute("foobar", actionContext);
        }

        @Test
        @DisplayName("should verify that target types match")
        public void shouldVerifyTargetType() {

            ActionContext context = actionContext;

            assertThatCode(() -> context.execute(1)).doesNotThrowAnyException();

            verifyNoInteractions(action);
        }
    }

}