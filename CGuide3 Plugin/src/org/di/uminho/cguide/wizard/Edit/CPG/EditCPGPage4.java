package org.di.uminho.cguide.wizard.Edit.CPG;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.AbstractOWLWizardPanel;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public class EditCPGPage4 extends AbstractOWLWizardPanel {

	public static final String ID = "EditCPGPage4";

	public static final String title = "Insert Scope Data Required";

	private JLabel target_label;
	private JLabel added_label;

	private JTextArea target_jtext;

	private JList list;
	public DefaultListModel<String> model;

	private JButton btnAdd;
	private JButton btnRemove;

	public Set<String> targetpopulation_set;

	public EditCPGPage4(OWLEditorKit owlEditorKit, String cpg_individual) {
		super(ID, title, owlEditorKit);
		getCPGData(cpg_individual);
	}

	protected void createUI(JComponent parent) {
		setInstructions(
				"Please write the set of target population where should be applied this Clinical Practice Guideline and click in the Add button.\nYou can also select an added content from the list and remove it.");

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 171, 233, 0 };
		gridBagLayout.rowHeights = new int[] { 31, 14, 150, 23, 29, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		parent.setLayout(gridBagLayout);

		added_label = new JLabel("Added:");
		GridBagConstraints gbc_lblAdded = new GridBagConstraints();
		gbc_lblAdded.anchor = GridBagConstraints.CENTER;
		gbc_lblAdded.insets = new Insets(0, 0, 5, 5);
		gbc_lblAdded.gridx = 0;
		gbc_lblAdded.gridy = 1;
		parent.add(added_label, gbc_lblAdded);

		target_label = new JLabel("Target Population:");
		GridBagConstraints gbc_lblDiseaseOrCondition = new GridBagConstraints();
		gbc_lblDiseaseOrCondition.anchor = GridBagConstraints.CENTER;
		gbc_lblDiseaseOrCondition.insets = new Insets(0, 0, 5, 0);
		gbc_lblDiseaseOrCondition.gridx = 1;
		gbc_lblDiseaseOrCondition.gridy = 1;
		parent.add(target_label, gbc_lblDiseaseOrCondition);

		model = new DefaultListModel<String>();
		list = new JList<String>(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(getOWLEditorKit().getWorkspace().createOWLCellRenderer());
		list.setFixedCellHeight(20);
		list.setFixedCellWidth(100);

		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.gridx = 0;
		gbc_list.gridy = 2;
		parent.add(new JScrollPane(list), gbc_list);

		target_jtext = new JTextArea();
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridheight = 3;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 2;
		parent.add(new JScrollPane(target_jtext), gbc_textArea);

		btnAdd = new JButton(new AbstractAction("Add") {
			public void actionPerformed(ActionEvent e) {
				if (!target_jtext.getText().isEmpty()) {
					model.addElement(target_jtext.getText());
					list.setModel(model);
					target_jtext.setText(null);
				}
			}
		});

		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.anchor = GridBagConstraints.CENTER;
		gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdd.gridx = 0;
		gbc_btnAdd.gridy = 3;
		parent.add(btnAdd, gbc_btnAdd);

		btnRemove = new JButton(new AbstractAction("Remove") {
			public void actionPerformed(ActionEvent e) {
				if (list.getSelectedIndex() > -1) {
					model.remove(list.getSelectedIndex());
					list.setModel(model);
				}
			}
		});

		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.anchor = GridBagConstraints.CENTER;
		gbc_btnRemove.insets = new Insets(0, 0, 0, 5);
		gbc_btnRemove.gridx = 0;
		gbc_btnRemove.gridy = 4;
		parent.add(btnRemove, gbc_btnRemove);

	}

	public Object getBackPanelDescriptor() {
		return EditCPGPage3.ID;
	}

	public Object getNextPanelDescriptor() {
		return EditCPGPage5.ID;
	}

	@Override
	public void aboutToDisplayPanel() {
		for (String a : this.targetpopulation_set) {
			model.addElement(a);
		}
		list.setModel(model);
		// TODO Auto-generated method stub
		super.aboutToDisplayPanel();
	}

	public void getCPGData(String cpg_individual_name) {
		this.targetpopulation_set = new HashSet<String>();

		OWLNamedIndividual cpg_individual = getOWLModelManager().getOWLDataFactory().getOWLNamedIndividual(IRI.create(
				getOWLModelManager().getActiveOntology().getOntologyID().getOntologyIRI() + "#" + cpg_individual_name));

		OWLObjectProperty scope_objectproperty = getOWLModelManager().getOWLDataFactory().getOWLObjectProperty(
				IRI.create(getOWLModelManager().getActiveOntology().getOntologyID().getOntologyIRI() + "#hasScope"));

		try {
			OWLNamedIndividual scope_individual = cpg_individual
					.getObjectPropertyValues(scope_objectproperty, getOWLModelManager().getActiveOntology()).iterator()
					.next().asOWLNamedIndividual();

			OWLDataProperty targetpopulation_dataproperty = getOWLModelManager().getOWLDataFactory().getOWLDataProperty(
					IRI.create(getOWLModelManager().getActiveOntology().getOntologyID().getOntologyIRI()
							+ "#targetPopulation"));

			Set<OWLLiteral> targetpopulation_literal_set = new HashSet<OWLLiteral>();

			targetpopulation_literal_set = scope_individual.getDataPropertyValues(targetpopulation_dataproperty,
					getOWLModelManager().getActiveOntology());

			for (OWLLiteral a : targetpopulation_literal_set) {
				this.targetpopulation_set.add(a.getLiteral());
			}
		} catch (Exception e) {
		}

	}
}
