package code.parsing.config;

/**
* @author Alexis Paoleschi
* @mail alexis.paoleschi@gmail.com
*/

//Classe qui va stocker toutes les caractéristiques qui nous intéresse d'un élément de type "rect"
//présent dans le fichier SVG. Ce sont les éléments que nous afficherons dans notre scène
public class Rect {
	//Caractéristiques d'un élément
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

	//Les différents accesseurs nous permettront de récupérer les données
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
