package com.librarian.models.resources;

public class TreasureBook extends Resource implements Readable {
    private String publisher, publicationYear, donor;

    public TreasureBook(String id, String libraryId, String categoryId, String title, String author, String publisher, String publicationYear, String donor) {
        super(id, libraryId, categoryId, title, author);
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.donor = donor;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getDonor() {
        return donor;
    }

    public void setDonor(String donor) {
        this.donor = donor;
    }
}
