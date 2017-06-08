package ke.co.debechlabs.besure.util;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

/**
 * Created by chriz on 6/8/2017.
 */

//...
// Parcelable RevealAnimationSettings with AutoValue
@AutoValue
public abstract class RevealAnimationSetting implements Parcelable {
    public abstract int getCenterX();
    public abstract int getCenterY();
    public abstract int getWidth();
    public abstract int getHeight();

    public static RevealAnimationSetting with(int centerX, int centerY, int width, int height) {
        return new AutoValue_RevealAnimationSetting(centerX, centerY, width, height);
    }
}
