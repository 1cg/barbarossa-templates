package bb.tokenizer;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;


public class HTemplateGen {
    private static class fileTypeChecker implements BiPredicate {
        public boolean test(Object path, Object attr){
            String regexStr = ".*\\.bb\\..*";
            return path.toString().matches(regexStr);
        }
    }

    private static class Name {
        String inputDir;
        String outputDir;
        String fileName;
        String relativePath;
        String javaWholePath;


        Name(String inputDir, String outputDir, Path bbFile) {

            this.inputDir = inputDir;
            this.outputDir = outputDir;

            fileName = bbFile.toFile().getName().split("\\.bb\\.")[0];
//            String regexString = ".*" + fileName;
//            Pattern pat = Pattern.compile(regexString);
//            Matcher mat = pat.matcher(bbFile.toString());
//            mat.find();
//            String withoutFileType = mat.group(0);
            String withoutFileType = bbFile.toString().split(fileName + "\\.bb\\.")[0];
            //@TODO: \bb\hgen is temporary
            relativePath = "bb\\hgen" + withoutFileType.substring(inputDir.length(), withoutFileType.length() - 1);
            javaWholePath = outputDir  + "\\" + relativePath + "\\" + fileName + ".java";

        }

    }

//    //@TODO: \bb\hgen is temporary
//    private static String getNewFileName(String inputDir, String outputDir, String bbFileLoc) {
//        String regexString = "(.*\\.bb\\.)";
//        Pattern pat = Pattern.compile(regexString);
//        Matcher mat = pat.matcher(bbFileLoc);
//        mat.find();
//        String withoutFileType = mat.group(0);
//        String extra = withoutFileType.substring(inputDir.length(), withoutFileType.length() - 4);
//        return outputDir + "\\bb\\hgen" + extra + ".java";
//    }


    private static String getJavaContent(Name name, String bbContent) {
        StringBuilder importHeader = new StringBuilder();
        HTokenizer tokenizer = new HTokenizer();
        List<Token> tokens = tokenizer.tokenize(bbContent);
        importHeader.append("package " + name.relativePath.replaceAll("\\\\", ".") + ";\n\n");
        importHeader.append("import java.io.IOException;\n\n");

        Iterator<Token> tokenIterator = tokens.iterator();


        StringBuilder classContent = makeClassContent(name.fileName, importHeader, tokenIterator);


        return importHeader.append(classContent).toString();
    }

    //given a trimmed string of variables,
    // returns a list with a string list per variable with the type and variable name (when both are given)
    // or just the name if both aren't given
    private static String[][] splitParamsList(String params) {
        params = params.replaceAll(" ,", ",").replace(", ", ",");
        String[] parameters = params.split(",");
        String[][] paramsList = new String[parameters.length][2];
        for (int i = 0; i < parameters.length; i++) {
            paramsList[i] = parameters[i].split(" ", 2);
        }
        return paramsList;
    }

    //given a list of 2 element String lists (0th elem is type and 1st elem is value), returns the string form
    //ex. [[String, str],[int,5]] returns "String str, int 5"
    private static String makeParamsString(String[][] paramsList) {
        String params = "" + paramsList[0][0] + " " + paramsList[0][1];
        for (int i = 1; i < paramsList.length; i++) {
            params += ", " + paramsList[i][0] + " " + paramsList[i][1];
        }
        return params;
    }

    private static StringBuilder makeClassContent(String name, StringBuilder importHeader, Iterator<Token> tokenIterator) {
        return makeClassContent(name, importHeader, tokenIterator, true, null);
    }

    private static StringBuilder makeClassContent(String name, StringBuilder importHeader, Iterator<Token> tokenIterator, boolean outermost, String [][] paramsList) {
        StringBuilder classHeader = new StringBuilder();
        StringBuilder innerClass = new StringBuilder();
        StringBuilder jspContent = new StringBuilder();
        String superClass = null;
        String params = null;

        while (tokenIterator.hasNext()) {
            Token token = tokenIterator.next();
            switch (token.getType()) {
                case STRING_CONTENT:
                    jspContent.append("            buffer.append(\"" + token.getContent().replaceAll("\"", "\\\\\"").replaceAll("\r\n", "\\\\n") + "\");\n");
                    break;
                case STATEMENT:
                    jspContent.append("            " + token.getContent() + "\n");
                    break;
                case EXPRESSION:
                    jspContent.append("            buffer.append(toS(" + token.getContent() + "));\n");
                    break;
                case COMMENT:
                    break;
                case DIRECTIVE:
                    if (token.getContent().matches("import.*")) {
                        //@TODO: deal with import not having a space after it
                        importHeader.append(token.getContent() + ";\n");
                    } else if (token.getContent().matches("extends.*")) {
                        //@TODO: deal with extends not having a space after it
                        if (superClass == null) {
                            superClass = token.getContent();
                        } else {
                            throw new RuntimeException("Cannot extend 2 classes:" + superClass + " and " + token.getContent());
                        }
                    } else if (token.getContent().matches("section.*")) {
                        String[] content = token.getContent().substring(7).trim().split("\\(", 2);
                        String innerName = content[0];
                        String innerVars = content[1].replace(" {", "{").substring(0, content[1].length() - 2);
                        innerVars = innerVars.substring(0, innerVars.length() - 1);
                        String[][] innerVarsList = splitParamsList(innerVars);
                        //@TODO: use the params list
                        innerClass.append(makeClassContent(innerName, importHeader, tokenIterator, false, innerVarsList));
                        jspContent.append("\n" + innerName + "." + "renderInto(buffer");
                        for (int i = 0; i <innerVarsList.length; i++) {
                            jspContent.append(", " + innerVarsList[i][1]);
                        }
                        jspContent.append(");\n");
                    } else if (token.getContent().equals("end section")) {
                        break;
                    } else if (token.getContent().matches("params.*")) {
                        if (paramsList == null) {
                            String content = token.getContent();
                            params = content.substring(7, content.length() - 1).trim();
                            paramsList = splitParamsList(params);
                        } else {
                            throw new RuntimeException("Cannot have 2 params directives: on line" + token.getLine());
                        }
                    } else if (token.getContent().matches("include.*")) {
                        String content = token.getContent().substring(8);
                        String[] parts = content.split("\\(", 2);

                        if (parts.length == 1 || (parts.length == 2 && parts[1].trim().equals(")"))) {
                            jspContent.append("            " + parts[0] + ".renderInto(buffer);\n");
                        } else {
                            jspContent.append("            " + parts[0] + ".renderInto(buffer, ");
                            jspContent.append(parts[1] + ";\n");
                        }
                    } else {
                        throw new RuntimeException("Unsupported Directive on line" + token.getLine() + ":" + token.getContent());
                    }
                    break;
            }
        }
        if (outermost) {
            if (superClass == null) {
                classHeader.append("\npublic class " + name + " {\n");
            } else {
                classHeader.append("\npublic class " + name + " " + superClass + " {\n");
            }
        } else {
            if (superClass == null) {
                classHeader.append("\npublic static class " + name + " {\n");
            } else {
                classHeader.append("\npublic static class " + name + " " + superClass + " {\n");
            }
        }


        classHeader.append(innerClass);

        if (paramsList == null) {
            classHeader.append("\n" +
                    "    public static String render() {\n" +
                    "        StringBuilder sb = new StringBuilder();\n" +
                    "        renderInto(sb);\n" +
                    "        return sb.toString();\n" +
                    "    }\n\n");
            classHeader.append("    public static void renderInto(Appendable buffer) {\n" +
                    "        try {\n");
        } else {
            //@TODO: findParamTypes(paramsList);
            if (params == null) {
                params = makeParamsString(paramsList);
            }
            classHeader.append("\n" +
                    "    public static String render(" + params + ") {\n" +
                    "        StringBuilder sb = new StringBuilder();\n" +
                    "        renderInto(sb");
            for (String[] p : paramsList) {
                classHeader.append(", " + p[1]);
            }
            classHeader.append(");\n" +
                    "        return sb.toString();\n" +
                    "    }\n\n");

            classHeader.append("    public static void renderInto(Appendable buffer, " + params + ") {\n" +
                    "        try {\n");
        }


        jspContent.append("        } catch (IOException e) {\n" +
                "            throw new RuntimeException(e);\n" +
                "        }\n" +
                "    }\n");

        jspContent.append("\n" +
                "    private static String toS(Object o) {\n" +
                "        return o == null ? \"\" : o.toString();\n" +
                "    }\n" +
                "}");

        return classHeader.append(jspContent);
    }

    public static void main(String[] args) {
        String inputDir = args[0];
        String outputDir = args[1];

        Path root = Paths.get(inputDir);

        try {//@TODO: there is a max depth, which is problematic, actual sol can't be hacky like this...
            Object[] filesToConvert = Files.find(root, Integer.MAX_VALUE,  new fileTypeChecker()).toArray();
            for (Object p : filesToConvert){
                Name name = new Name(inputDir, outputDir, (Path) p);

                File writeTo = new File(name.javaWholePath);
                if (!writeTo.getParentFile().exists()) {
                    writeTo.getParentFile().mkdirs();
                }
                if (writeTo.createNewFile()){
                    System.out.println("File is created!");
                }else{
                    System.out.println("File already exists.");
                }

                //String content = new String(Files.readAllBytes(Paths.get(p.toString())));
                String content = getJavaContent(name, new String(Files.readAllBytes(Paths.get(p.toString()))));
                FileWriter fw = null;
                BufferedWriter bw = null;

                try {
                    fw = new FileWriter(writeTo);
                    bw = new BufferedWriter(fw);
                    bw.write(content);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (bw != null) {
                            bw.close();
                        }
                        if (fw != null) {
                            fw.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("The given parameter is not a valid directory.");
        }



        //TODO: scan input dir for all files with .bb.* ending and generate
        // a corresponding java file to the given output dir, preserving the package
        // relative to the input dir root, with a .render() static function that
        // renders the template
    }
}
