package ConcreateLockProvider;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import Core.Seat;
import Core.SeatLock;
import Core.Show;
import Core.User;
import Interfaces.ISeatLockProvider;

public class SeatLockProvider implements ISeatLockProvider {

    private final int lockTimeout;
    private final Map<Show, Map<Show, SeatLock>> locks;

    public SeatLockProvider(Integer lockTimeout) {
        this.locks = new ConcurrentHashMap<>();
        this.lockTimeout = lockTimeout;
    }

    @Override
    public void lockSeats(Show show, List<Seat> seatList, User user) throws Exception {
        Map<Seat, SeatLock> seatLocks = locks.computeIfAbsent(show, s -> new ConcurrentHashMap<>());
        synchronized (seatLocks) {
            // Check if any of the requested seats is already locked and the lock is still valid
            for (Seat seat : seatList) {
                if (seatLocks.containsKey(seat)) {
                    SeatLock existingLock = seatLocks.get(seat);
                    if (!existingLock.isLockExpired()) {
                        throw new Exception("Seat " + seat.getSeatId() + " is already locked.");
                    }
                }
            }
            Date now = new Date();
            for (Seat seat : seats) {
                SeatLock lock = new SeatLock(seat, show, lockTimeout, now, user);
                seatLocks.put(seat, lock);
            }
        }
    }

    @Override
    public void unlockSeats(final Show show, final List<Seat> seats, final User user) {
        Map<Seat, SeatLock> seatLocks = locks.get(show);
        if (seatLocks == null) {
            return;
        }
        synchronized (seatLocks) {
            for (Seat seat : seats) {
                SeatLock lock = seatLocks.get(seat);
                if (lock != null && lock.getLockedBy().equals(user)) {
                    seatLocks.remove(seat);
                }
            }
        }
    }

    @Override
    public boolean validateLock(final Show show, final Seat seat, final User user) {
        Map<Seat, SeatLock> seatLocks = locks.get(show);
        if (seatLocks == null) {
            return false;
        }
        synchronized (seatLocks) {
            SeatLock lock = seatLocks.get(seat);
            return lock != null && !lock.isLockExpired() && lock.getLockedBy().equals(user);
        }
    }

    @Override
    public List<Seat> getLockedSeats(final Show show) {
        Map<Seat, SeatLock> seatLocks = locks.get(show);
        if (seatLocks == null) {
            return Collections.emptyList();
        }
        synchronized (seatLocks) {
            return seatLocks.entrySet().stream()
                    .filter(entry -> !entry.getValue().isLockExpired())
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
    }
}
