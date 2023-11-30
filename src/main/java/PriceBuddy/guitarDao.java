package PriceBuddy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class guitarDao {
    SessionFactory sessionFactory;

    /**
     * Demonstrates simple saving without merging
     */
    public guitar simpleSave(guitar guitar) {
        //Get a new Session instance from the session factory and start transaction
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        //Add Student, degree and university to database - will not be stored until we commit the transaction
        session.saveOrUpdate(guitar);

        //NOTE: THIS IS THE WRONG WAY TO SAVE DATA - IT WILL CREATE A LOT OF DUPLICATE ROWS!

        //Commit transaction to save it to database
        session.getTransaction().commit();

        //Close the session and release database connection
        session.close();
        System.out.println("Guitar added to database with ID: " + guitar.getId());
        return guitar;
    }

    public void init() {
        try {
            //Create a builder for the standard service registry
            StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();

            //Load configuration from hibernate configuration file.
            //Here we are using a configuration file that specifies Java annotations.
            standardServiceRegistryBuilder.configure("hibernate.cfg.xml");

            //Create the registry that will be used to build the session factory
            StandardServiceRegistry registry = standardServiceRegistryBuilder.build();
            try {
                //Create the session factory - this is the goal of the init method.
                sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                    /* The registry would be destroyed by the SessionFactory,
                        but we had trouble building the SessionFactory, so destroy it manually */
                System.err.println("Session Factory build failed.");
                e.printStackTrace();
                StandardServiceRegistryBuilder.destroy(registry);
            }


            //Output result
            System.out.println("Session factory built.");

        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("SessionFactory creation failed." + ex);
        }
    }

    /**
     * Saves and merges data from student, degree and university classes
     */
    public void saveAndMerge(guitar guitar) throws Exception {
        //Get a new Session instance from the session factory and start transaction
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        //First find or create university
        String queryStr = "from guitar where name=" + guitar.getName()+ "";
        List<guitar> guitarList = session.createQuery(queryStr).getResultList();

        //University is in database
        if (guitarList.size() == 1) {//Found a single University
            //Update university location, if we want to
            guitarList.get(0);

        }
        //No university with that name in database
        else if (guitarList.size() == 0) {
            session.saveOrUpdate(guitar.getName());
        }
        //Error
        else {
            throw new Exception("Multiple guitars with the same name");
        }

    }
}
