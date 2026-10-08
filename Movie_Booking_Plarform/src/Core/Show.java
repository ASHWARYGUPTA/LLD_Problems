package Core;

import java.util.Date;

public class Show {

    private final int id;
    private final Movie movie;
    private final Screen screen;
    private final Date startDate;
    private final Integer durationInMinutes;

    public Show(int id, Movie movie, Screen screen, Date startDate, Integer durationInMinutes) {
        this.id = id;
        this.movie = movie;
        this.screen = screen;
        this.startDate = startDate;
        this.durationInMinutes = durationInMinutes;

    }

    public int getId() {
        return this.id;
    }

    public Screen getScreen() {
        return this.screen;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public Date getStartDate() {
        return this.startDate;
    }
}
