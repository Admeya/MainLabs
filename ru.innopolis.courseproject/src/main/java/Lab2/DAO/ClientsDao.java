package Lab2.DAO;

import Lab2.Entities.ClientEntity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Ирина on 17.02.2017.
 */
@XmlRootElement(name = "clients")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClientsDao {
    @XmlElement(name = "client")
    private List<ClientEntity> clients = null;

    public List<ClientEntity> getClients() {
        return clients;
    }

    public void setClients(List<ClientEntity> clients) {
        this.clients = clients;
    }
}

