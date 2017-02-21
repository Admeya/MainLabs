package Lab2.DAO;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * Абстрактный класс предоставляющий базовую реализацию CRUD операций с использованием JDBC.
 *
 * @param <T> DAO таблицы из БД
 */
public abstract class AbstractDao<T extends Serializable> implements GenericDAO<T> {
    static Logger logger = Logger.getLogger(AbstractDao.class);

    private Connection connection;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

    public abstract String getTableName();

    public abstract String getInsertQuery();

    public abstract String getDeleteQuery();

    public String getDeleteAllQuery() {
        return "DELETE FROM public." + getTableName();
    }

    public String getSelectAllQuery() {
        return "SELECT * FROM public." + getTableName();
    }

    public abstract File getXMLPath();

    protected abstract List<T> parseResultSet(ResultSet rs);

    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object);

    @Override
    public List<T> selectAll() {
        List<T> list = new ArrayList<T>();
        ResultSet rs = null;
        String sql = getSelectAllQuery();
        try (Statement statement = connection.createStatement()) {
            rs = statement.executeQuery(sql);
            list = parseResultSet(rs);
        } catch (Exception e) {
            logger.error("Возникла ошибка при извлечении данных из БД: " + sql, e);
        }
        return list;
    }

    @Override
    public void deleteAll() {
        String sql = getDeleteAllQuery();
        try (Statement statement = connection.createStatement()) {
            int count = statement.executeUpdate(sql);
            logger.trace(sql + " Delete " + count + " records");
        } catch (Exception e) {
            logger.error("Возникла ошибка при удалении данных из БД: " + sql, e);
        }
    }

    @Override
    public void insert(T object) {
        String sql = getInsertQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForInsert(statement, object);
            int count = statement.executeUpdate();
            logger.trace(statement + " Insert " + count + " records");
        } catch (Exception e) {
            logger.error("Возникла ошибка при вставке записей в БД: " + sql, e);
        }
    }

    @Override
    public T selectByPK(int key, String columnName, T object) {
        List<T> list = null;
        String sql = getSelectAllQuery() + " WHERE " + columnName + " = " + key;
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            list = parseResultSet(rs);
        } catch (Exception e) {
            logger.error("Возникла ошибка при извлечении данных по первичному ключу из БД: " + sql, e);
        }
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.iterator().next();
    }
}

