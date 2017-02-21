package Lab2.DAO;

import Lab2.Entities.OrderEntity;
import Lab2.Helpers.Utils;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;


public class OrderDao extends AbstractDao<OrderEntity> {

    public OrderDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getTableName() {
        return OrderEntity.tableName;
    }

    @Override
    public File getXMLPath() {
        return new File("./Order.xml");
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO public." + getTableName() + " (" + OrderEntity.columnId + ", " + OrderEntity.columnIdEmployee + "," + OrderEntity.columnIdClient + "," +
                OrderEntity.columnIdTour + "," + OrderEntity.columnCheckoutDate + ") " +
                "VALUES (?, ?, ?, ?, ?);";
    }

    @Override
    public List<OrderEntity> parseResultSet(ResultSet rs) {
        LinkedList<OrderEntity> result = new LinkedList<OrderEntity>();
        try {
            while (rs.next()) {
                OrderEntity order = new OrderEntity();
                order.setIdOrder(rs.getInt(OrderEntity.columnId));
                order.setIdEmployee(rs.getInt(OrderEntity.columnIdEmployee));
                order.setIdClient(rs.getInt(OrderEntity.columnIdClient));
                order.setIdTour(rs.getInt(OrderEntity.columnIdTour));
                order.setCheckoutDate(Utils.SQLDateToUtilDate(rs.getDate(OrderEntity.columnCheckoutDate)));
                result.add(order);
            }
        } catch (SQLException e) {
            logger.error("SQL Exception при парсинге записей из таблицы " + OrderEntity.tableName + " в объект OrderDAO:", e);
        } catch (ParseException e) {
            logger.error("Parse Exception при преобразовании дат из таблицы " + OrderEntity.tableName + " в объект OrderDAO:", e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, OrderEntity object) {
        try {
            statement.setInt(1, object.getIdOrder());
            statement.setInt(2, object.getEmployeeByIdEmployee().getIdEmployee());
            statement.setInt(3, object.getClientByIdClient().getIdClient());
            statement.setInt(4, object.getCatalogueToursByIdTour().getIdTour());
            statement.setDate(5, Utils.UtilDateToSQLDate(object.getCheckoutDate()));
        } catch (Exception e) {
            logger.error("Возникла ошибка при подготовке данных для вставки в таблицу " + OrderEntity.tableName, e);
        }
    }

    @Override
    public String getDeleteQuery() {
        return null;
    }

    @Override
    public void update(OrderEntity object) {

    }

    @Override
    public void delete(OrderEntity object) {

    }
}
