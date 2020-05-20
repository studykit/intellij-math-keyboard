package net.mlcoder.unimath;

import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.psi.PsiFile;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class UnimathContextType extends TemplateContextType {
    private static final List<String> fileSuffix = Arrays.asList(
      ".md", ".markdown", ".adoc", "asciidoc", ".asc", ".txt",
        ".tex", ".bib", ".ipynb", ".rmd", ".math"
    );
    public UnimathContextType() {
        super("UNIMATH", "UniMath");
    }

    @Override
    public boolean isInContext(@NotNull PsiFile file, int offset) {
        String name = file.getName();
        if (StringUtils.isNotEmpty(name)) {
            return fileSuffix.stream().anyMatch(name::endsWith) && file.isWritable();
        }

        return file.isWritable();
    }
}
