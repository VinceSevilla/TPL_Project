import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LexicalAnalysisAction implements ActionListener {
    private final Compiler compiler;

    public LexicalAnalysisAction(Compiler compiler) {
        this.compiler = compiler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String code = compiler.getCodeTextArea().getText().trim();
        StringBuilder result = new StringBuilder();
        boolean hasErrors = false;

        String dataTypeRegex = "\\b(int|double|boolean|String|char)\\b";
        String variableNameRegex = "\\b[a-zA-Z_][a-zA-Z0-9_]*\\b";
        String intValueRegex = "\\b[0-9]+\\b";
        String doubleValueRegex = "\\b[0-9]+(\\.[0-9]+)?\\b";
        String booleanValueRegex = "\\b(true|false)\\b";
        String charValueRegex = "'[^']'"; 
        String stringValueRegex = "\".*?\""; 
        String validDeclarationRegex = dataTypeRegex + "\\s+" + variableNameRegex + "(\\s*=\\s*(" +
                intValueRegex + "|" + doubleValueRegex + "|" + booleanValueRegex + "|" +
                charValueRegex + "|" + stringValueRegex + "))?\\s*;?";

        String[] lines = code.split("\\n");

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue; 

            if (line.matches(validDeclarationRegex)) {
                result.append("Valid declaration: ").append(line).append("\n");
            } else {
                result.append("Error: Invalid declaration or syntax: ").append(line).append("\n");
                hasErrors = true;
            }
        }

        if (hasErrors) {
            compiler.getResultTextArea().setText(result.toString());
            compiler.getSyntaxAnalysisButton().setEnabled(false);
            compiler.getLexicalAnalysisButton().setEnabled(false);
        } else {
            result.append("Lexical Analysis Successful!\n");
            compiler.getResultTextArea().setText(result.toString());
            compiler.getSyntaxAnalysisButton().setEnabled(true);
            compiler.getLexicalAnalysisButton().setEnabled(false);
        }

        compiler.getSemanticAnalysisButton().setEnabled(false);
    }
}