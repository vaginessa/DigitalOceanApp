package in.tosc.digitaloceanapp.utils;

import android.content.Context;

import static android.content.Context.MODE_PRIVATE;

public class Utils {

    public static String getAuthToken(Context context) {
        return context.getSharedPreferences("DO", MODE_PRIVATE)
                .getString("authToken", null);
    }
}
