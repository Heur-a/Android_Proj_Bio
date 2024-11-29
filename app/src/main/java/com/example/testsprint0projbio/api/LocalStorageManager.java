package com.example.testsprint0projbio.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.testsprint0projbio.pojo.Node;
import com.google.gson.Gson;

public class LocalStorageManager {

    private static final String PREF_NAME = "app_preferences";
    private static final String KEY_NODE = "key_node";

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public LocalStorageManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    // Guarda el Node en SharedPreferences
    public void saveNode(Node node) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String nodeJson = gson.toJson(node); // Serialitza l'objecte Node a JSON
        editor.putString(KEY_NODE, nodeJson);
        editor.apply(); // Guarda els canvis de forma asíncrona
    }

    // Recupera el Node des de SharedPreferences
    public Node getNode() {
        String nodeJson = sharedPreferences.getString(KEY_NODE, null);
        if (nodeJson != null) {
            return gson.fromJson(nodeJson, Node.class); // Converteix el JSON a un objecte Node
        }
        return null; // Retorna null si no hi ha cap Node guardat
    }

    // Elimina el Node de SharedPreferences
    public void deleteNode() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_NODE);
        editor.apply();
    }
}
