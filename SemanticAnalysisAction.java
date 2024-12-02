import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

public class SemanticAnalysisAction implements ActionListener {
    private final Compiler compiler;

    public SemanticAnalysisAction(Compiler compiler) {
        this.compiler = compiler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String code = compiler.getCodeTextArea().getText();
        StringBuilder result = new StringBuilder();
        HashSet<String> declaredVariables = new HashSet<>();
        boolean hasErrors = false;

        String intDeclarationRegex = "int\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*-?\\d+;";
        String stringDeclarationRegex = "String\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*\"[^\"]+\";";
        String doubleDeclarationRegex = "double\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*-?\\d*\\.\\d+;";
        String charDeclarationRegex = "char\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*'.'\\s*;";
        String booleanDeclarationRegex = "boolean\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*(true|false);";

        for (String line : code.split("\n")) {
            line = line.trim();

            if (line.isEmpty()) continue;

            if (line.matches(intDeclarationRegex) || line.matches(stringDeclarationRegex) || 
                line.matches(doubleDeclarationRegex) || line.matches(charDeclarationRegex) || 
                line.matches(booleanDeclarationRegex)) {
                
                String[] parts = line.split("\\s+");
                String varName = parts[1].split("=")[0].trim();

                if (declaredVariables.contains(varName)) {
                    result.append("Semantic Error: Variable '").append(varName).append("' redeclared!\n");
                    hasErrors = true;
                } else {
                    declaredVariables.add(varName);
                }

                if (!validateAssignment(line)) {
                    result.append("Semantic Error: Invalid assignment in line - ").append(line).append("\n");
                    hasErrors = true;
                }
            } else {
                result.append("Semantic Error: Invalid declaration or assignment in line - ").append(line).append("\n");
                hasErrors = true;
            }
        }

        if (hasErrors) {
            compiler.getResultTextArea().setText(result.toString());
        } else {
            compiler.getResultTextArea().setText(result.append("Semantic Analysis Successful!").toString());
        }
    }

    private boolean validateAssignment(String line) {
        if (line.matches("int\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*-?\\d+;")) {
            return true;
        } else if (line.matches("String\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*\"[^\"]+\";")) {
            return true;
        } else if (line.matches("double\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*-?\\d*\\.\\d+;")) {
            return true;
        } else if (line.matches("char\\s+[a-zA-Z_][a-zA0-9_]*\\s*=\\s*'.'\\s*;")) {
            return true;
        } else if (line.matches("boolean\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*(true|false);")) {
            return true;
        }
        return false;
    }
}
