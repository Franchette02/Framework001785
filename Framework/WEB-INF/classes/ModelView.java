package etu1785.framework;

import java.util.HashMap;

public class ModelView {
    private String view;

    public ModelView() {
        this.data = new HashMap<>();
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

}
