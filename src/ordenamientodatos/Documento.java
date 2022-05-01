package ordenamientodatos;

import java.io.BufferedReader;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Documento {

    private String nombre;
    private String apellido1;
    private String apellido2;
    private String tipoDocumento;

    public Documento() {
        nombre = "";
        apellido1 = "";
        apellido2 = "";
        tipoDocumento = "";
    }

    public Documento(String apellido1,
            String apellido2,
            String nombre,
            String tipoDocumento) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.tipoDocumento = tipoDocumento;
    }

    public String getNombreCompleto() {
        return apellido1 + " " + apellido2 + " " + nombre;
    }

    public String gettipoDocumento() {
        return tipoDocumento;
    }

    //********** Metodos estaticos ********** 
    public static Documento[] documentos;

    public static void agregar(Documento d) {
        if (documentos == null) {
            documentos = new Documento[1];
        } else {
            documentos = (Documento[]) Util.redimensionar(documentos, documentos.length + 1);
        }
        documentos[documentos.length - 1] = d;
    }

    public static void ObtenerLista(String nombreArchivo) {
        try {
            BufferedReader br = Archivo.abrirArchivo(nombreArchivo);
            String linea = br.readLine();
            while (linea != null) {
                String[] textos = linea.split(";");
                if (textos.length >= 4) {
                    Documento d = new Documento(textos[0],
                            textos[1],
                            textos[2],
                            textos[3]
                    );
                    agregar(d);
                }
                linea = br.readLine();
            }
        } catch (Exception ex) {

        }
    }//ObtenerLista

    public static void mostrarLista(JTable tbl) {
        String[] encabezados = new String[]{"Nombre", "Tipo Documento"};
        String[][] datos = new String[documentos.length][encabezados.length];
        for (int i = 0; i < documentos.length; i++) {
            datos[i][0] = documentos[i].getNombreCompleto();
            datos[i][1] = documentos[i].gettipoDocumento();
        }

        tbl.setModel(new DefaultTableModel(datos, encabezados));
    }//mostrarLista

    public static String ordenarBurbuja() {
        Util.iniciarCronometro();
        for (int i = 0; i < documentos.length - 1; i++) {
            
            System.out.println("Vamos en "+i);
            
            for (int j = i + 1; j < documentos.length; j++) {
                if (documentos[i].getNombreCompleto().compareTo(documentos[j].getNombreCompleto()) > 0) {
                    Documento t = documentos[i];
                    documentos[i] = documentos[j];
                    documentos[j] = t;
                    
                    System.out.println("Intercambio "+documentos[i].getNombreCompleto()+" por "+documentos[j].getNombreCompleto());
                }
            }
        }
        return Util.obtenerTextoTiempoCronometro();
    }

}
