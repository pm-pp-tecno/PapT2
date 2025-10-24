
package publicadores;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Clase Java para dtLibro complex type.
 * 
 * &lt;p&gt;El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="dtLibro"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://publicadores.biblioteca.lectoresuy/}dtMaterial"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="cantidadPaginas" type="{http://www.w3.org/2001/XMLSchema}int"/&amp;gt;
 *         &amp;lt;element name="titulo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtLibro", propOrder = {
    "cantidadPaginas",
    "titulo"
})
public class DtLibro
    extends DtMaterial
{

    protected int cantidadPaginas;
    protected String titulo;

    /**
     * Obtiene el valor de la propiedad cantidadPaginas.
     * 
     */
    public int getCantidadPaginas() {
        return cantidadPaginas;
    }

    /**
     * Define el valor de la propiedad cantidadPaginas.
     * 
     */
    public void setCantidadPaginas(int value) {
        this.cantidadPaginas = value;
    }

    /**
     * Obtiene el valor de la propiedad titulo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Define el valor de la propiedad titulo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitulo(String value) {
        this.titulo = value;
    }

}
