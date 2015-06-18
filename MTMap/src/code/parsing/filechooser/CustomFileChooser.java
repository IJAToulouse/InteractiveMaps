package code.parsing.filechooser;

import java.io.File;

import javax.swing.JFileChooser;

/**
* @author Alexis Paoleschi
* @mail alexis.paoleschi@gmail.com
*/

//Classe de choix de fichier personnalis� � un type de fichier
public class CustomFileChooser extends JFileChooser {
	private static final long serialVersionUID = 1L;
	//Extension du type de fichier d�sir�
	private String extension;

	public CustomFileChooser(final String extension, String titre) {
		super();
		this.extension = extension;
		//Une s�lection de multiples fichiers n'a pas lieu d'�tre
		this.setMultiSelectionEnabled(false);
		this.setDialogTitle(titre);
		//On d�finit le dossier de base au package "files" contenant tous les fichiers n�cessaires au parsing
		this.setCurrentDirectory(new File(".\\src\\files"));
		
		//On applique le filtre de fichier personnalis�
		this.addChoosableFileFilter(new CustomFileFilter(this));
		
		this.setAcceptAllFileFilterUsed(false);
	}

	public String getExtension() {
		return extension;
	}
}
