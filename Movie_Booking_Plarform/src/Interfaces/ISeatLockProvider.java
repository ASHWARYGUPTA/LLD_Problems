package Interfaces;

import java.util.List;

import Core.Seat;
import Core.Show;
import Core.User;

public interface ISeatLockProvider {

    void lockSeats(Show show, List<Seat> seatList, User user) throws Exception;

    void unlockSeats(Show show, List<Seat> seatList, User user);

    boolean validateUser(Show show, Seat seat, User user);

    List<Seat> getLockedSeats(Show show);
}
