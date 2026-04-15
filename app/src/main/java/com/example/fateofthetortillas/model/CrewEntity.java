package com.example.fateofthetortillas.model;

public interface CrewEntity {
    String getEntityType();
    int act(String actionName);
    void damage(int damage);

    int actUpon(String actionName);
    void defend(int damage);



}
