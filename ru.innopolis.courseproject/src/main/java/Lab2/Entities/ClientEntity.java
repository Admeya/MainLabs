package Lab2.Entities;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;
import java.util.Collection;

/**
 * Created by Ирина on 17.02.2017.
 */
@XmlType(propOrder = { "idClient", "name", "surname", "middlename", "birthdate", "passportSerNum", "phone", "orderssByIdClient"}, name = "ClientEntity")
@XmlRootElement
public class ClientEntity implements Serializable {
    private Integer idClient;
    private String name;
    private String surname;
    private String middlename;
    private Date birthdate;
    private String passportSerNum;
    private String phone;
    private Collection<OrderEntity> orderssByIdClient;

    public static String tableName = "client";
    public static String columnIdClient = "id_client";
    public static String columnName = "name";
    public static String columnSurname = "surname";
    public static String columnMiddlename = "middlename";
    public static String columnBirthdate = "birthdate";
    public static String columnPasport = "passport_ser_num";
    public static String columnPhone = "phone";

    public Integer getIdClient() {return idClient;}

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getPassportSerNum() {
        return passportSerNum;
    }

    public void setPassportSerNum(String passportSerNum) {
        this.passportSerNum = passportSerNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientEntity that = (ClientEntity) o;

        if (idClient != null ? !idClient.equals(that.idClient) : that.idClient != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (middlename != null ? !middlename.equals(that.middlename) : that.middlename != null) return false;
        if (birthdate != null ? !birthdate.equals(that.birthdate) : that.birthdate != null) return false;
        if (passportSerNum != null ? !passportSerNum.equals(that.passportSerNum) : that.passportSerNum != null)
            return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idClient != null ? idClient.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (middlename != null ? middlename.hashCode() : 0);
        result = 31 * result + (birthdate != null ? birthdate.hashCode() : 0);
        result = 31 * result + (passportSerNum != null ? passportSerNum.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ClientEntity{" +
                "idClient=" + idClient +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", middlename='" + middlename + '\'' +
                ", birthdate=" + birthdate +
                ", passportSerNum='" + passportSerNum + '\'' +
                ", phone='" + phone + '\'' +
                ", orderssByIdClient=" + orderssByIdClient +
                '}';
    }

    public Collection<OrderEntity> getOrderssByIdClient() {
        return orderssByIdClient;
    }

    public void setOrderssByIdClient(Collection<OrderEntity> orderssByIdClient) {
        this.orderssByIdClient = orderssByIdClient;
    }

}
