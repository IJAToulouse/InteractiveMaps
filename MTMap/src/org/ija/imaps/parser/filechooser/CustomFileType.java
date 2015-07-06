package org.ija.imaps.parser.filechooser;

import java.io.File;

/**
* @author Alexis Paoleschi
* @mail alexis.paoleschi@gmail.com
*/

//Classe des fichiers de type personnalis�
public class CustomFileType {
	//D�finitions des extensions int�ressantes au choix de fichiers personnalis�
    public final static String svg = "svg";
    public final static String xml = "xml";
    public final static String jpg = "jpg";

    //M�thode de r�cup�ration de l'extension d'un fichier
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}
