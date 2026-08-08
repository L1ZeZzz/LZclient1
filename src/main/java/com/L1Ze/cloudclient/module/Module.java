package com.yourname.cloudclient.module;

public abstract class Module {
    private final String name;
    private boolean enabled;

    public Module(String name, boolean defaultEnabled) {
        this.name = name;
        this.enabled = defaultEnabled;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        onStateChanged();
    }

    public void toggle() {
        enabled = !enabled;
        onStateChanged();
    }

    protected void onStateChanged() {
        // 子类可重写，例如保存配置
    }
}