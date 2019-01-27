package android.alex.showofftaskex;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MovieItem implements Parcelable {
    private final String title;
    private final int releaseYear;
    private final double rating;
    private final ArrayList<String> genres;
    private final String image;

    public MovieItem(String title, int releaseYear, double rating, ArrayList<String> genres, String image) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.genres = genres;
        this.image = image;
    }

    //getters only - alt + insert

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

    //toString() alt + insert

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