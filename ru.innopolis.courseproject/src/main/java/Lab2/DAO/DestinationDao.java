package Lab2.DAO;

import Lab2.Entities.DestinationEntity;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class DestinationDao extends AbstractDao<DestinationEntity> {

    public DestinationDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getTableName() {
        return DestinationEntity.tableName;
    }

    @Override
    public File getXMLPath() {
        return new File("./Destination.xml");
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO " + DestinationEntity.tableName + " (" + DestinationEntity.columnIdCountry + "," + DestinationEntity.columnResort + "," +
                DestinationEntity.columnHotel + ") " +
                "VALUES (?, ?, ?);";
    }

    @Override
    public List<DestinationEntity> parseResultSet(ResultSet rs) {
        LinkedList<DestinationEntity> result = new LinkedList<DestinationEntity>();
        try {
            while (rs.next()) {
                DestinationEntity dest = new DestinationEntity();
                dest.setIdDestination(rs.getInt(DestinationEntity.columnId));
                dest.setIdCountry(rs.getInt(DestinationEntity.columnIdCountry));
                dest.setResort(rs.getString(DestinationEntity.columnResort).trim());
                dest.setHotel(rs.getString(DestinationEntity.columnHotel).trim());
                result.add(dest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, DestinationEntity object) {
        try {
            statement.setInt(1, object.getCountryByIdCountry().getIdCountry());
            statement.setString(2, object.getResort());
            statement.setString(3, object.getHotel());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDeleteQuery() {
        return null;
    }

    @Override
    public void update(DestinationEntity object) {

    }

    @Override
    public void delete(DestinationEntity object) {

    }
}
