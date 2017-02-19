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
        return "INSERT INTO " + ClientEntity.tableName + " (" + ClientEntity.columnName + "," + ClientEntity.columnSurname + "," +
                ClientEntity.columnMiddlename + "," + ClientEntity.columnBirthdate + "," + ClientEntity.columnPasport + "," +
                ClientEntity.columnPhone + ") " +
                "VALUES (?, ?, ?, ?, ?, ?);";
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
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, ClientEntity object) {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getSurname());
            statement.setString(3, object.getMiddlename());
            statement.setDate(4, Utils.UtilDateToSQLDate(object.getBirthdate()));
            statement.setString(5, object.getPassportSerNum());
            statement.setString(6, object.getPhone());
        } catch (Exception e) {
            e.printStackTrace();
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
