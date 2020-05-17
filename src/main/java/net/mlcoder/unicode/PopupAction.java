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
import com.intellij.ui.speedSearch.FilteringTableModel;
import com.intellij.ui.table.JBTable;
import net.mlcoder.unicode.category.MathSymbol;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PopupAction extends AnAction {
    private static final Logger logger = Logger.getInstance(PopupAction.class);
    private static final ListModel<MathSymbol> symbols = new CollectionListModel<>(MathSymbol.symbols);

    private static class Query {
        String text = "";
        String[] words = StringUtils.splitByWholeSeparator(text, null);

        void append(char ch) {
            text = (text + ch);
            split();
        }

        void append(CharSequence charSequence) {
            text = (text + charSequence);
            split();
        }

        void backspace() {
            if (StringUtils.isEmpty(text)) {
                return;
            }

            text = text.substring(0, text.length() - 1);
            split();
        }

        void split() {
            words = StringUtils.splitByWholeSeparator(text, null);
        }
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        final Editor editor = event.getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            return;
        }

        final Project project = event.getProject();
        if (project == null) {
            return;
        }

        MathSymbol symbol1 = MathSymbol.titleSymbols.get("alpha");
        logger.info("alpha: " + symbol1);
        FilteringTableModel<MathSymbol> tableModel = new FilteringTableModel<>(new FixedColumnsModel(symbols, 5), MathSymbol.class);
        JBTable jbTable = new JBTable(tableModel);
        Query query = new Query();
        tableModel.setFilter(symbol -> {
            if (StringUtils.isEmpty(query.text))
                return true;

            if (query.text.startsWith("\\")) {
                return StringUtils.startsWith(symbol.latex, query.text);
            }

            String[] searchWords = query.words;
            String[] targets = StringUtils.splitByWholeSeparator(symbol.title, null);

            if (searchWords.length > targets.length)
                return false;

            for (int i = 0; i < searchWords.length; i ++) {
                if (StringUtils.startsWith(targets[i], searchWords[i]))
                    continue;

                return false;
            }

            logger.info("targets: " + StringUtils.join(targets, "|"));

            return true;
        });

        jbTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jbTable.setTableHeader(null);
        jbTable.setCellSelectionEnabled(true);
        jbTable.setDefaultRenderer(Object.class, new MathTableCellRender());

        JBPopupFactory factory = JBPopupFactory.getInstance();
        PopupChooserBuilder<MathSymbol> builder = factory.createPopupChooserBuilder(jbTable);
        builder
            .setAdText(query.text)
            .setAutoselectOnMouseMove(true)
            .setResizable(true)
            .setItemChoosenCallback(() -> {
                int row = jbTable.getSelectedRow();
                int col = jbTable.getSelectedColumn();

                MathSymbol symbol = (MathSymbol) tableModel.getValueAt(row, col);
                logger.info("## (" +  + row + ",  " + col + ") " + symbol);

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
            private void setAdText() {
                popup.setAdText(query.text.trim(), SwingConstants.LEFT);
                tableModel.refilter();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.isActionKey())
                    return;

                if (e.isConsumed())
                    return;

                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    if (StringUtils.isEmpty(query.text)) {
                        return;
                    }
                    query.backspace();
                    setAdText();
                    return;
                }

                char keyChar = e.getKeyChar();
                if (!StringUtils.isAsciiPrintable("" + keyChar)) {
                    return;
                }
                e.consume();
                query.append(keyChar);
                setAdText();
            }
        });

        popup.setMinimumSize(new Dimension(160, 40));
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
