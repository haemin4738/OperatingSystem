/*
 * LimitedTextField 클래스
 * 입력 길이 제한
 * 
 */

package application;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class LimitedTextField extends TextField //입력 제한
{
    private static final int MAX_LENGTH = 5;

    public LimitedTextField() {
        super();
        addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (getText().length() >= MAX_LENGTH) {
                event.consume();
            }
        });
    }
}