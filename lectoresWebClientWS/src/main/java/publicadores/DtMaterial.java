
package publicadores;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * &lt;p&gt;Clase Java para dtMaterial complex type.
 * 
 * &lt;p&gt;El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="dtMaterial"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="cantidadPrestamosPendientes" type="{http://www.w3.org/2001/XMLSchema}int"/&amp;gt;
 *         &amp;lt;element name="fechaIngreso" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtMaterial", propOrder = {
    "cantidadPrestamosPendientes",
    "fechaIngreso",
    "id"
})
public abstract class DtMaterial {

    protected int cantidadPrestamosPendientes;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaIngreso;
    protected Long id;

    /**
     * Obtiene el valor de la propiedad cantidadPrestamosPendientes.
     * 
     */
    public int getCantidadPrestamosPendientes() {
        return cantidadPrestamosPendientes;
    }

    /**
     * Define el valor de la propiedad cantidadPrestamosPendientes.
     * 
     */
    public void setCantidadPrestamosPendientes(int value) {
        this.cantidadPrestamosPendientes = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaIngreso.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaIngreso() {
        return fechaIngreso;
    }

    /**
     * Define el valor de la propiedad fechaIngreso.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaIngreso(XMLGregorianCalendar value) {
        this.fechaIngreso = value;
    }

    /**
     * Obtiene el valor de la propiedad id.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

}
