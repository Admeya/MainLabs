package Lab2.Entities;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Ирина on 17.02.2017.
 */
@XmlType(propOrder = { "idEmployee", "name", "surname", "phone","orderssByIdEmployee"}, name = "EmployeeEntity")
@XmlRootElement
public class EmployeeEntity implements Serializable{
    private Integer idEmployee;
    private String name;
    private String surname;
    private String phone;
    private Collection<OrderEntity> orderssByIdEmployee;

    public static String tableName = "employee";
    public static String columnId = "id_employee";
    public static String columnName = "name";
    public static String columnSurname = "surname";
    public static String columnPhone = "phone";

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
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

        EmployeeEntity that = (EmployeeEntity) o;

        if (idEmployee != null ? !idEmployee.equals(that.idEmployee) : that.idEmployee != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idEmployee != null ? idEmployee.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EmployeeEntity{" +
                "idEmployee=" + idEmployee +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", orderssByIdEmployee=" + orderssByIdEmployee +
                '}';
    }

    public Collection<OrderEntity> getOrderssByIdEmployee() {
        return orderssByIdEmployee;
    }

    public void setOrderssByIdEmployee(Collection<OrderEntity> orderssByIdEmployee) {
        this.orderssByIdEmployee = orderssByIdEmployee;
    }
}
