package code.parsing.config;

/**
* @author Alexis Paoleschi
* @mail alexis.paoleschi@gmail.com
*/

//Classe qui va stocker toutes les caract�ristiques qui nous int�resse d'un �l�ment de type "rect"
//pr�sent dans le fichier SVG. Ce sont les �l�ments que nous afficherons dans notre sc�ne
public class Rect {
	//Caract�ristiques d'un �l�ment
	private String id;
	private Double width;
	private Double height;
	private Double x;
	private Double y;
	
	public Rect() {
		super();
		this.id = new String("");
		this.width = new Double(0);
		this.height = new Double(0);
		this.x = new Double(0);
		this.y = new Double(0);
	}
	
	public Rect(String id, Double width, Double height, Double x, Double y) {
		super();
		this.id = id;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}

	//Les diff�rents accesseurs nous permettront de r�cup�rer les donn�es
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Rect [id=" + id + ", width=" + width + ", height=" + height
				+ ", x=" + x + ", y=" + y + "]";
	}
}
