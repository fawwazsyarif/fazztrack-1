package com.payfazz.hello.model;

import java.util.List;

public class ListSelectedOKR {
    private List<ObjectiveModel> objective;

    public ListSelectedOKR() {
    }

    public ListSelectedOKR(List<ObjectiveModel> objective) {
        this.objective = objective;
    }

    public List<ObjectiveModel> getObjective() {
        return this.objective;
    }

    public void setObjective(List<ObjectiveModel> objective) {
        this.objective = objective;
    }

    public ListSelectedOKR objective(List<ObjectiveModel> objective) {
        this.objective = objective;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " objective='" + getObjective() + "'" +
            "}";
    }
}