package com.librarian.models.resources;

public abstract class Resource {
    private final String id;
    private String title, author, libraryId, categoryId;

    public Resource(String id, String libraryId, String categoryId, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.libraryId = libraryId;
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
