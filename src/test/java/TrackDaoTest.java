

import data.TrackDao;
import entities.Track;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TrackDaoTest {

    @Test
    void getCount() {
        int count = TrackDao.getInstance().getCount();

        assertTrue(count > 0);
    }

    @Test
    void getByKey() {
        Track track = TrackDao.getInstance().getByKey(14);


        assertEquals("Spellbound", track.getName());
    }

    @Test
    void getByAlbumId(){
        ArrayList<Track> tracks = TrackDao.getInstance().getByAlbumId(1);

        assertTrue(tracks.size() > 0);

        for (Track track : tracks) {
            assertEquals(1, track.getAlbumId());
        }
    }

    @Test
    void getAll() {
        ArrayList<Track> tracks = TrackDao.getInstance().getAll();

        assertTrue(tracks.size() > 0);
    }

    @Test
    void getMaxTrackId() {
        Integer maxTrackId = TrackDao.getInstance().getMaxTrackId();

        assertTrue(maxTrackId != null);
    }

    @Test
    void insert() {
        String now = LocalDateTime.now().toString();

        int oldCount = TrackDao.getInstance().getCount();

        Track track = new Track();

        track.setName(now);
        track.setAlbumId(1);
        track.setGenreId(1);
        track.setUnitPrice(1);

        boolean inserted = TrackDao.getInstance().insert(track);

        assertTrue(inserted);

        int newCount = TrackDao.getInstance().getCount();

        assertEquals(oldCount + 1, newCount);

        int maxTrackId = TrackDao.getInstance().getMaxTrackId();
        track = TrackDao.getInstance().getByKey(maxTrackId);

        assertEquals(now, track.getName());
    }

    @Test
    void update() {
        String now = LocalDateTime.now().toString();

        int maxTrackId = TrackDao.getInstance().getMaxTrackId();
        Track track = TrackDao.getInstance().getByKey(maxTrackId);
        track.setName(now);
        TrackDao.getInstance().update(track);

        track = TrackDao.getInstance().getByKey(maxTrackId);
        assertEquals(now, track.getName());
    }

    @Test
    void deleteByKey() {
        int oldCount = TrackDao.getInstance().getCount();
        int maxTrackId = TrackDao.getInstance().getMaxTrackId();

        TrackDao.getInstance().deleteByKey(maxTrackId);

        int newCount = TrackDao.getInstance().getCount();

        assertEquals(oldCount - 1, newCount);
    }
}