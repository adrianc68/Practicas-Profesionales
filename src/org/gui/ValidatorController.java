package org.gui;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.scene.control.Control;
import javafx.scene.control.TextInputControl;
import org.util.Validator;
import java.util.HashMap;
import java.util.Map;

/***
 * You should use this class for every class which it will works like a
 * GUI for an user that will use some inputs (e.g a TextField or TextArea).
 */
public class ValidatorController extends Controller {
    protected Map<Control, Boolean> interruptorMap = new HashMap<>();

    /***
     * It should be used before an action. e.g. save data.
     * It was written likes this to reduce cyclomatic complexity.
     * @return boolean true if all inputs has true value and false if any input has false as value.
     */
    protected boolean verifyInputData() {
        boolean dataInputValid = true;
        for( Map.Entry<Control, Boolean> entry : interruptorMap.entrySet() ) {
            if( !entry.getValue() ){
                dataInputValid = false;
                break;
            }
        }
        return dataInputValid;
    }

    /***
     * It should be used to try out and input by a specific pattern.
     * This method receive a validatorMap which must contains
     * a TextInputControl as first parameter and as second parameter
     * an Array which must contains three ordered constraints:
     * regex pattern, limit length and a CheckIcon
     * It was written like this to reduce cyclomatic complexity.
     * @param validatorMap
     */
    protected void initValidatorToTextInputControl(Map<TextInputControl, Object[]> validatorMap) {
        final int FIRST_CONTRAINT = 0;
        final int SECOND_CONTRAINT = 1;
        final int THIRD_CONSTRAINT_ICON = 2;
        for (Map.Entry<TextInputControl, Object[]> entry : validatorMap.entrySet() ) {
            entry.getKey().textProperty().addListener( (observable, oldValue, newValue) -> {
                if( !Validator.doesStringMatchPattern( newValue, ( (String) entry.getValue()[FIRST_CONTRAINT] ) ) || Validator.isStringLargerThanLimitOrEmpty( newValue, ( (Integer) entry.getValue()[SECOND_CONTRAINT] ) ) ){
                    interruptorMap.put(entry.getKey(), false );
                    ( (MaterialDesignIconView) entry.getValue()[THIRD_CONSTRAINT_ICON] ).getStyleClass().clear();
                    ( (MaterialDesignIconView) entry.getValue()[THIRD_CONSTRAINT_ICON] ).getStyleClass().add("wrongTextField");
                } else {
                    interruptorMap.put(entry.getKey(), true );
                    ((MaterialDesignIconView) entry.getValue()[THIRD_CONSTRAINT_ICON]).getStyleClass().clear();
                    ( (MaterialDesignIconView) entry.getValue()[THIRD_CONSTRAINT_ICON]).getStyleClass().add("correctlyTextField");
                }
            });
        }
    }

}
