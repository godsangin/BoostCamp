package com.msproject.myhome.boostcamp;

public class Movie {
    String title;
    double grade;
    String director;
    String year;
    String actors;
    String imageURL;
    String link;

    public Movie(String title, double grade, String director, String year, String actors, String imageURL, String link){
        this.title = title;
        this.grade = grade;
        this.director = director;
        this.year = year;
        this.actors = actors;
        this.imageURL = imageURL;
        this.link = link;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String toString(){
        return "제목: " + title + " 평점: " + grade + " 감독: " + director + " 개봉년: " + year + " 배우: " + actors + " 이미지: " + imageURL + " 링크: " + link;
    }
}
