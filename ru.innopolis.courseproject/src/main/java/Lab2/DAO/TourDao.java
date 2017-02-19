package Lab2.DAO;

import Lab2.Entities.TourEntity;
import Lab2.Helpers.Utils;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;


public class TourDao extends AbstractDao<TourEntity> {

    public TourDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getTableName() {
        return TourEntity.tableName;
    }

    @Override
    public File getXMLPath() {
        return new File("./Tour.xml");
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO " + getTableName() + " (" + TourEntity.columnName + "," + TourEntity.columnDateStart + "," +
                TourEntity.columnDateEnd + "," + TourEntity.columnCost + "," + TourEntity.columnIdDestination + ") " +
                "VALUES (?, ?, ?, ?, ?);";
    }

    @Override
    public List<TourEntity> parseResultSet(ResultSet rs) {
        LinkedList<TourEntity> result = new LinkedList<TourEntity>();
        try {
            while (rs.next()) {
                TourEntity tour = new TourEntity();
                tour.setIdTour(rs.getInt(TourEntity.columnId));
                tour.setName(rs.getString(TourEntity.columnName).trim());
                tour.setDateStart(Utils.SQLDateToUtilDate(rs.getDate(TourEntity.columnDateStart)));
                tour.setDateEnd(Utils.SQLDateToUtilDate(rs.getDate(TourEntity.columnDateEnd)));
                tour.setCost(rs.getInt(TourEntity.columnCost));
                tour.setIdDestination(rs.getInt(TourEntity.columnIdDestination));
                result.add(tour);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, TourEntity object) {
        try {
            statement.setString(1, object.getName());
            statement.setDate(2, Utils.UtilDateToSQLDate(object.getDateStart()));
            statement.setDate(3, Utils.UtilDateToSQLDate(object.getDateEnd()));
            statement.setInt(4, object.getCost());
            statement.setInt(5, object.getDestinationPlaceByIdDestination().getIdDestination());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDeleteQuery() {
        return null;
    }

    @Override
    public void update(TourEntity object) {

    }

    @Override
    public void delete(TourEntity object) {

    }
}
