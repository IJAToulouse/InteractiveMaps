package org.ija.imaps.parser.filechooser;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
* @author Alexis Paoleschi
* @mail alexis.paoleschi@gmail.com
*/

//Classe du filtre de fichier personnalisé aux fichiers de type SVG, XML et JPG
public class CustomFileFilter extends FileFilter {
	private CustomFileChooser fileChooser;
	
	public CustomFileFilter(CustomFileChooser fc) {
		super();
		
		this.fileChooser = fc;
	}

	//Méthode d'acceptation des fichiers SVG, XML ou JPG
	@Override
	public boolean accept(File f) {
	    if (f.isDirectory()) {
	        return true;
	    }
	    
	    //Cas des fichiers SVG
	    if(fileChooser.getExtension().equals("svg")) {
		    String extension = CustomFileType.getExtension(f);
		    if (extension != null) {
		        if (extension.equals(CustomFileType.svg)) {
		                return true;
		        } else {
		            return false;
		        }
		    }
		    return false;
	    }
	    
	    //Cas des fichiers XML
	    if(fileChooser.getExtension().equals("xml")) {
		    String extension = CustomFileType.getExtension(f);
		    if (extension != null) {
		        if (extension.equals(CustomFileType.xml)) {
		                return true;
		        } else {
		            return false;
		        }
		    }
		    return false;
	    }
	    
	  //Cas des fichiers JPG
	    if(fileChooser.getExtension().equals("jpg")) {
		    String extension = CustomFileType.getExtension(f);
		    if (extension != null) {
		        if (extension.equals(CustomFileType.jpg)) {
		                return true;
		        } else {
		            return false;
		        }
		    }
		    return false;
	    }
	    
	    //Autre types de fichiers
	    return false;
	}

	@Override
	public String getDescription() {
		return "Fichiers de type " + fileChooser.getExtension();
	}
}
