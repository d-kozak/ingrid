package premun.mps.ingrid.importer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * Class represents an input form, that prompts user for import data.
 */
public class ImportForm {
    private JList fileList;
    private JButton addFileButton;
    private JButton removeFileButton;
    private JPanel buttonPanel;
    private JPanel fileButtonPanel;
    private JPanel fileLabelPanel;
    private JPanel fileListPanel;
    private JPanel filePanel;
    private JPanel mainPanel;
    private JButton importButton;
    private JButton cancelButton;
    private JComboBox languages;
    private JPanel languagePanel;
    private JPanel rootRule;
    private JTextField rootRuleTextField;
    private JPanel inlineRulesPanel;
    private JTextArea inlineRulesTextArea;
    private JPanel simplifySeparatorListPanel;
    private JTabbedPane tabbedPane;
    private JCheckBox enableRuleInliningCheckBox;
    private JCheckBox simplifyListsWithSeparatorsCheckBox;

    private static JDialog frame = new JDialog(); // JFrame was not working together with MPS

    private List<File> files;
    private boolean confirmed;
    private String language;
    private List<String> mpsLanguages;

    public ImportForm(List<String> mpsLanguages) {
        this.mpsLanguages = mpsLanguages;
        this.files = new ArrayList<>();
        this.confirmed = false;

        frame.setTitle("Import ANTLRv4 grammar");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(this.mainPanel);
        frame.setSize(600, 300);
        frame.setModal(true);

        // Main buttons
        this.cancelButton.addActionListener(e -> this.cancelDialog());
        this.importButton.addActionListener(e -> this.confirmDialog());

        // Plus / minus buttons
        this.addFileButton.addActionListener(e -> this.addFiles());

        this.removeFileButton.setEnabled(false);
        this.removeFileButton.addActionListener(e -> this.removeFiles());

        // File list
        this.fileList.setModel(new DefaultListModel<String>());
        this.fileList.addListSelectionListener(e ->
                this.removeFileButton.setEnabled(!this.fileList.isSelectionEmpty())
        );

        // Language combo box
        DefaultComboBoxModel<String> languageModel = new DefaultComboBoxModel<>();
        this.mpsLanguages
                .stream()
                .forEach(languageModel::addElement);
        languageModel.addElement("New language...");
        this.languages.setModel(languageModel);

        this.enableRuleInliningCheckBox.addChangeListener(__ -> {
            if (enableRuleInliningCheckBox.isSelected()) {
                inlineRulesTextArea.setBackground(Color.white);
                inlineRulesTextArea.setEnabled(true);
            } else {
                inlineRulesTextArea.setBackground(Color.lightGray);
                inlineRulesTextArea.setEnabled(false);
            }
        });
        this.enableRuleInliningCheckBox.setSelected(false);
        this.tabbedPane.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        new ImportForm(Collections.emptyList()).openAndGet();
    }

    /**
     * Opens the form and waits for its closing. Then reports success.
     *
     * @return True, when the form has been filled in correctly and Import button has benn clicked.
     */
    public boolean openAndGet() {
        frame.setVisible(true);
        return this.isConfirmed();
    }

    public boolean isConfirmed() {
        return this.confirmed;
    }

    public String getLanguage() {
        return this.language;
    }


    /**
     * @return a DTO containing all important imformation specified in form.
     * @see IngridConfiguration
     */
    public IngridConfiguration getIngridConfiguration() {
        return new IngridConfiguration(
                getFiles(),
                getRootRule(),
                getRulesToInline(),
                simplifyListsWithSeparators()
        );
    }


    private File[] getFiles() {
        return this.files.toArray(new File[0]);
    }

    private String getRootRule() {
        return this.rootRuleTextField.getText();
    }

    private List<String> getRulesToInline() {
        if (!enableRuleInliningCheckBox.isSelected())
            return Collections.emptyList();
        return Arrays.stream(inlineRulesTextArea.getText()
                                                .split("\n"))
                     .map(String::trim)
                     .collect(toList());
    }

    private boolean simplifyListsWithSeparators() {
        return simplifyListsWithSeparatorsCheckBox.isSelected();
    }


    /**
     * Called, when user presses the "Import" button.
     */
    private void confirmDialog() {
        if (this.files.size() == 0) {
            this.showError("No grammar files selected!");
            return;
        }

        // "New language..." selected
        if (this.languages.getSelectedIndex() == this.languages.getModel()
                                                               .getSize() - 1) {
            this.language = null;
        } else {
            this.language = (String) this.languages.getSelectedItem();
        }

        this.confirmed = true;
        this.closeDialog();
    }

    /**
     * Called, when user presses the "Import" button.
     */
    private void cancelDialog() {
        this.confirmed = false;
        this.closeDialog();
    }

    /**
     * Closes the dialog in the same way as when the cross is clicked.
     */
    private void closeDialog() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    /**
     * Prompts user through file dialog to choose grammar files and adds them into the file list and the files field.
     */
    private void addFiles() {
        JFileChooser dialog = new JFileChooser();
        dialog.setMultiSelectionEnabled(true);
        dialog.addChoosableFileFilter(new FileNameExtensionFilter("ANTLRv4", "g4", "g"));
        dialog.showOpenDialog(frame);

        // Add selected files
        files.addAll(Arrays.asList(dialog.getSelectedFiles()));

        // Remove duplicates from the files list
        files = files
                .stream()
                .filter(distinctFile(File::getPath))
                .collect(toList());

        this.syncFilesAndList();
    }

    /**
     * Removes rows selected in the list from the file list and the files field.
     */
    private void removeFiles() {
        this.fileList
                .getSelectedValuesList()
                .stream()
                .forEach(i -> this.files.removeIf(f -> f.getName()
                                                        .equals(i)));

        this.syncFilesAndList();
    }

    /**
     * Synchronizes content of the files field and the files list.
     */
    private void syncFilesAndList() {
        DefaultListModel listModel = (DefaultListModel) this.fileList.getModel();
        listModel.clear();
        files
                .stream()
                .map(File::getName)
                .forEach(listModel::addElement);
    }

    /**
     * Predicate used for detecting non-unique elements of an array.
     *
     * @param keyExtractor Object property extractor
     * @param <T>          Type of the object
     * @return A Predicate that allows array unique operation.
     */
    private static <T> Predicate<T> distinctFile(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        tabbedPane = new JTabbedPane();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(tabbedPane, gbc);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        tabbedPane.addTab("Common", panel1);
        filePanel = new JPanel();
        filePanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(8, 8, 8, 8);
        panel1.add(filePanel, gbc);
        fileButtonPanel = new JPanel();
        fileButtonPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        filePanel.add(fileButtonPanel, gbc);
        addFileButton = new JButton();
        Font addFileButtonFont = this.$$$getFont$$$(null, Font.BOLD, 16, addFileButton.getFont());
        if (addFileButtonFont != null) addFileButton.setFont(addFileButtonFont);
        addFileButton.setText("+");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        fileButtonPanel.add(addFileButton, gbc);
        removeFileButton = new JButton();
        Font removeFileButtonFont = this.$$$getFont$$$(null, Font.BOLD, 16, removeFileButton.getFont());
        if (removeFileButtonFont != null) removeFileButton.setFont(removeFileButtonFont);
        removeFileButton.setText("-");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        fileButtonPanel.add(removeFileButton, gbc);
        fileLabelPanel = new JPanel();
        fileLabelPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        filePanel.add(fileLabelPanel, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("Grammar files:      ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        fileLabelPanel.add(label1, gbc);
        fileListPanel = new JPanel();
        fileListPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 8, 0, 0);
        filePanel.add(fileListPanel, gbc);
        fileList = new JList();
        fileList.setSelectionMode(2);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        fileListPanel.add(fileList, gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 8, 8, 8);
        panel1.add(panel2, gbc);
        languagePanel = new JPanel();
        languagePanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(languagePanel, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        languagePanel.add(panel3, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Target language:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label2, gbc);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 8, 0, 0);
        languagePanel.add(panel4, gbc);
        languages = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel4.add(languages, gbc);
        rootRule = new JPanel();
        rootRule.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 8, 0, 8);
        panel1.add(rootRule, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Root rule: (leave empty for first rule):");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        rootRule.add(label3, gbc);
        rootRuleTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootRule.add(rootRuleTextField, gbc);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridBagLayout());
        tabbedPane.addTab("Transformations", panel5);
        inlineRulesPanel = new JPanel();
        inlineRulesPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(8, 8, 8, 8);
        panel5.add(inlineRulesPanel, gbc);
        inlineRulesTextArea = new JTextArea();
        inlineRulesTextArea.setBackground(new Color(-4144960));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(8, 0, 0, 0);
        inlineRulesPanel.add(inlineRulesTextArea, gbc);
        enableRuleInliningCheckBox = new JCheckBox();
        enableRuleInliningCheckBox.setText("Enable rule inlining");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        inlineRulesPanel.add(enableRuleInliningCheckBox, gbc);
        simplifySeparatorListPanel = new JPanel();
        simplifySeparatorListPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        panel5.add(simplifySeparatorListPanel, gbc);
        simplifyListsWithSeparatorsCheckBox = new JCheckBox();
        simplifyListsWithSeparatorsCheckBox.setText("Simplify lists with separators");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        simplifySeparatorListPanel.add(simplifyListsWithSeparatorsCheckBox, gbc);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        mainPanel.add(buttonPanel, gbc);
        importButton = new JButton();
        importButton.setText("Import");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        buttonPanel.add(importButton, gbc);
        cancelButton = new JButton();
        cancelButton.setText("Cancel");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        buttonPanel.add(cancelButton, gbc);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
