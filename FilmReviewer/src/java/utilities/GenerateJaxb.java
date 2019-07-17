/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.api.ErrorListener;
import com.sun.tools.xjc.api.S2JJAXBModel;
import com.sun.tools.xjc.api.SchemaCompiler;
import com.sun.tools.xjc.api.XJC;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

/**
 *
 * @author ASUS
 */
public class GenerateJaxb {
    public static void main(String[] args) {
        try {
            String output = "src/java";
            SchemaCompiler sc = XJC.createSchemaCompiler();
            sc.setErrorListener(new ErrorListener() {
                @Override
                public void error(SAXParseException saxpe) {
                    System.out.println("error " + saxpe.getMessage());
                }

                @Override
                public void fatalError(SAXParseException saxpe) {
                    System.out.println("fatal " + saxpe.getMessage());
                }

                @Override
                public void warning(SAXParseException saxpe) {
                    System.out.println("warning " + saxpe.getMessage());
                }

                @Override
                public void info(SAXParseException saxpe) {
                    System.out.println("info " + saxpe.getMessage());
                }
            });
            sc.forcePackageName("jaxb.totalpage");
//            File schema = new File("D:\\Tin\\fpt3\\prx\\xml_project\\FilmReviewer\\web\\xsd\\crawlsource.xsd");
            File schema = new File("D:\\Tin\\fpt3\\prx\\xml_project\\FilmReviewer\\web\\WEB-INF\\xsd\\totalPage.xsd");
            InputSource is = new InputSource(schema.toURI().toString());
            sc.parseSchema(is);
            S2JJAXBModel model = sc.bind();
            JCodeModel code = model.generateCode(null, null);
            code.build(new File(output));
            System.out.println("Finished");
        } catch (IOException ex) {
            
        } 
    }
}
