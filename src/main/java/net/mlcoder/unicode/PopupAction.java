package net.mlcoder.unicode;

import com.intellij.ide.plugins.newui.VerticalLayout;
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
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.JBFont;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import net.mlcoder.unicode.category.MathSymbol;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.stream.Collectors;

public class PopupAction extends AnAction {
    private static final Logger logger = Logger.getInstance(PopupAction.class);
    private static final FixedColumnsModel wholeTableModel =
        new FixedColumnsModel(new CollectionListModel<>(MathSymbol.symbols, true), 5);

    private static JBTable createMathTable() {
        JBTable jbTable = new JBTable(wholeTableModel);
        jbTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jbTable.setTableHeader(null);
        jbTable.setCellSelectionEnabled(true);
        jbTable.setDefaultRenderer(Object.class, new MathRichTableCellRender());

        return jbTable;
    }

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

        JBPopupFactory factory = JBPopupFactory.getInstance();
        JBTable jbTable = createMathTable();
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
        popup.setMinimumSize(new Dimension(400, 500));
        popup.setRequestFocus(true);
        jbTable.addKeyListener(new MathTableKeyListener(jbTable, popup));

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

            String tooltip = "[" + letter.desc + "]";
            if (letter.latex != null) {
                tooltip = "(" + letter.latex + ") " + tooltip;
            }
            setToolTipText(tooltip);
        }
    }

    private static class MathTableKeyListener extends KeyAdapter {
        private final JBTable jbTable;
        private final JBPopup popup;
        private String text = "";

        public MathTableKeyListener(JBTable table, JBPopup popup) {
            this.jbTable = table;
            this.popup = popup;
        }

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

                String[] targets = StringUtils.splitByWholeSeparator(symbol.desc, null);

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
    }

    @SuppressWarnings("rawtypes")
    private static class MathRichTableCellRender implements TableCellRenderer {
        private final JBFont symbolFont = JBFont.label().biggerOn(3f);
        private final JBFont latexFont = JBUI.Fonts.create(Font.MONOSPACED, JBUI.Fonts.smallFont().getSize()).asItalic();

        private final JBPanel emptyContainer = new JBPanel()
            .withBackground(UIUtil.getPanelBackground());


        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (!(value instanceof MathSymbol)) {
                return emptyContainer;
            }

            MathSymbol symbol = (MathSymbol) value;

            if (symbol.latex == null) {
                JBLabel symbolLabel = new JBLabel(symbol.chars, SwingConstants.CENTER)
                    .withFont(symbolFont).andOpaque();

                symbolLabel.setBackground(UIUtil.getTableBackground(isSelected, hasFocus));
                symbolLabel.setForeground(UIUtil.getTableForeground(isSelected, hasFocus));
                symbolLabel.withBorder(isSelected ? UIUtil.getTableFocusCellHighlightBorder() : symbolLabel.getBorder());
                symbolLabel.setToolTipText(symbol.desc);
                return symbolLabel;
            }

            JBPanel container = new JBPanel(new VerticalLayout(10))
                .withBackground(UIUtil.getTableBackground(isSelected, hasFocus));

            container.setToolTipText(symbol.desc);
            if (isSelected) {
                container.withBorder(UIUtil.getTableFocusCellHighlightBorder());
            }

            JBLabel symbolLabel = new JBLabel(symbol.chars, SwingConstants.CENTER).withFont(symbolFont);
            symbolLabel.setForeground(UIUtil.getTableForeground(isSelected, hasFocus));

            JBLabel latex = new JBLabel(symbol.latex, SwingConstants.CENTER).withFont(latexFont);
            latex.setForeground(UIUtil.getTableForeground(isSelected, hasFocus));

            container.add(symbolLabel, VerticalLayout.FILL_HORIZONTAL);
            container.add(latex, VerticalLayout.FILL_HORIZONTAL);
            return container;
        }
    }
}
