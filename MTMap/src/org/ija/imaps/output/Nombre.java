package org.ija.imaps.output;

/**
 * @author JHelp
 * @version 1.0
 */

/**
 * Classe permetant de transformer un entier en son écriture en toutes lettres <BR>
 * Exemple : 123 devient cent vingt trois
 */
public class Nombre
{
	/**
	 * Représentaion en lettre de 0
	 */
	public static final String ZERO="zéro";
	/**
	 * Représentaion en lettre de 1
	 */
	public static final String UN="un";
	/**
	 * Représentaion en lettre de 2
	 */
	public static final String DEUX="deux";
	/**
	 * Représentaion en lettre de 3
	 */
	public static final String TROIS="trois";
	/**
	 * Représentaion en lettre de 4
	 */
	public static final String QUATRE="quatre";
	/**
	 * Représentaion en lettre de 5
	 */
	public static final String CINQ="cinq";
	/**
	 * Représentaion en lettre de 6
	 */
	public static final String SIX="six";
	/**
	 * Représentaion en lettre de 7
	 */
	public static final String SEPT="sept";
	/**
	 * Représentaion en lettre de 8
	 */
	public static final String HUIT="huit";
	/**
	 * Représentaion en lettre de 9
	 */
	public static final String NEUF="neuf";
	/**
	 * Représentaion en lettre de 10
	 */
	public static final String DIX="dix";
	/**
	 * Représentaion en lettre de 11
	 */
	public static final String ONZE="onze";
	/**
	 * Représentaion en lettre de 12
	 */
	public static final String DOUZE="douze";
	/**
	 * Représentaion en lettre de 13
	 */
	public static final String TREIZE="treize";
	/**
	 * Représentaion en lettre de 14
	 */
	public static final String QUATORZE="quatorze";
	/**
	 * Représentaion en lettre de 15
	 */
	public static final String QUINZE="quinze";
	/**
	 * Représentaion en lettre de 16
	 */
	public static final String SEIZE="seize";
	/**
	 * Représentaion en lettre de 20
	 */
	public static final String VINGT="vingt";
	/**
	 * Représentaion en lettre de 30
	 */
	public static final String TRENTE="trente";
	/**
	 * Représentaion en lettre de 40
	 */
	public static final String QUARANTE="quarante";
	/**
	 * Représentaion en lettre de 50
	 */
	public static final String CINQUANTE="cinquante";
	/**
	 * Représentaion en lettre de 60
	 */
	public static final String SOIXANTE="soixante";
	/**
	 * Représentaion en lettre de 100
	 */
	public static final String CENT="cent";
	/**
	 * Représentaion en lettre de 1000
	 */
	public static final String MILLE="mille";
	/**
	 * Représentaion en lettre de 1000000
	 */
	public static final String MILLION="million";
	/**
	 * Représentaion en lettre de 1000000000
	 */
	public static final String MILLIARD="milliard";
	/**
	 * Représentaion en lettre de -
	 */
	public static final String MOINS="moins";

	//Nom des différents types de paquet de nombre
	private static final String[] tab={"",MILLE,MILLION,MILLIARD,MILLE+" "+MILLIARD,
			MILLION+" de "+MILLIARD,MILLIARD+" de "+MILLIARD};

	/**
	 * Renvoie la représentation en lettre d'un chiffre, c'est à dire d'un nombre ente 0 et 9
	 */
	public static String getChiffre(int l)
	{
		if((l<0)||(l>9))
			throw new IllegalArgumentException("Un chiffre est entre 0 et 9, donc "+l+" est interdit");
		switch(l)
		{
			case 0 :
				return ZERO;
			case 1 :
				return UN;
			case 2 :
				return DEUX;
			case 3 :
				return TROIS;
			case 4 :
				return QUATRE;
			case 5 :
				return CINQ;
			case 6 :
				return SIX;
			case 7 :
				return SEPT;
			case 8 :
				return HUIT;
			case 9 :
				return NEUF;
		}
		return null;
	}

	//Retourne la représentation en lettre d'un paquet. Un paquet est formé de tois chiffres, comme 123, 012, 001, 100, 101,...
	private static String paquet(int p)
	{
		//On initialise la réponse
		String reponse="";
		//Si on a un chiffre des centaines
		if(p>100)
		{
			//Si la valeur est >199 alors, on va mettre devant le chiffre des centaine
			if(p/100>1)
				reponse=getChiffre(p/100)+" ";
			//C'est une centaine, donc on ajoute ensuite "cent"
			reponse += CENT+" ";
			//On récupère ce qui n'est pas la centaine
			p=p%100;
		}
		//c chiffre des dizaines
		//u chiffre des unitées
		int c=p/10;
		int u=p%10;
		switch(c)
		{
			//Si la dizaine est nulle, alors le nombre est un chiffre
			case 0 :
				if(u!=0)
					reponse += getChiffre(u);
				break;
			case 1 :
				switch(u)
				{
					case 0 :
						reponse += DIX;
						break;
					case 1 :
						reponse += ONZE;
						break;
					case 2 :
						reponse += DOUZE;
						break;
					case 3 :
						reponse += TREIZE;
						break;
					case 4 :
						reponse += QUATORZE;
						break;
					case 5 :
						reponse += QUINZE;
						break;
					case 6 :
						reponse += SEIZE;
						break;
					default :
						reponse += DIX+" "+getChiffre(u);
				}
				break;
			case 2 :
				reponse += VINGT;
				if(u==1)
					reponse += " et";
				if(u>0)
					reponse += " "+getChiffre(u);
				break;
			case 3 :
				reponse += TRENTE;
				if(u==1)
					reponse += " et";
				if(u>0)
					reponse += " "+getChiffre(u);
				break;
			case 4 :
				reponse += QUARANTE;
				if(u==1)
					reponse += " et";
				if(u>0)
					reponse += " "+getChiffre(u);
				break;
			case 5 :
				reponse += CINQUANTE;
				if(u==1)
					reponse += " et";
				if(u>0)
					reponse += " "+getChiffre(u);
				break;
			case 6 :
				reponse += SOIXANTE;
				if(u==1)
					reponse += " et";
				if(u>0)
					reponse += " "+getChiffre(u);
				break;
			case 7 :
				reponse += SOIXANTE+" ";
				if(u==1)
					reponse += " et";
				switch(u)
				{
					case 0 :
						reponse += DIX;
						break;
					case 1 :
						reponse += ONZE;
						break;
					case 2 :
						reponse += DOUZE;
						break;
					case 3 :
						reponse += TREIZE;
						break;
					case 4 :
						reponse += QUATORZE;
						break;
					case 5 :
						reponse += QUINZE;
						break;
					case 6 :
						reponse += SEIZE;
						break;
					default :
						reponse += DIX+" "+getChiffre(u);
				}
				break;
			case 8 :
				reponse += QUATRE+" "+VINGT;
				if(u>0)
					reponse += " "+getChiffre(u);
				break;
			case 9 :
				reponse += QUATRE+" "+VINGT+" ";
				switch(u)
				{
					case 0 :
						reponse += DIX;
						break;
					case 1 :
						reponse += ONZE;
						break;
					case 2 :
						reponse += DOUZE;
						break;
					case 3 :
						reponse += TREIZE;
						break;
					case 4 :
						reponse += QUATORZE;
						break;
					case 5 :
						reponse += QUINZE;
						break;
					case 6 :
						reponse += SEIZE;
						break;
					default :
						reponse += DIX+" "+getChiffre(u);
				}
				break;
		}
		//On renvoie la réponse, à laquelle on retire les éventuels espaces surperflus
		return reponse.trim();
	}

	/**
	 * Renvoie le nombre en lettre, <BR>
	 * ex: 1234567890 devient : un milliard deux cent trente quatre million cinq cent soixante sept mille huit cent quatre vingt dix
	 */
	public static String getLettre(long l)
	{
		//Cas zéro
		if(l==0L)
			return ZERO;
		String signe="";
		//Cas négatif
		if(l<0L)
		{
			//On prend la valeur absolue
			l=-l;
			//On ajoutera moins devant
			signe=MOINS+" ";
		}
		//Initialisation de la réponse
		String reponse="";
		//Rang du paquet actuel, on va parcourir le nombre de gauche à droite, le premier paquet de 123456 sera donc : 456
		int rang=0;
		while(l>0L)
		{
			//on ajoute le paquet devant la réponse
			reponse=paquet((int)(l%1000L))+" "+tab[rang]+" "+reponse;
			//on passe au paquet suivant
			l=l/1000L;
			rang++;
		}
		//on ajoute le signe éventuel
		reponse=signe+reponse;
		//On renvoie la réponse, à laquelle on retire les éventuels espaces surperflus
		return reponse.trim();
	}
}