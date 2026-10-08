package Core;

public class Movie {

    int movieId;
    String movieName;
    int movieDuration;

    public Movie(int movieId, String movieName, int movieDuration) {
        this.movieDuration = movieDuration;
        this.movieName = movieName;
        this.movieId = movieId;
    }

    public int getMovieId() {
        return this.movieId;
    }

    public String getMovieName() {
        return this.movieName;
    }

    public int getMovieDuration() {
        return this.movieDuration;
    }
}
