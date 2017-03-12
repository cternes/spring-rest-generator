package de.slackspace.generator;

import java.util.HashMap;
import java.util.Map;

public class ValueHolder {

    private String targetPackage;
    private String endpoint;
    private String clazz;
    private String model;
    private String repo;

    public ValueHolder addTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
        return this;
    }

    public ValueHolder addRestEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public ValueHolder addTargetClazz(String clazz) {
        this.clazz = clazz;
        return this;
    }

    public ValueHolder addModelClazz(String model) {
        this.model = model;
        return this;
    }

    public ValueHolder addRepoClazz(String repo) {
        this.repo = repo;
        return this;
    }

    public Map<String, String> build() {
        Map<String, String> map = new HashMap<>();
        map.put("endpoint", endpoint);
        map.put("targetPackage", getPackageName(clazz));
        map.put("clazz", getSimpleName(clazz));
        map.put("fullyQualifiedModel", model);
        map.put("model", getSimpleName(model));
        map.put("fullyQualifiedRepo", repo);
        map.put("repo", getSimpleName(repo));

        return map;
    }

    private String getSimpleName(String clazz) {
        return clazz.substring(clazz.lastIndexOf(".") + 1);
    }

    private String getPackageName(String clazz) {
        return clazz.substring(0, clazz.lastIndexOf("."));
    }

}
