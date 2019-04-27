package com.nicoga.taskplanner.network.data;

import java.time.LocalDate;
import java.util.Date;

public class Task {
    protected String id;
    private Date dueDate;
    private User responsible;
    private int priority;
    private String status;
    private String description;

    public Task() {
    }

    public Task(String id, Date dueDate, User responsible, int priority, String status, String description) {
        this.id = id;
        this.dueDate = dueDate;
        this.responsible = responsible;
        this.priority = priority;
        this.status = status;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;

    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", dueDate=" + dueDate +
                ", responsible=" + responsible +
                ", priority=" + priority +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
