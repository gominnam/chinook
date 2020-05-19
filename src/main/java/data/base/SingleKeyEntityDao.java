package data.base;

import java.sql.ResultSet;

public abstract class SingleKeyEntityDao<E, K> extends EntityDao<E> {
    protected abstract String getByKeyQuery();

    protected abstract String deleteByKeyQuery();
}
