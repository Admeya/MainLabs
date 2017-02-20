package Lab2;

import Lab2.DAO.*;
import Lab2.Entities.*;
import Lab2.JAXB.Binding;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;

/**
 * Created by Ирина on 17.02.2017.
 */
public class Main {
    static String url;
    static String user;
    static String pass;

    public static void main(String[] args) {
        initialize();
        bindingClient();
        bindingCountry();
        bindingEmployee();
        bindingDestination();
        bindingTour();
        bindingOrder();
    }

    public static void initialize() {
        Properties property = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            property.load(fis);
        } catch (
                FileNotFoundException e)

        {
            e.printStackTrace();
        } catch (
                IOException e)

        {
            e.printStackTrace();
        }
        // Class.forName(property.getProperty("driver"));
        url = property.getProperty("url");
        user = property.getProperty("user");
        pass = property.getProperty("pass");
    }

    static void bindingClient() {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            ClientDao client = new ClientDao(conn);
            List<ClientEntity> clients = client.selectAll();
            client.deleteAll();

            Binding bind = new Binding();
            ClientsDao clientsDAO = new ClientsDao();
            clientsDAO.setClients(clients);

            bind.marshalling(ClientsDao.class, clientsDAO, client.getXMLPath());
            for (ClientEntity cl : clientsDAO.getClients()) {
                System.out.println(cl);
            }
            ClientsDao newClientsDao = (ClientsDao) bind.unmarshalling(ClientsDao.class, client.getXMLPath());

            System.out.println("Unmarshall objects");
            for (ClientEntity cl : newClientsDao.getClients()) {
                System.out.println(cl);
                client.insert(cl);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void bindingCountry() {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            CountryDao country = new CountryDao(conn);
            List<CountryEntity> countries = country.selectAll();
            country.deleteAll();

            Binding bind = new Binding();
            CountriesDao countriesDAO = new CountriesDao();
            countriesDAO.setCountries(countries);

            bind.marshalling(CountriesDao.class, countriesDAO, country.getXMLPath());
            for (CountryEntity ce : countriesDAO.getCountries()) {
                System.out.println(ce);
            }
            CountriesDao newCountriesDao = (CountriesDao) bind.unmarshalling(CountriesDao.class, country.getXMLPath());

            System.out.println("Unmarshall objects");
            for (CountryEntity cl : newCountriesDao.getCountries()) {
                System.out.println(cl);
                country.insert(cl);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void bindingEmployee() {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            EmployeeDao employee = new EmployeeDao(conn);
            List<EmployeeEntity> employees = employee.selectAll();
            employee.deleteAll();

            Binding bind = new Binding();
            EmployeesDao employeesDao = new EmployeesDao();
            employeesDao.setEmployees(employees);

            bind.marshalling(EmployeesDao.class, employeesDao, employee.getXMLPath());
            for (EmployeeEntity ce : employeesDao.getEmployees()) {
                System.out.println(ce);
            }
            EmployeesDao newEmployeesDao = (EmployeesDao) bind.unmarshalling(EmployeesDao.class, employee.getXMLPath());

            System.out.println("Unmarshall objects");
            for (EmployeeEntity cl : newEmployeesDao.getEmployees()) {
                System.out.println(cl);
                employee.insert(cl);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void bindingDestination() {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            DestinationDao dest = new DestinationDao(conn);
            List<DestinationEntity> destinations = dest.selectAll();
            dest.deleteAll();

            for (DestinationEntity entityDest : destinations) {
                int idCountry = entityDest.getIdCountry();
                CountryDao country = new CountryDao(conn);
                CountryEntity couEnt = new CountryEntity();
                couEnt = country.selectByPK(idCountry, CountryEntity.columnId, couEnt);
                entityDest.setCountryByIdCountry(couEnt);
            }

            Binding bind = new Binding();
            DestinationsDao destinationsDao = new DestinationsDao();
            destinationsDao.setDestinations(destinations);

            System.out.println("Marshall objects");
            bind.marshalling(DestinationsDao.class, destinationsDao, dest.getXMLPath());
            for (DestinationEntity ce : destinationsDao.getDestinations()) {
                System.out.println(ce);
            }
            DestinationsDao newDestinationsDao = (DestinationsDao) bind.unmarshalling(DestinationsDao.class, dest.getXMLPath());

            System.out.println("Unmarshall objects");
            for (DestinationEntity cl : newDestinationsDao.getDestinations()) {
                System.out.println(cl);
                dest.insert(cl);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void bindingTour() {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            TourDao tour = new TourDao(conn);
            List<TourEntity> tours = tour.selectAll();
            tour.deleteAll();

            for (TourEntity entityTour : tours) {
                int idDestination = entityTour.getIdDestination();
                DestinationDao dest = new DestinationDao(conn);
                DestinationEntity destEnt = new DestinationEntity();
                destEnt = dest.selectByPK(idDestination, DestinationEntity.columnId, destEnt);
                entityTour.setDestinationPlaceByIdDestination(destEnt);
            }

            Binding bind = new Binding();
            ToursDao toursDao = new ToursDao();
            toursDao.setTours(tours);

            System.out.println("Marshall objects");
            bind.marshalling(ToursDao.class, toursDao, tour.getXMLPath());
            for (TourEntity ce : toursDao.getTours()) {
                System.out.println(ce);
            }
            ToursDao newToursDao = (ToursDao) bind.unmarshalling(ToursDao.class, tour.getXMLPath());

            System.out.println("Unmarshall objects");
            for (TourEntity cl : newToursDao.getTours()) {
                System.out.println(cl);
                tour.insert(cl);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void bindingOrder() {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            OrderDao order = new OrderDao(conn);
            List<OrderEntity> orders = order.selectAll();
            order.deleteAll();

            for (OrderEntity entityOrder : orders) {
                int idEmployee = entityOrder.getIdEmployee();
               EmployeeDao empl = new EmployeeDao(conn);
                EmployeeEntity emplEnt = new EmployeeEntity();
                emplEnt = empl.selectByPK(idEmployee, EmployeeEntity.columnId, emplEnt);
                entityOrder.setEmployeeByIdEmployee(emplEnt);

                int idClient = entityOrder.getIdClient();
                ClientDao client = new ClientDao(conn);
                ClientEntity clientEnt = new ClientEntity();
                clientEnt = client.selectByPK(idClient, ClientEntity.columnIdClient, clientEnt);
                entityOrder.setClientByIdClient(clientEnt);

                int idTour = entityOrder.getIdTour();
                TourDao tour = new TourDao(conn);
                TourEntity tourEnt = new TourEntity();
                tourEnt = tour.selectByPK(idTour, TourEntity.columnId, tourEnt);
                entityOrder.setCatalogueToursByIdTour(tourEnt);
            }

            Binding bind = new Binding();
            OrdersDao ordersDao = new OrdersDao();
            ordersDao.setOrders(orders);

            System.out.println("Marshall objects");
            bind.marshalling(OrdersDao.class, ordersDao, order.getXMLPath());
            for (OrderEntity ce : ordersDao.getOrders()) {
                System.out.println(ce);
            }
            OrdersDao newOrdersDao = (OrdersDao) bind.unmarshalling(OrdersDao.class, order.getXMLPath());

            System.out.println("Unmarshall objects");
            for (OrderEntity cl : newOrdersDao.getOrders()) {
                System.out.println(cl);
                order.insert(cl);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
