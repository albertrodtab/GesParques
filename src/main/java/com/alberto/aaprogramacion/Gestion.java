package com.alberto.aaprogramacion;

import com.alberto.aaprogramacion.dao.CiudadDAO;
import com.alberto.aaprogramacion.dao.Conexion;
import com.alberto.aaprogramacion.dao.ParqueDAO;
import com.alberto.aaprogramacion.domain.Ciudad;
import com.alberto.aaprogramacion.domain.Parque;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Gestion {
    private Boolean salir;
    private final Scanner teclado = new Scanner(System.in);
    private Conexion conexion;
    private ParqueDAO parqueDAO;
    private CiudadDAO ciudadDao;

    /**
     * Arranca la aplicación y conecta con la base de datos.
     */
    public Gestion() {
        salir = false;
        conexion = new Conexion();
        conexion.conectar();
        parqueDAO = new ParqueDAO(conexion);
        ciudadDao = new CiudadDAO(conexion);
    }

    /**
     * Permite ejecutar el menú principal de la aplicación
     */
    public void ejecutar() {
        do {
            System.out.println("========================================================");
            System.out.println("=============Aplicación GesParques v0.1=================");
            System.out.println("========================================================");
            System.out.println("                1. Ver Parques                          ");
            System.out.println("                2. Registrar Parque                     ");
            System.out.println("                3. Modificar Parque                     ");
            System.out.println("                4. Eliminar Parque                      ");
            System.out.println("                5. Listados pre-diseñados               ");
            System.out.println("                x. Salir                                ");
            System.out.print("Selecciona: ");
            String opcion = teclado.nextLine();

            switch (opcion) {
                case "1":
                    verParques();
                    break;
                case "2":
                    registrarParque();
                    break;
                case "3":
                    modificarParque();
                    break;
                case "4":
                    eliminarParque();
                    break;
                case "5":
                    listadoUsuario();
                    break;
                case "x":
                case "X":
                    salir();
                    break;
                default:
                    System.out.println("========================================================");
                    System.out.println("=========Opción incorrecta, selecciona de nuevo=========");
                    System.out.println("========================================================");
            }
        } while (!salir);
    }

    /**
     * Permite ejecutar el menú secundario de visualización de parques de la aplicación
     */
    private void verParques() {
        do {
            System.out.println("========================================================");
            System.out.println("===Quieres consultar todos los parques o uno concreto===");
            System.out.println("========================================================");
            System.out.println("    1. Ver Todos los Parques registrados                ");
            System.out.println("    2. Escoger todos los Parques de una Ciudad          ");
            System.out.println("    3. Escoger todos los Parques de una Comunidad       ");
            System.out.println("    4. Escoger todos los Parques cuyo nombre contenga:  ");
            System.out.println("    x. Volver al menú principal                         ");
            System.out.print("Selecciona: ");
            String opcion = teclado.nextLine();

            switch (opcion) {
                case "1":
                    try {
                        ArrayList<Parque> parques = parqueDAO.obtenerParques();
                        for (Parque parque : parques) {
                            System.out.println(parque.getNparque() + " / " + parque.getExtension());
                        }
                    } catch (SQLException sqle) {
                        sqle.printStackTrace();
                        System.out.println("Se ha producido un problema leyendo los datos");
                    }
                    break;
                case "2":
                    try {
                        System.out.println("Introduce la ciudad por la que deseas realizar la búsqueda: ");
                        String ciudad = teclado.nextLine();
                        ArrayList<Parque> parques = parqueDAO.obtenerParquesCiudad(ciudad);
                        for (Parque parque : parques) {
                            System.out.println(parque.getNparque() + " / " + parque.getExtension());
                        }
                    } catch (SQLException sqle) {
                        sqle.printStackTrace();
                        System.out.println("Se ha producido un problema leyendo los datos");
                    }
                    break;
                case "3":
                    try {
                        System.out.println("Introduce la CCAA por la que deseas realizar la búsqueda: ");
                        String ccaa = teclado.nextLine();
                        ArrayList<Parque> parques = parqueDAO.obtenerParquesCcaa(ccaa);
                        for (Parque parque : parques) {
                            System.out.println(parque.getNparque() + " / " + parque.getExtension());
                        }
                    } catch (SQLException sqle) {
                        sqle.printStackTrace();
                        System.out.println("Se ha producido un problema leyendo los datos");
                    }
                    break;
                case "4":
                    try {
                        System.out.println("Introduce la parte del nombre por la que deseas realizar la búsqueda: ");
                        String cadena = teclado.nextLine();
                        ArrayList<Parque> parques = parqueDAO.obtenerParquesNombre(cadena);
                        for (Parque parque : parques) {
                            System.out.println(parque.getNparque() + " / " + parque.getExtension());
                        }
                    } catch (SQLException sqle) {
                        sqle.printStackTrace();
                        System.out.println("Se ha producido un problema leyendo los datos");
                    }
                    break;
                case "x":
                case "X":
                    ejecutar();
                    break;
                default:
                    System.out.println("========================================================");
                    System.out.println("=========Opción incorrecta, selecciona de nuevo=========");
                    System.out.println("========================================================");
            }
        } while (!salir);

    }

    /**
     * Permite ejecutar el menú secundario de visualización de parques según unos criterios
     * que introduce el usuario.
     */
    private void listadoUsuario() {
        do {
            System.out.println("========================================================");
            System.out.println("=================Listados prediseñados==================");
            System.out.println("========================================================");
            System.out.println("    1. Listado parques por ciudad extensión > número    ");
            System.out.println("    2. Ciudades con parques suma extension > número     ");
            System.out.println("    x. Volver al menú principal                         ");
            System.out.print("Selecciona: ");
            String opcion = teclado.nextLine();

            switch (opcion) {
                case "1":
                    try {
                        System.out.println("Introduce la Ciudad que deseas consultar: ");
                        String ciudad = teclado.nextLine();
                        System.out.println("Introduce el valor de extensión que deben superar los parques: ");
                        int extension = teclado.nextInt();
                        teclado.nextLine();
                        int resultado = parqueDAO.listadoParquesUsuario(ciudad, extension);
                        System.out.println("El número de parques de " + ciudad + " cuya extensión individual de sus " +
                                "parques es mayor que " + extension + " metros cuadrados es " + resultado);

                    } catch (SQLException sqle) {
                        sqle.printStackTrace();
                        System.out.println("Se ha producido un problema leyendo los datos");
                    }
                    break;
                case "2":
                    try {
                        System.out.println("Introduce la extensión a partir de la cual quieres ver las ciudades cuya suma de su parques es mayor: ");
                        int extension = teclado.nextInt();
                        teclado.nextLine();
                        ArrayList<Ciudad> ciudades = ciudadDao.listadoCiudadesUsuario(extension);
                        for (Ciudad ciudad : ciudades) {
                            System.out.println(ciudad.getnCiudad());
                        }
                    } catch (SQLException sqle) {
                        sqle.printStackTrace();
                        System.out.println("Se ha producido un problema leyendo los datos");
                    }
                    break;
                case "x":
                case "X":
                    ejecutar();
                    break;
                default:
                    System.out.println("========================================================");
                    System.out.println("=========Opción incorrecta, selecciona de nuevo=========");
                    System.out.println("========================================================");
            }
        } while (!salir);

    }

    /**
     * Permite registrar un nuevo parque en la base de datos.
     */
    private void registrarParque() {
        System.out.println("Introduce la Ciudad: ");
        String nCiudad = teclado.nextLine();
        System.out.println("Introduce el nombre del parque: ");
        String nParque = teclado.nextLine();
        System.out.println("Introduce la extension: ");
        int extension = teclado.nextInt();
        teclado.nextLine();
        try {
            Parque parque = new Parque();
            Ciudad ciudad;
            ciudad = ciudadDao.buscarCiudad(nCiudad);
            parque.setIdCiudad(ciudad.getIdCiudad());
            parque.setNparque(nParque);
            parque.setExtension(extension);
            if (parque.getIdCiudad() != null) {
                parqueDAO.registrarParqueDAO(parque);
                System.out.println("El parque se ha registrado correctamente.");

            } else {
                System.out.println("La ciudad no existe no se puede registrar el parque.");
            }
        } catch (SQLException sqle) {
            //sqle.printStackTrace();
            System.out.println("Se ha producido un problema. Inténtelo de nuevo");

        }
    }

    /**
     * Permite eliminar parques de la base de datos.
     */
    private void eliminarParque() {
        System.out.println("Introduce la ciudad de la cual quieres eliminar los parques: ");
        String ciudad = teclado.nextLine();

        try {
            Ciudad ciudadBorr;
            ciudadBorr = ciudadDao.buscarCiudad(ciudad);
            Parque parque = new Parque();
            parque.setIdCiudad(ciudadBorr.getIdCiudad());
            parqueDAO.eliminarParqueDao(ciudad);
            if(parque.getIdCiudad() == null){
                System.out.println("Esa ciudad no tiene parques registrados en la base de datos.");
            }else {
                System.out.println("Se han eliminado los parques que pertenecían a la ciudad: " + ciudad + ".");
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.out.println("Se ha producido un problema. Inténtelo de nuevo");
        }
    }

    /**
     * Permite modificar/actualizar un parque de la base de datos.
     */
    private void modificarParque() {
        System.out.println("Introduce el nombre del parque que deseas modificar: ");
        String nParque = teclado.nextLine();
        try {
            ArrayList<Parque> parques = parqueDAO.seleccionaParqueDAO(nParque);
            for (Parque parque : parques) {
                Ciudad ciudad;
                String idCiudad = parque.getIdCiudad();
                ciudad = ciudadDao.buscarCiudadId(idCiudad);
                System.out.println(parque.getNparque() + " / " + ciudad.getnCiudad() + " / " + parque.getExtension());

            }
            if (parques.isEmpty()) {
                System.out.println("No existe ese parque");
            } else {
                Parque parque;
                System.out.println("Introduce los nuevos valores para modificar este parque: ");
                System.out.print("Nombre Parque: ");
                String nParqueNuevo = teclado.nextLine();
                System.out.print("Ciudad a la que pertenece: ");
                String nciudad = teclado.nextLine();
                System.out.print("Extensión: ");
                int extension = teclado.nextInt();
                teclado.nextLine();
                parque = parqueDAO.buscarParqueId(nParque);
                parque.setIdParque(parque.getIdParque());
                Ciudad ciudad;
                ciudad = ciudadDao.buscarCiudad(nciudad);
                parque.setIdCiudad(ciudad.getIdCiudad());
                parque.setNparque(nParqueNuevo);
                parque.setExtension(extension);
                if (parque.getIdCiudad() != null) {
                    parqueDAO.actualizarParqueDAO(parque);
                    System.out.println("El parque se ha actualizado correctamente.");

                } else {
                    System.out.println("La ciudad no existe no se puede registra la modificación");
                }
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.out.println("Se ha producido un problema leyendo los datos");
        }
    }

    /**
     * Permite salir del programa de una manera controlada.
     */
    private void salir() {
        conexion.desconectar();
        System.out.println("========================================================");
        System.out.println("========SE HA CERRADO EL PROGRAMA CORRECTAMENTE=========");
        System.out.println("========================================================");
        salir = true;
    }
}
