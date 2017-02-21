package Lab2.DAO;

import Lab2.Entities.EmployeeEntity;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class EmployeeDao extends AbstractDao<EmployeeEntity> {

    public EmployeeDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getTableName() {
        return EmployeeEntity.tableName;
    }

    @Override
    public File getXMLPath() {
        return new File("./Employee.xml");
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO " + EmployeeEntity.tableName + " (" + EmployeeEntity.columnId + ", " + EmployeeEntity.columnName + "," + EmployeeEntity.columnSurname + "," +
                EmployeeEntity.columnPhone + ") " +
                "VALUES (?, ?, ?, ?);";
    }

    @Override
    public List<EmployeeEntity> parseResultSet(ResultSet rs) {
        LinkedList<EmployeeEntity> result = new LinkedList<EmployeeEntity>();
        try {
            while (rs.next()) {
                EmployeeEntity employee = new EmployeeEntity();
                employee.setIdEmployee(rs.getInt(EmployeeEntity.columnId));
                employee.setName(rs.getString(EmployeeEntity.columnName).trim());
                employee.setSurname(rs.getString(EmployeeEntity.columnSurname).trim());
                employee.setPhone(rs.getString(EmployeeEntity.columnPhone).trim());
                result.add(employee);
            }
        } catch (SQLException e) {
            logger.error("SQL Exception при парсинге записей из таблицы " + EmployeeEntity.tableName + " в объект EmployeeDAO ", e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, EmployeeEntity object) {
        try {
            statement.setInt(1, object.getIdEmployee());
            statement.setString(2, object.getName());
            statement.setString(3, object.getSurname());
            statement.setString(4, object.getPhone());
        } catch (Exception e) {
            logger.error("Возникла ошибка при подготовке данных для вставки в таблицу " + EmployeeEntity.tableName, e);
        }
    }

    @Override
    public String getDeleteQuery() {
        return null;
    }

    @Override
    public void update(EmployeeEntity object) {

    }

    @Override
    public void delete(EmployeeEntity object) {

    }
}
