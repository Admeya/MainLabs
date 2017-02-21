package Lab2.DAO;

import Lab2.Entities.ClientEntity;
import Lab2.Helpers.Utils;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;


public class ClientDao extends AbstractDao<ClientEntity> {

    public ClientDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getTableName() {
        return ClientEntity.tableName;
    }

    @Override
    public File getXMLPath() {
        return new File("./Client.xml");
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO " + ClientEntity.tableName + " (" + ClientEntity.columnIdClient + "," + ClientEntity.columnName + "," + ClientEntity.columnSurname + "," +
                ClientEntity.columnMiddlename + "," + ClientEntity.columnBirthdate + "," + ClientEntity.columnPasport + "," +
                ClientEntity.columnPhone + ") " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";
    }

    @Override
    public List<ClientEntity> parseResultSet(ResultSet rs) {
        LinkedList<ClientEntity> result = new LinkedList<ClientEntity>();
        try {
            while (rs.next()) {
                ClientEntity client = new ClientEntity();
                client.setIdClient(rs.getInt(ClientEntity.columnIdClient));
                client.setName(rs.getString(ClientEntity.columnName).trim());
                client.setSurname(rs.getString(ClientEntity.columnSurname).trim());
                client.setMiddlename(rs.getString(ClientEntity.columnMiddlename).trim());
                client.setBirthdate(Utils.SQLDateToUtilDate(rs.getDate(ClientEntity.columnBirthdate)));
                client.setPassportSerNum(rs.getString(ClientEntity.columnPasport).trim());
                client.setPhone(rs.getString(ClientEntity.columnPhone).trim());
                result.add(client);
            }
        } catch (SQLException e) {
            logger.error("SQL Exception при парсинге записей из таблицы " + ClientEntity.tableName + " в объект ClientDAO:", e);
        } catch (ParseException e) {
            logger.error("Parse Exception при преобразовании дат из таблицы " + ClientEntity.tableName + " в объект ClientDAO:", e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, ClientEntity object) {
        try {
            statement.setInt(1, object.getIdClient());
            statement.setString(2, object.getName());
            statement.setString(3, object.getSurname());
            statement.setString(4, object.getMiddlename());
            statement.setDate(5, Utils.UtilDateToSQLDate(object.getBirthdate()));
            statement.setString(6, object.getPassportSerNum());
            statement.setString(7, object.getPhone());
        } catch (Exception e) {
            logger.error("Возникла ошибка при подготовке данных для вставки в таблицу " + ClientEntity.tableName, e);
        }
    }

    @Override
    public void update(ClientEntity object) {

    }

    @Override
    public void delete(ClientEntity object) {

    }

    @Override
    public String getDeleteQuery() {
        return null;
    }


}
