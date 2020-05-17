package net.mlcoder.unicode;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.PopupChooserBuilder;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.components.FixedColumnsModel;
import com.intellij.ui.table.JBTable;
import net.mlcoder.unicode.category.MathSymbol;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.stream.Collectors;

public class PopupAction extends AnAction {
    private static final Logger logger = Logger.getInstance(PopupAction.class);
    private static final FixedColumnsModel wholeTableModel =
        new FixedColumnsModel(new CollectionListModel<>(MathSymbol.symbols, true), 5);

    @Override
    @SuppressWarnings("unchecked")
    public void actionPerformed(@NotNull AnActionEvent event) {
        final Editor editor = event.getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            return;
        }

        final Project project = event.getProject();
        if (project == null) {
            return;
        }

        JBTable jbTable = new JBTable(wholeTableModel);
        jbTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jbTable.setTableHeader(null);
        jbTable.setCellSelectionEnabled(true);
        jbTable.setDefaultRenderer(Object.class, new MathTableCellRender());

        JBPopupFactory factory = JBPopupFactory.getInstance();
        PopupChooserBuilder<MathSymbol> builder = factory.createPopupChooserBuilder(jbTable);
        builder
            .setAutoselectOnMouseMove(true)
            .setResizable(true)
            .addAdditionalChooseKeystroke(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0))
            .setItemChoosenCallback(() -> {
                int row = jbTable.getSelectedRow();
                int col = jbTable.getSelectedColumn();

                MathSymbol symbol = (MathSymbol) jbTable.getModel().getValueAt(row, col);

                if (symbol == null) {
                    return;
                }

                Document document = editor.getDocument();
                Caret caret = editor.getCaretModel().getPrimaryCaret();
                WriteCommandAction.runWriteCommandAction(project, () -> {
                    if (caret.hasSelection()) {
                        int start = caret.getSelectionStart();
                        int end = caret.getSelectionEnd();
                        document.replaceString(start, end, symbol.chars);
                        caret.removeSelection();
                        return;
                    }

                    document.insertString(caret.getOffset(), symbol.chars);
                    caret.moveCaretRelatively(symbol.chars.length(), 0, false, false);
                });
            });

        JBPopup popup = builder.createPopup();
        jbTable.addKeyListener(new KeyAdapter() {
            private String text = "";

            private void refresh() {
                popup.setAdText(text.trim(), SwingConstants.LEFT);

                if (StringUtils.isEmpty(text)) {
                    jbTable.setModel(wholeTableModel);
                    return;
                }

                String[] searchWords = StringUtils.splitByWholeSeparator(text, null);

                List<MathSymbol> filteredSymbols = MathSymbol.symbols.stream().filter(symbol -> {
                    if (text.startsWith("\\")) {
                        return StringUtils.startsWith(symbol.latex, text);
                    }

                    String[] targets = StringUtils.splitByWholeSeparator(symbol.title, null);

                    if (searchWords.length > targets.length)
                        return false;

                    for (int i = 0; i < searchWords.length; i++) {
                        if (StringUtils.startsWith(targets[i], searchWords[i]))
                            continue;

                        return false;
                    }

                    return true;
                }).collect(Collectors.toList());
                jbTable.setModel(new FixedColumnsModel(new CollectionListModel<>(filteredSymbols, true), Math.min(filteredSymbols.size(), 5)));
                jbTable.setRowSelectionInterval(0, 0);
                jbTable.setColumnSelectionInterval(0, 0);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.isActionKey())
                    return;

                if (e.isConsumed())
                    return;

                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    if (StringUtils.isEmpty(text)) {
                        return;
                    }
                    e.consume();
                    text = text.substring(0, text.length() - 1);
                    refresh();
                    return;
                }

                char keyChar = e.getKeyChar();
                if (!StringUtils.isAsciiPrintable("" + keyChar)) {
                    return;
                }
                e.consume();
                text = text + keyChar;
                refresh();
            }
        });

        popup.showInBestPositionFor(editor);
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
    }

    private static class MathTableCellRender extends DefaultTableCellRenderer {
        @Override
        protected void setValue(Object value) {
            if (!(value instanceof MathSymbol)) {
                setText("");
                setToolTipText("");
                return;
            }

            MathSymbol letter = (MathSymbol) value;
            setText(letter.chars);

            String tooltip = "[" + letter.title + "]";
            if (letter.latex != null) {
                tooltip = "(" + letter.latex + ") " + tooltip;
            }
            setToolTipText(tooltip);
        }
    }
}
