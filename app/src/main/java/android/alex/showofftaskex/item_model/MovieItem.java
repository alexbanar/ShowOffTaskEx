package android.alex.showofftaskex.item_model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MovieItem implements Parcelable {
    //Properties
    private int id;
    private String title;
    private int releaseYear;
    private double rating;
    private ArrayList<String> genres;
    private String image;

    //Constructor for INSERT:
    public MovieItem(String title, int releaseYear, double rating, ArrayList<String> genres, String image) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.genres = genres;
        this.image = image;
    }

    //Constructor for the SELECT:
    public MovieItem(int id, String title, int releaseYear, double rating, ArrayList<String> genres, String image) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.genres = genres;
        this.image = image;
    }

    //getters and setters /alt + insert

    public int getID() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public int getReleaseYear() {
        return releaseYear;
    }
    public double getRating() {
        return rating;
    }
    public ArrayList<String> getGenres() {
        return genres;
    }
    public String getImage() {
        return image;
    }
    public void setID(int ID) {
        this.id = ID;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }
    public void setImage(String image) {
        this.image = image;
    }

    //toString() /alt + insert

    @Override
    public String toString() {
        return "MovieItem{" +
                "title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                ", rating=" + rating +
                ", genres=" + genres +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.releaseYear);
        dest.writeDouble(this.rating);
        dest.writeStringList(this.genres);
        dest.writeString(this.image);
    }

    protected MovieItem(Parcel in) {
        this.title = in.readString();
        this.releaseYear = in.readInt();
        this.rating = in.readDouble();
        this.genres = in.createStringArrayList();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel source) {
            return new MovieItem(source);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };
}