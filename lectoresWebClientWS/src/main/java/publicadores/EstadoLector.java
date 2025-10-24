
package publicadores;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Clase Java para estadoLector.
 * 
 * &lt;p&gt;El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="estadoLector"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="ACTIVO"/&amp;gt;
 *     &amp;lt;enumeration value="SUSPENDIDO"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "estadoLector")
@XmlEnum
public enum EstadoLector {

    ACTIVO,
    SUSPENDIDO;

    public String value() {
        return name();
    }

    public static EstadoLector fromValue(String v) {
        return valueOf(v);
    }

}
