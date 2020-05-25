package net.mlcoder.unimath;

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
import com.intellij.openapi.ui.popup.*;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.components.FixedColumnsModel;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.JBFont;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import net.mlcoder.unimath.category.*;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.*;

public class PopupAction extends AnAction {
    private static final Logger logger = Logger.getInstance(PopupAction.class);
    private static final List<UniCode> allUniCodes;
    private static final FixedColumnsModel wholeTableModel;
    private static final FixedColumnsModel emptyTableModel;
    private static final Comparator<UniCode> latexComparator = (o1, o2) -> StringUtils.compareIgnoreCase(o1.latex(), o2.latex());
    private static final Comparator<UniCode> ulatexComparator = (o1, o2) -> StringUtils.compareIgnoreCase(o1.ulatex(), o2.ulatex());



    static {
        allUniCodes = new ArrayList<>();
        allUniCodes.addAll(MathLetter.symbols);
        allUniCodes.addAll(MathOperator.symbols);
        allUniCodes.addAll(Arrow.symbols);
        allUniCodes.addAll(Shapes.symbols);
        allUniCodes.addAll(Pictographs.symbols);
        allUniCodes.addAll(CombininingMark.symbols);
        wholeTableModel =
            new FixedColumnsModel(new CollectionListModel<>(allUniCodes, true), 5);
        emptyTableModel =
            new FixedColumnsModel(new CollectionListModel<>(new CollectionListModel<>(Collections.emptyList(), true)), 1);
    }

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
        PopupChooserBuilder<UniCode> builder = factory.createPopupChooserBuilder(jbTable);
        builder
            .setAutoselectOnMouseMove(true)
            .setResizable(true)
            .addAdditionalChooseKeystroke(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0))
            .setItemChoosenCallback(() -> {
                int row = jbTable.getSelectedRow();
                int col = jbTable.getSelectedColumn();

                UniCode symbol = (UniCode) jbTable.getModel().getValueAt(row, col);

                if (symbol == null) {
                    return;
                }

                Document document = editor.getDocument();
                Caret caret = editor.getCaretModel().getPrimaryCaret();
                WriteCommandAction.runWriteCommandAction(project, () -> {
                    if (caret.hasSelection()) {
                        int start = caret.getSelectionStart();
                        int end = caret.getSelectionEnd();
                        document.replaceString(start, end, symbol.chars());
                        caret.removeSelection();
                        return;
                    }

                    document.insertString(caret.getOffset(), symbol.chars());
                    caret.moveCaretRelatively(symbol.chars().length(), 0, false, false);
                });
            });

        JBPopup popup = builder.createPopup();
        popup.setMinimumSize(new Dimension(400, 340));
        popup.setRequestFocus(true);
        jbTable.addKeyListener(new MathTableKeyListener(jbTable, popup));

        popup.showInBestPositionFor(editor);
    }

    @Override
    public void update(@NotNull AnActionEvent e) {

    }

    private static class MathTableKeyListener extends KeyAdapter {
        private final JBTable jbTable;
        private final JBPopup popup;
        private String text = "";

        @Nullable
        private Balloon balloon;

        public MathTableKeyListener(JBTable table, JBPopup popup) {
            this.jbTable = table;
            this.popup = popup;
        }

        private void refresh() {
            popup.setAdText(text.trim(), SwingConstants.LEFT);

            if (StringUtils.isEmpty(text)) {
                if (jbTable.getModel() == wholeTableModel)
                    return;

                jbTable.setModel(wholeTableModel);
                jbTable.setRowSelectionInterval(0, 0);
                jbTable.setColumnSelectionInterval(0, 0);
                return;
            }

            if (text.startsWith("U+")) {
                Optional<UniCode> any = Optional.empty();
                try {
                    int codePoint = Integer.parseInt(text.substring(2), 16);
                    any = allUniCodes.stream().filter(uniCode -> uniCode.codePoint() == codePoint).findAny();
                } catch (Exception ignored) {
                }

                if (any.isEmpty()) {
                    if (jbTable.getModel() != emptyTableModel)
                        jbTable.setModel(emptyTableModel);
                    return;
                }

                FixedColumnsModel model = new FixedColumnsModel(new CollectionListModel<>(Collections.singletonList(any.get())), 1);
                jbTable.setModel(model);
                jbTable.setRowSelectionInterval(0, 0);
                jbTable.setColumnSelectionInterval(0, 0);
                return;
            }

            if (StringUtils.startsWith(text, "\\")) {
                TreeSet<UniCode> latexes = new TreeSet<>(latexComparator);
                TreeSet<UniCode> ulatexes = new TreeSet<>(ulatexComparator);
                allUniCodes.forEach(ud -> {
                    if (StringUtils.startsWith(ud.latex(), text)) {
                        latexes.add(ud);
                        return;
                    }
                    if (StringUtils.startsWith(ud.ulatex(), text)) {
                        ulatexes.add(ud);
                    }
                });

                List<UniCode> sorted = new ArrayList<>(latexes.size() + ulatexes.size());
                sorted.addAll(latexes);
                sorted.addAll(ulatexes);

                jbTable.setModel(new FixedColumnsModel(new CollectionListModel<>(sorted, true), Math.min(latexes.size(), 5)));
                if (latexes.size() > 0) {
                    jbTable.setRowSelectionInterval(0, 0);
                    jbTable.setColumnSelectionInterval(0, 0);
                }
                return;
            }

            List<String> searchWords = Arrays.asList(StringUtils.split(text, " "));
            String tex = searchWords.size() == 1 ? "\\" + searchWords.get(0) : null;

            TreeSet<UniCode> latexes = new TreeSet<>(latexComparator);
            TreeSet<UniCode> ulatexes = new TreeSet<>(ulatexComparator);

            List<UniCode> prefixMatch = Lists.newArrayList();
            List<UniCode> containMatchSymbols = Lists.newArrayList();

            allUniCodes.forEach(symbol -> {
                if (searchWords.size() >  symbol.tokenized().length)
                    return;

                if (tex != null && symbol.hasTex()) {
                    if (StringUtils.startsWith(symbol.latex(), tex)) {
                        latexes.add(symbol);
                    }

                    if (StringUtils.startsWith(symbol.ulatex(), tex)) {
                        ulatexes.add(symbol);
                    }

                    return;
                }

                // seq seqrch
                String[] tokenized = symbol.tokenized();
                boolean found = true;
                int j = 0;
                for (String keyword : searchWords) {
                    for (; j < tokenized.length; j++) {
                        if (StringUtils.startsWith(tokenized[j], keyword))
                            break;
                    }

                    if (j >= tokenized.length) {
                        found = false;
                    }
                }

                if (found) {
                    prefixMatch.add(symbol);
                    return;
                }

                if (searchWords.stream().allMatch(k -> Arrays.stream(symbol.tokenized()).anyMatch(t -> t.contains(k)))) {
                    containMatchSymbols.add(symbol);
                }
            });

            List<UniCode> sorted = new ArrayList<>(latexes.size() +
                + ulatexes.size()
                + prefixMatch.size()
                + containMatchSymbols.size()
            );
            sorted.addAll(latexes);
            sorted.addAll(ulatexes);
            sorted.addAll(prefixMatch);
            sorted.addAll(containMatchSymbols);

            jbTable.setModel(new FixedColumnsModel(new CollectionListModel<>(sorted, true), Math.min(sorted.size(), 5)));
            if (sorted.size() > 0) {
                jbTable.setRowSelectionInterval(0, 0);
                jbTable.setColumnSelectionInterval(0, 0);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (balloon != null && !balloon.isDisposed()) {
                balloon.dispose();
                balloon = null;
            }

            if (e.getKeyCode() == KeyEvent.VK_ALT) {
                int row = jbTable.getSelectedRow();
                if (row < 0)
                    return;

                int col = jbTable.getSelectedColumn();
                if (col < 0)
                    return;

                FixedColumnsModel model = (FixedColumnsModel) jbTable.getModel();
                UniCode symbol = (UniCode) model.getValueAt(row, col);
                if (symbol == null) {
                    return;
                }

                JBLabel ballonText = new JBLabel(UIUtil.getBalloonInformationIcon());
                JBPopupFactory factory = JBPopupFactory.getInstance();
                ballonText.setText(toolTipText(symbol));

                BalloonBuilder builder = factory.createBalloonBuilder(ballonText);
                balloon = builder.createBalloon();
                balloon.showInCenterOf(jbTable);
                e.consume();
                return;
            }

            if (e.isActionKey())
                return;

            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if (StringUtils.isEmpty(text)) {
                    return;
                }
                text = text.substring(0, text.length() - 1);
                e.consume();
                refresh();
                return;
            }

            if (e.isControlDown()) {
                if (jbTable.getRowCount() <= 0)
                    return;

                if (jbTable.getColumnCount() <= 0)
                    return;

                int column;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                        column = 0;
                        break;
                    case KeyEvent.VK_E:
                        column = jbTable.getColumnCount() - 1;
                        break;
                    default:
                        return;
                }

                int row = jbTable.getSelectedRow();

                jbTable.setRowSelectionInterval(Math.max(0, row), Math.max(0, row));
                jbTable.setColumnSelectionInterval(column, column);
                e.consume();
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

        @Override
        public void keyReleased(KeyEvent e) {
            if (balloon != null && !balloon.isDisposed()) {
                balloon.dispose();
                balloon = null;
            }
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
            if (!(value instanceof UniCode)) {
                return emptyContainer;
            }

            UniCode symbol = (UniCode) value;

            if (symbol.latex() == null && symbol.ulatex() == null) {
                JBLabel symbolLabel = new JBLabel(symbol.chars(), SwingConstants.CENTER)
                    .withFont(symbolFont).andOpaque();

                symbolLabel.setBackground(UIUtil.getTableBackground(isSelected, hasFocus));
                symbolLabel.setForeground(UIUtil.getTableForeground(isSelected, hasFocus));
                symbolLabel.withBorder(isSelected ? UIUtil.getTableFocusCellHighlightBorder() : symbolLabel.getBorder());
                symbolLabel.setToolTipText(toolTipText(symbol));
                return symbolLabel;
            }

            JBPanel container = new JBPanel(new VerticalLayout(10))
                .withBackground(UIUtil.getTableBackground(isSelected, hasFocus));

            container.setToolTipText(toolTipText(symbol));
            if (isSelected) {
                container.withBorder(UIUtil.getTableFocusCellHighlightBorder());
            }

            JBLabel symbolLabel = new JBLabel(symbol.chars(), SwingConstants.CENTER).withFont(symbolFont);
            symbolLabel.setForeground(UIUtil.getTableForeground(isSelected, hasFocus));

            JBLabel latex = new JBLabel(Objects.requireNonNull(symbol.latex() != null ? symbol.latex() : symbol.ulatex()), SwingConstants.CENTER).withFont(latexFont);
            latex.setForeground(UIUtil.getTableForeground(isSelected, hasFocus));

            container.add(symbolLabel, VerticalLayout.FILL_HORIZONTAL);
            container.add(latex, VerticalLayout.FILL_HORIZONTAL);
            return container;
        }
    }

    private static String toolTipText(UniCode uniCode) {
        return uniCode.ulatex() == null ?
            uniCode.desc() + " (" + uniCode.codeStr() + ")" :
            uniCode.desc() + " [" + uniCode.ulatex() + "]" + " (" + uniCode.codeStr() + ")";
    }

}
