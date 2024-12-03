import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SyntaxAnalysisAction implements ActionListener {
    private final Compiler compiler;

    public SyntaxAnalysisAction(Compiler compiler) {
        this.compiler = compiler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String code = compiler.getCodeTextArea().getText().trim();
        StringBuilder result = new StringBuilder();
        boolean hasErrors = false;

        String syntaxRegex = "\\b(int|double|boolean|String|char)\\b\\s+[a-zA-Z_][a-zA-Z0-9_]*(\\s*=\\s*(\".*\"|'[^']'|true|false|[0-9]+(\\.[0-9]+)?))?;";

        String[] lines = code.split("\\n");
        for (String line : lines) {
            line = line.trim();
            if (!line.isEmpty() && !line.matches(syntaxRegex)) {
                result.append("Syntax Error: Invalid statement - ").append(line).append("\n");
                hasErrors = true;
            }
        }

        if (hasErrors) {
            result.append("Syntax Analysis Failed!\n");
            compiler.getResultTextArea().setText(result.toString());
            compiler.getSemanticAnalysisButton().setEnabled(false);
            compiler.getSyntaxAnalysisButton().setEnabled(false);
        } else {
            result.append("Syntax Analysis Successful!\n");
            compiler.getResultTextArea().setText(result.toString());
            compiler.getSemanticAnalysisButton().setEnabled(true);
            compiler.getSyntaxAnalysisButton().setEnabled(false);
        }
    }
}