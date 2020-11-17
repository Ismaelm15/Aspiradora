/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programa;

import javax.swing.JOptionPane;
import java.util.Date;
import java.util.Arrays;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

/**
 *
 * @author ismae
 */
public class Aspiradora {

    //Variables para el calculo de bateria en cada modo y la posicion exacta del robot
    static final double ASP = 1.5;
    static final double ASPYFREG = 2.25;
    static int posicion = 0;
    static String USUARIO = "Usuario";
    static String CONTRASENIA = "Usuario";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //creacion de los arrays para la habitacion , los metros y el menu ademas de la carga, la longitud de los arrays, la opcion para el fregado y aspirado o aspirado y el booleano para salir
        double carga = 0;
        int n = 0;
        String usuario;
        int[] mCuadrado = null;
        String habitacion[] = null;
        char password[] = null;

        //if (CONTRASENIA.equals(new String(password))){}
        int opcion;
        boolean salir = false;
        do {
            usuario = JOptionPane.showInputDialog(null, "Introduzca el usuario");//Peticion de usuario

            JPanel panel = new JPanel();
            JLabel label = new JLabel("Introduzca una contraseña");
            JPasswordField pass = new JPasswordField(10);
            panel.add(label);
            panel.add(pass);
            String[] options = new String[]{"Cancelar", "Aceptar"};
            int option = JOptionPane.showOptionDialog(null, panel, "Contraseña",
                    JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[1]);
            if (option == 1) // pressing OK button
            {
                password = pass.getPassword();

                if (usuario.equals(USUARIO) && CONTRASENIA.equals(new String(password))) {
                    do {

                        String[] menu = {"Configurar el sistema", "Carga", "Aspiracion", "Aspiracion y fregado", "Estado General", "Base de carga", "Salir"};
                        //Creacion del switch y del menu con el showOptionDialog
                        opcion = JOptionPane.showOptionDialog(null, "Elija una opcion", "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, menu, menu[0]);

                        switch (opcion) {
                            case 0: {
                                //Creacion de strings array y llamada a las funciones para pedir metros, nombre y numero de habitaciones
                                n = nHabitaciones();
                                habitacion = new String[n];
                                mCuadrado = new int[n];
                                for (int i = 0; i < n; i++) {

                                    habitacion[i] = nombreHab();
                                    if (habitacion[i] == null) {
                                        n = 0;
                                        break;
                                    }
                                    mCuadrado[i] = metroCuadrado();

                                }
                                break;
                            }
                            case 1: {//Llamada a la funcion para establecer la carga e igualacion de posicion a -1 para hacer saber que el robot está en la base de carga
                                carga = carga();
                                posicion = -1;
                                break;
                            }
                            case 2: {
                                carga = process(1, n, habitacion, mCuadrado, carga);//llamada a la funcion process con la opcion en 1 para el modo aspirado
                                break;
                            }
                            case 3: {
                                carga = process(2, n, habitacion, mCuadrado, carga);//llamada a la funcion process con la opcion en 2 para el modo aspirado y fregado
                                break;
                            }
                            case 4: {
                                estado(habitacion, carga, mCuadrado);//llamada a la funcion estado para saber el estado general
                                break;
                            }
                            case 5: {//Igualacion de la variable carga a 100 e igualacion de posicion a -1 para hacer saber que el robot está en la base de carga
                                posicion = -1;
                                carga = 100;
                                break;
                            }
                            default: {//Tanto la X del JOptionPane como el boton salir llevan al Default
                                salir = true;
                                break;
                            }

                        }

                    } while (!salir);//Condicion de salida del bucle
                } else {
                    JOptionPane.showMessageDialog(null, "Contraseña o usuario equivocado intentelo de nuevo", "Error!", JOptionPane.ERROR_MESSAGE);//Mensaje de equivocacion
                }

            }
        } while (!usuario.equals(USUARIO) || !CONTRASENIA.equals(new String(password)));//Condicion de repeticion del programa mientras la contraseña sea fallida
    }

    public static int nHabitaciones() {
        //Declaracion de las variables salir y numero habitaciones
        boolean salir;
        int numeroHab = 0;
        do {//Iteracion hasta que el usuario introduzca todo correctamente
            salir = true;
            try {
                String sNumeroHab = JOptionPane.showInputDialog(null, "Introduzca el numero de habitaciones");
                if (sNumeroHab == null) {//para que el cancelar y la cruz de salir funcionen correctamente
                    break;
                }
                numeroHab = Integer.parseInt(sNumeroHab);//parse de string a int

                if (numeroHab < 0) {
                    JOptionPane.showMessageDialog(null, "Dato incorrecto intentelo de nuevo", "Error!", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {//Aplicacion de la exepcion NumberFormatException
                salir = false;
            }
        } while (!salir);
        return numeroHab;
    }

    public static int metroCuadrado() {
        //Declaracion de variable, salir y metros cuadrados
        boolean salir;
        int mC = 0;
        do {//Iteracion hasta que el usuario introduzca todo correctamente
            salir = true;
            try {
                String sMC = JOptionPane.showInputDialog(null, "Introduzca el numero de metros cuadrados de la habitacion");
                if (sMC == null) {
                    salir = true;//pendiente de arreglar no puedo evitar que entre a limpieza si no puedo resetear n a 0
                }
                mC = Integer.parseInt(sMC);
                if (mC < 0 || mC > 100) {
                    JOptionPane.showMessageDialog(null, "Dato incorrecto intentelo de nuevo", "Error!", JOptionPane.ERROR_MESSAGE);
                    salir = false;
                }
            } catch (NumberFormatException e) {//Aplicacion de la exepcion NumberFormatException
                salir = false;
            }
        } while (!salir);

        return mC;
    }

    public static String nombreHab() {
        //Declaracion de variable sNHab
        String sNHab;
        //Iteracion hasta que el usuario introduzca todo correctamente
        sNHab = JOptionPane.showInputDialog(null, "Introduzca el nombre de la habitacion");

        return sNHab;
    }

    public static double carga() {
        //Declaracion de variable salir y carga
        boolean salir;
        double carga = 0;
        do {//Iteracion hasta que el usuario introduzca todo correctamente
            salir = true;
            try {
                String sCarga = JOptionPane.showInputDialog(null, "Introduzca la carga de la aspiradora");
                carga = Double.parseDouble(sCarga);
                if (carga < 0 || carga > 100) {
                    JOptionPane.showMessageDialog(null, "Dato incorrecto intentelo de nuevo", "Error!", JOptionPane.ERROR_MESSAGE);
                    salir = false;
                }
                
            } catch (NumberFormatException | NullPointerException e) {//Aplicacion de la exepcion NumberFormatException
                
                JOptionPane.showMessageDialog(null, "La bateria no ha podido ser actualizada volviendo al menu...", "Error!", JOptionPane.ERROR_MESSAGE);  
            }
        } while (!salir);

        return carga;
    }

    public static double limpiar(String hab, int metros, int op) {
        double estbat;
        if (op == 0) {
            estbat = metros * ASP;//Si la opcion esta igualada a 0 calcula los metros de la aspiracion
        } else {
            estbat = metros * ASPYFREG;//Si la opcion esta igualada a 1 calcula los metros de la aspiracion y el fregado
        }
        return estbat;
    }

    public static double comprobar(double cargaT, String habitacion,int pos) {
        //Declaracion de carga
        double carga = 0;
        if (cargaT >= 3) {//comprobacion de que carga sea superior a 3
            carga = cargaT;
            posicion=pos;
            JOptionPane.showMessageDialog(null, "Se ha limpiado la habitación: " + habitacion);
        } else if (cargaT >= 0) {//comprobacion de que carga sea superior a 0
            carga = 3;
            JOptionPane.showMessageDialog(null, "Se ha limpiado parte de la habitación: " + habitacion + ", a la aspiradora sólo le queda un 3% de batería. No se puede limpiar la habitacion", "Error!", JOptionPane.ERROR_MESSAGE);
        } else {//Mensaje de cuando no tiene bateria suficiente
            JOptionPane.showMessageDialog(null, "La aspiradora no tiene suficiente batería para la habitación: " + habitacion + ".No se puede limpiar la habitacion", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        return carga;
    }

    public static double process(int op, int n, String[] habitacion, int[] mcuadrado, double carga) {
        //Creacion del array modo
        String[] modo = {"Modo Completo", "Modo Dependencia"};
        if (n != 0) {//Comprobacion de que tiene habitaciones introducidas
            int selector = JOptionPane.showOptionDialog(null, "Elija una opcion", "Modo Aspiración", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, modo, modo[0]);
            selector++;
            switch (selector) {//Declaracion del switch
                case 1:

                    for (int i = 0; i < n; i++) {
                        double cargaT = carga - limpiar(habitacion[i], mcuadrado[i], op);
                        carga = comprobar(cargaT, habitacion[i],i);
                        
                        if (carga == 3) {//Comprueba la carga despues de limpiar cada habitacion hasta que sea igual a 3
                            break;
                        }

                    }
                    break;

                case 2:
                    String[] copy;
                    copy = Arrays.copyOf(habitacion, habitacion.length + 1);//Copia el array habitaciones en copy y le añade una posicion mas que se llama salir, ademas de crear unas variables booleanas llamadas carga y salir
                    copy[copy.length - 1] = "Salir";
                    double cargaP = 0;
                    boolean bsalir = false;
                    do {//Iteracion mientras la bateria sea superior a 3 y no se pulse el boton salir

                        int recorrer = JOptionPane.showOptionDialog(null, "Elija la habitación a limpiar", null, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, copy, copy[0]);//Menu para la limpieza de habitaciones
                        if (recorrer == copy.length - 1) {//copy.length-1 es la opcion salir
                            bsalir = true;
                        } else {
                            cargaP = carga - limpiar(habitacion[recorrer], mcuadrado[recorrer], 0);
                            carga = comprobar(cargaP, habitacion[recorrer],recorrer);
                            posicion = recorrer;//Llamada a la funcion limpieza y a la comprobacion de bateria
                        }

                    } while (carga > 3 && !bsalir);

                    break;
            }
        } else {
            JOptionPane.showMessageDialog(null, "No ha metido datos de habitaciones introduzcalas en Configurar el sistema", "Error!", JOptionPane.ERROR_MESSAGE);//Mensaje por si no se introducen datos de habitaciones
        }
        return carga;
    }

    public static void estado(String[] habitacion, double nvCarga, int[] metros) {
        Date fecha = new Date();//Declaracion de fecha
        JOptionPane.showMessageDialog(null, fecha.toString());//Mostrar fecha por pantalla
        JOptionPane.showMessageDialog(null, "El robot tiene un " + nvCarga + " de bateria");//Mostrar el nivel de bateria
        try {//por si no hay habitaciones introducidas
            if (posicion == -1) {
                JOptionPane.showMessageDialog(null, "El robot está ubicado en la base de carga");//El -1 indica que el robot esta en la base de carga

            } else {
                JOptionPane.showMessageDialog(null, "El robot está ubicado en la habitacion " + habitacion[posicion]);
            }
            for (int i = 0; i < habitacion.length; i++) {
                JOptionPane.showMessageDialog(null, "Nombre de la habitacion: " + habitacion[i] + ", metros de la habitacion " + metros[i] + " metros cuadrados");//Mostrar todas las habitaciones

            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "No existen habitaciones en la base de datos, por favor introduzcalas", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

}
