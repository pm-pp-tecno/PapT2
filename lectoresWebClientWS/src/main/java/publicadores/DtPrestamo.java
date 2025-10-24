
package publicadores;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * &lt;p&gt;Clase Java para dtPrestamo complex type.
 * 
 * &lt;p&gt;El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="dtPrestamo"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="bibliotecario" type="{http://publicadores.biblioteca.lectoresuy/}dtBibliotecario" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="estado" type="{http://publicadores.biblioteca.lectoresuy/}estadoPrestamo" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="fechaDevolucionEstimada" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="fechaSolicitud" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="lector" type="{http://publicadores.biblioteca.lectoresuy/}dtLector" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="material" type="{http://publicadores.biblioteca.lectoresuy/}dtMaterial" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtPrestamo", propOrder = {
    "bibliotecario",
    "estado",
    "fechaDevolucionEstimada",
    "fechaSolicitud",
    "id",
    "lector",
    "material"
})
public class DtPrestamo {

    protected DtBibliotecario bibliotecario;
    @XmlSchemaType(name = "string")
    protected EstadoPrestamo estado;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaDevolucionEstimada;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaSolicitud;
    protected Long id;
    protected DtLector lector;
    protected DtMaterial material;

    /**
     * Obtiene el valor de la propiedad bibliotecario.
     * 
     * @return
     *     possible object is
     *     {@link DtBibliotecario }
     *     
     */
    public DtBibliotecario getBibliotecario() {
        return bibliotecario;
    }

    /**
     * Define el valor de la propiedad bibliotecario.
     * 
     * @param value
     *     allowed object is
     *     {@link DtBibliotecario }
     *     
     */
    public void setBibliotecario(DtBibliotecario value) {
        this.bibliotecario = value;
    }

    /**
     * Obtiene el valor de la propiedad estado.
     * 
     * @return
     *     possible object is
     *     {@link EstadoPrestamo }
     *     
     */
    public EstadoPrestamo getEstado() {
        return estado;
    }

    /**
     * Define el valor de la propiedad estado.
     * 
     * @param value
     *     allowed object is
     *     {@link EstadoPrestamo }
     *     
     */
    public void setEstado(EstadoPrestamo value) {
        this.estado = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaDevolucionEstimada.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaDevolucionEstimada() {
        return fechaDevolucionEstimada;
    }

    /**
     * Define el valor de la propiedad fechaDevolucionEstimada.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaDevolucionEstimada(XMLGregorianCalendar value) {
        this.fechaDevolucionEstimada = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaSolicitud.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaSolicitud() {
        return fechaSolicitud;
    }

    /**
     * Define el valor de la propiedad fechaSolicitud.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaSolicitud(XMLGregorianCalendar value) {
        this.fechaSolicitud = value;
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

    /**
     * Obtiene el valor de la propiedad lector.
     * 
     * @return
     *     possible object is
     *     {@link DtLector }
     *     
     */
    public DtLector getLector() {
        return lector;
    }

    /**
     * Define el valor de la propiedad lector.
     * 
     * @param value
     *     allowed object is
     *     {@link DtLector }
     *     
     */
    public void setLector(DtLector value) {
        this.lector = value;
    }

    /**
     * Obtiene el valor de la propiedad material.
     * 
     * @return
     *     possible object is
     *     {@link DtMaterial }
     *     
     */
    public DtMaterial getMaterial() {
        return material;
    }

    /**
     * Define el valor de la propiedad material.
     * 
     * @param value
     *     allowed object is
     *     {@link DtMaterial }
     *     
     */
    public void setMaterial(DtMaterial value) {
        this.material = value;
    }

}
