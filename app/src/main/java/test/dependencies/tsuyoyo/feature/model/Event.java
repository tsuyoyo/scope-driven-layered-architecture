package test.dependencies.tsuyoyo.feature.model;

public class Event {

    public final String title;
    public final String description;
    public final Prefecture prefecture;

    public Event(String title, String description, Prefecture prefecture) {
        this.title = title;
        this.description = description;
        this.prefecture = prefecture;
    }
}
