package com.example.testsprint0projbio.api;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

public class CookieManager {
    // Guardar la cookie a SharedPreferences
    static public void saveSessionCookie(String sessionCookie, Context context) {
        context.getSharedPreferences("UserSession", MODE_PRIVATE)
                .edit()
                .putString("SessionCookie", sessionCookie)
                .apply();
    }

    // Recuperar la cookie des de SharedPreferences
    static public String getSessionCookie(Context context) {
        return context.getSharedPreferences("UserSession", MODE_PRIVATE)
                .getString("SessionCookie", null);
    }
}
