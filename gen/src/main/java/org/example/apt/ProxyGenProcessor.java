package org.example.apt;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Set;

/**
 * @author Zero
 *         Created on 2016/12/1.
 */
public class ProxyGenProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("----ProxyGenProcessor----");
        //获取编译参数,不如在javac 中添加了 -AenableProxy=yes,则可以通过processingEnv.getOptions().get("enableProxy")获得
        System.out.println("自定义编译参数" + processingEnv.getOptions());

        Filer filer = processingEnv.getFiler();
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ProxyGen.class);
        elements.forEach(e -> {
            ElementKind kind = e.getKind();
            if (kind.isClass()) {
                TypeElement typeElement = (TypeElement) e;
                System.out.println(typeElement.getQualifiedName());//完整类名
                try {
                    String className = e.getSimpleName() + "Proxy";
                    //生成在源码中
                    FileObject fileObject = filer.createResource(StandardLocation.SOURCE_OUTPUT, "org.example.genproxy", className + ".java");
                    Writer writer = new OutputStreamWriter(fileObject.openOutputStream(), StandardCharsets.UTF_8);
                    writer.write("package org.example.genproxy;\n\n");

                    writer.write("//这个文件是在编译时生成的\n");
                    writer.write("public class " + className + " extends " + e.toString() + "{\n");
                    writer.write("//" + processingEnv.getOptions() + "\n");
                    writer.write("//...\n");
                    writer.write("  public void echo() {\n");
                    writer.write("      System.out.println(\"--------------before--------------\");\n");
                    writer.write("      super.echo();\n");
                    writer.write("      System.out.println(\"--------------after--------------\");\n");
                    writer.write("  }\n");
                    writer.write("}");
                    writer.close();
                } catch (IOException e1) {
                    String msg = "Could not generate element for " + e + ": " + e1.getMessage();
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, e);
                }
            }
        });
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(ProxyGen.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
