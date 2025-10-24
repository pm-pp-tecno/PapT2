
package publicadores;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Clase Java para estadoPrestamo.
 * 
 * &lt;p&gt;El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="estadoPrestamo"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="PENDIENTE"/&amp;gt;
 *     &amp;lt;enumeration value="EN_CURSO"/&amp;gt;
 *     &amp;lt;enumeration value="DEVUELTO"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "estadoPrestamo")
@XmlEnum
public enum EstadoPrestamo {

    PENDIENTE,
    EN_CURSO,
    DEVUELTO;

    public String value() {
        return name();
    }

    public static EstadoPrestamo fromValue(String v) {
        return valueOf(v);
    }

}
