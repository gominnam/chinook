package data;

import data.base.EntityDao;
import data.base.IntEntityDao;
import entities.Album;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AlbumDao extends IntEntityDao<Album> {
    //region singleton
    private AlbumDao() {
    }

    private static AlbumDao _instance;

    public static AlbumDao getInstance(){
        if (_instance == null)
            _instance = new AlbumDao();

        return _instance;
    }

    //endregion

    @SneakyThrows
    @Override
    protected Album readEntity(ResultSet result) {
        Album entity = new Album();

        entity.setAlbumId(result.getInt(1));
        entity.setTitle(result.getString(2));
        entity.setArtistId(result.getInt(3));

        return entity;
    }

    @Override
    protected String getCountQuery() {
        //language=TSQL
        return "select count(*) from Album";
    }

    @Override
    protected String getByKeyQuery() {
        //language=TSQL
        return "select * from Album where AlbumId = ?";
    }

    @Override
    protected String getAllQuery() {
        //language=TSQL
        return "select * from Album";
    }

    @SneakyThrows
    public boolean insert(Album entity){
        //language=TSQL
        String query = "insert into Album values (?, ?)";

        return execute(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setString(1, entity.getTitle());
                statement.setInt(2, entity.getArtistId());
            }
        });
    }

    @SneakyThrows
    public boolean update(Album album){
        //language=TSQL
        String query = "update Album set Title = ?, ArtistId = ? where AlbumId = ?";

        return execute(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setString(1, album.getTitle());
                statement.setInt(2, album.getArtistId());
                statement.setInt(3, album.getAlbumId());
            }
        });
    }

    @Override
    protected String deleteByKeyQuery() {
        //language=TSQL
        return "delete Album where AlbumId = ?";
    }

    @SneakyThrows
    public ArrayList<Album> getByArtistId(int artistId) {
        //language=TSQL
        String query = "select * from Album where ArtistId = ?";

        // verbose / decorating code
        return getMany(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setInt(1, artistId);
            }
        });
    }

    @SneakyThrows
    public int getMaxAlbumId() {
        //language=TSQL
        String query = "select top 1 AlbumId from Album order by AlbumId desc ";

        return getInt(query, null);
    }
}
