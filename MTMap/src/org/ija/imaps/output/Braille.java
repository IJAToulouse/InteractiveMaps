package org.ija.imaps.output;

import java.text.Normalizer;
import java.util.regex.Pattern;

import org.ija.imaps.main.MapScene;

import fr.irit.elipse.hardware.brailledisplay.jbraille;

/**
* @author Alexis Paoleschi
* @mail alexis.paoleschi@gmail.com
*/

//Classe de la sortie braille
public class Braille {
	//Objet qui appellera les primitives de l'afficheur braille
	private jbraille j;
	//Chaîne de caractère qui sera affichée sur l'afficheur braille
	private String sortieBraille;

	public Braille(jbraille jb, String sortieBraille) {
		this.j = jb;
		//On enlève les accents de la chaîne car ils sont mals encodés
		this.sortieBraille = unAccent(sortieBraille);
	}
	
	//Affichage de la chaîne sur l'afficheur braille
	public void read() {
		if(MapScene.brailleOnline)
			j.display(sortieBraille);
	}
	
	//Fonction permettant de retirer les accents d'une chaîne de caractère
	public static String unAccent(String s) {
		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}
}