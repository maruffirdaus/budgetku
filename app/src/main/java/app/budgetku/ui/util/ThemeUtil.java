package app.budgetku.ui.util;

import android.content.Context;
import android.util.TypedValue;

import androidx.annotation.AttrRes;
import androidx.core.content.ContextCompat;

public class ThemeUtil {

    public static int getThemeAttributeColor(Context context, @AttrRes int attribute) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attribute, typedValue, true);
        return ContextCompat.getColor(context, typedValue.resourceId);
    }
}
