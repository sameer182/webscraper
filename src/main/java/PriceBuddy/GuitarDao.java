package PriceBuddy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

    public class guitarDao {
        SessionFactory sessionFactory;

        public guitar simpleSave(guitar guitar) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.saveOrUpdate(guitar);
                session.getTransaction().commit();
                System.out.println("Guitar added to the database with ID: " + guitar.getId());
                return guitar;
            } catch (Exception e) {
                System.err.println("Error saving guitar to the database.");
                e.printStackTrace();
                return null;
            }
        }

        public comparison simpleSave(comparison comparison) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.saveOrUpdate(comparison);
                session.getTransaction().commit();
                System.out.println("Comparison added to the database with ID: " + comparison.getId());
                return comparison;
            } catch (Exception e) {
                System.err.println("Error saving comparison to the database.");
                e.printStackTrace();
                return null;
            }
        }
        public guitar saveAndMergeGuitar(guitar guitar, int price, String url) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

//                // Check if a guitar with the same model already exists
                guitar existingGuitar = getGuitarByModel(guitar.getModel());

                if (existingGuitar == null) {
                    // No existing guitar found, save the new guitar
                    session.saveOrUpdate(guitar);
                    System.out.println("New Guitar added to the database with ID: " + guitar.getId());
                    existingGuitar = guitar; // Set existingGuitar to the newly added guitar
                } else {
                    // Existing guitar found, update the details
                    existingGuitar.setName(guitar.getName());
                    existingGuitar.setBrands(guitar.getBrands());
                    existingGuitar.setDescription(guitar.getDescription());
                    existingGuitar.setPic(guitar.getPic());
//                    session.merge(existingGuitar);
                    System.out.println("Existing Guitar merged in the database with ID: " + existingGuitar.getId());
                }

                // Save the comparison record
                comparison gComparison = new comparison();
                gComparison.setGuitar(existingGuitar);
                gComparison.setPrice(price);
                gComparison.setUrl(url);
                session.save(gComparison);
                System.out.println("Comparison added to the database with ID: " + gComparison.getId());

                session.getTransaction().commit();
                return existingGuitar;
            } catch (Exception e) {
                System.err.println("Error saving/merging guitar and comparison to the database.");
                e.printStackTrace();
                return null;
            }
        }

        // Helper method to get a guitar by model
        private guitar getGuitarByModel(String model) {
            try (Session session = sessionFactory.openSession()) {
                System.out.println("Searching for guitar with model: " + model);
                return session.createQuery("FROM guitar WHERE LOWER(TRIM(model))= :model", guitar.class)
                        .setParameter("model", model.trim())
                        .uniqueResult();
            } catch (Exception e) {
                System.err.println("Error retrieving guitar by model from the database.");
                e.printStackTrace();
                return null;
            }
        }

        public void init() {
            try {
                StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
                standardServiceRegistryBuilder.configure("hibernate.cfg.xml");
                StandardServiceRegistry registry = standardServiceRegistryBuilder.build();

                try {
                    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

                    // Enable SQL query logging
                    sessionFactory.getProperties().put("hibernate.show_sql", "true");
                } catch (Exception e) {
                    System.err.println("Session Factory build failed.");
                    e.printStackTrace();
                    StandardServiceRegistryBuilder.destroy(registry);
                }

                System.out.println("Session factory built.");
            } catch (Throwable ex) {
                System.err.println("SessionFactory creation failed." + ex);
            }
        }



        public void setSessionFactory(SessionFactory sessionFactory) {
            this.sessionFactory = sessionFactory;
        }

        public SessionFactory getSessionFactory() {
            return sessionFactory;
        }
    }




