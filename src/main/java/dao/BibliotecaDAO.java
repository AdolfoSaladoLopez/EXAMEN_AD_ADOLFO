package dao;

import java.util.HashSet;
import java.util.List;

import models.Libro;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * @author FranciscoRomeroGuill
 */
public class BibliotecaDAO {

    private static SessionFactory sessionFactory;

    static {
        try {
            /* Completar conexión con hibernate */
            sessionFactory = new Configuration().configure().buildSessionFactory();
            System.out.println("Conexión realizada");
        } catch (Exception ex) {
            System.out.println("Error iniciando Hibernate");
            System.out.println(ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public void saveLibro(Libro e) {

        /* Guarda un libro con todos sus ejemplares en la base de datos */
        try (var s = sessionFactory.openSession()) {
            Transaction t = s.beginTransaction();
            s.saveOrUpdate(e);
            t.commit();
            System.out.println("Método saveLibro implementado");
        }
    }

    public HashSet<Libro> findByEstado(String estado) {
        HashSet<Libro> salida = new HashSet<Libro>();
        /* 
         Devuelve el conjunto de libros que tenga el estado indicado      
        */

        try (var s = sessionFactory.openSession()) {
            Query q = s.createQuery("SELECT l From Libro l, Ejemplar e WHERE l = e.libro AND e.estado = :estado");
            q.setParameter("estado", estado);

            salida.addAll(q.list());

        }
        System.out.println("Método findByEstado implementado");

        return salida;

    }

    public void printInfo() {

        List<Libro> libros;
        
        /* 
          Muestra por consola todos los libros de la biblioteca y el número
          de ejemplares disponibles de cada uno.
          
          Debe imprimirlo de esta manera:
        
          Biblioteca
          ----------
          Como aprender java en 24h. (3)
          Como ser buena persona (1)
          Aprobando exámenes fácilmente (5)
          ...
        
        */

        try (var s = sessionFactory.openSession()) {
            var q = s.createQuery("From Libro", Libro.class);
            libros = q.list();
        }
        System.out.println("Biblioteca");
        System.out.println("----------");
        for(Libro libro : libros) {
            System.out.println(libro.getTitulo() + ". (" + libro.getEjemplares().size() + ")");
        }

        System.out.println("Método printInfo implementado");

    }

}
