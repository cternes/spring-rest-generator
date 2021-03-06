package de.slackspace.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;

public class Main {

    private static final String TEMPLATE_FTLH = "template.ftlh";

    public static void main(String[] args) throws Exception {
        Configuration configuration = SetupBasicConfiguration();

        // read from input stream
        Map<String, String> values = requestValuesFromUser();

        // write file
        generateFile(configuration, values);
    }

    private static Map<String, String> requestValuesFromUser() {
        Scanner scanner = new Scanner(System.in);
        String endpoint = readFromConsole(scanner, "REST Endpoint");
        String targetClazz = readFromConsole(scanner, "REST class (fully qualified)");
        String modelClazz = readFromConsole(scanner, "Domain class (fully qualified)");
        String repoClazz = readFromConsole(scanner, "Repository class (fully qualified)");

        Map<String, String> values = new ValueHolder()
                .addRestEndpoint(endpoint)
                .addTargetClazz(targetClazz)
                .addModelClazz(modelClazz)
                .addRepoClazz(repoClazz)
                .build();

        return values;
    }

    private static String readFromConsole(Scanner scanner, String hint) {
        System.out.println(hint + ":");
        return scanner.nextLine();
    }

    private static void generateFile(Configuration configuration, Map<String, String> values)
            throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
        Template temp = configuration.getTemplate(TEMPLATE_FTLH);

        File file = determineTargetFile(values);

        Writer out = new FileWriter(file);
        temp.process(values, out);
    }

    private static File determineTargetFile(Map<String, String> values) {
        String targetClazz = values.get("fullyQualifiedTargetClazz");
        String targetFilePath = targetClazz.replace('.', '/').concat(".java");
        File file = Paths.get("src/main/java", targetFilePath).toFile();
        file.getParentFile().mkdirs();
        return file;
    }

    private static Configuration SetupBasicConfiguration() throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new File("."));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(true);

        if(!Files.exists(Paths.get(TEMPLATE_FTLH))) {
            System.out.println("Could not find template file '" + TEMPLATE_FTLH + "'");
            System.exit(-1);
        }

        return cfg;
    }
}
