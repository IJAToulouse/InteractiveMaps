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
	//Cha�ne de caract�re qui sera affich�e sur l'afficheur braille
	private String sortieBraille;

	public Braille(jbraille jb, String sortieBraille) {
		this.j = jb;
		//On enl�ve les accents de la cha�ne car ils sont mals encod�s
		this.sortieBraille = unAccent(sortieBraille);
	}
	
	//Affichage de la cha�ne sur l'afficheur braille
	public void read() {
		if(MapScene.brailleOnline)
			j.display(sortieBraille);
	}
	
	//Fonction permettant de retirer les accents d'une cha�ne de caract�re
	public static String unAccent(String s) {
		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}
}