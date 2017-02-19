package Lab2.DAO;

import Lab2.Entities.CountryEntity;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class CountryDao extends AbstractDao<CountryEntity> {

    public CountryDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getTableName() {
        return CountryEntity.tableName;
    }

    @Override
    public File getXMLPath() {
        return new File("./Country.xml");
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO " + CountryEntity.tableName + " (" + CountryEntity.columnName + ") " +
                "VALUES (?);";
    }

    @Override
    public List<CountryEntity> parseResultSet(ResultSet rs) {
        LinkedList<CountryEntity> result = new LinkedList<CountryEntity>();
        try {
            while (rs.next()) {
                CountryEntity country = new CountryEntity();
                country.setIdCountry(rs.getInt(CountryEntity.columnId));
                country.setNameCountry(rs.getString(CountryEntity.columnName).trim());
                result.add(country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, CountryEntity object) {
        try {
            statement.setString(1, object.getNameCountry());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(CountryEntity object) {

    }

    @Override
    public void delete(CountryEntity object) {

    }

    @Override
    public String getDeleteQuery() {
        return null;
    }
}
