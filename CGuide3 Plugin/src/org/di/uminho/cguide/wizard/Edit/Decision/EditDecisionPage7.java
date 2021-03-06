package org.di.uminho.cguide.wizard.Edit.Decision;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JRadioButton;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.AbstractOWLWizardPanel;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public class EditDecisionPage7 extends AbstractOWLWizardPanel {

	public static final String ID = "EditDecisionPage7";

	public static final String title = "Decision Next Clinical Task";

	public int choice = -1;

	private JRadioButton paralleltask;
	private JRadioButton preferencealternativetask;
	private JRadioButton alternativetask;
	private JRadioButton nexttask;

	public int selected_next_task;

	public EditDecisionPage7(OWLEditorKit owlEditorKit, String individual) {
		super(ID, title, owlEditorKit);
		this.selected_next_task = getIndividual(individual);
	}

	protected void createUI(JComponent parent) {
		setInstructions("Please select the type of next Clinical Task.");
		parent.setLayout(new BorderLayout());

		Box box = new Box(BoxLayout.Y_AXIS);
		parent.add(box, BorderLayout.CENTER);

		paralleltask = new JRadioButton("Parallel Task");
		box.add(paralleltask);
		preferencealternativetask = new JRadioButton("Preference Alternative Task");
		box.add(preferencealternativetask);
		alternativetask = new JRadioButton("Alternative Task");
		box.add(alternativetask);
		nexttask = new JRadioButton("Next Task");
		box.add(nexttask);
		ButtonGroup bg = new ButtonGroup();
		bg.add(paralleltask);
		bg.add(preferencealternativetask);
		bg.add(alternativetask);
		bg.add(nexttask);
	}

	public Object getBackPanelDescriptor() {
		return EditDecisionPage6.ID;
	}

	public Object getNextPanelDescriptor() {
		if (paralleltask.isSelected()) {
			choice = 1;
			return EditDecisionPage8parallel.ID;
		} else if (preferencealternativetask.isSelected()) {
			choice = 2;
			return EditDecisionPage8preferencealternative.ID;
		} else if (alternativetask.isSelected()) {
			choice = 3;
			return EditDecisionPage8alternative.ID;
		} else if (nexttask.isSelected()) {
			choice = 4;
			return EditDecisionPage8next.ID;
		} else
			choice = -1;
		return EditDecisionPage7.ID;
	}

	@Override
	public void aboutToDisplayPanel() {
		if (this.selected_next_task == 1)
			paralleltask.setSelected(true);
		else if (this.selected_next_task == 2)
			preferencealternativetask.setSelected(true);
		else if (this.selected_next_task == 3)
			alternativetask.setSelected(true);
		else if (this.selected_next_task == 4)
			nexttask.setSelected(true);
		// TODO Auto-generated method stub
		super.aboutToDisplayPanel();
	}

	public int getIndividual(String individual_name) {

		int res = 0;

		OWLNamedIndividual individual = getOWLModelManager().getOWLDataFactory().getOWLNamedIndividual(IRI.create(
				getOWLModelManager().getActiveOntology().getOntologyID().getOntologyIRI() + "#" + individual_name));

		OWLObjectProperty alternativeTask_objectproperty = getOWLModelManager().getOWLDataFactory()
				.getOWLObjectProperty(
						IRI.create(getOWLModelManager().getActiveOntology().getOntologyID().getOntologyIRI()
								+ "#alternativeTask"));

		OWLObjectProperty parallelTask_objectproperty = getOWLModelManager().getOWLDataFactory()
				.getOWLObjectProperty(IRI.create(
						getOWLModelManager().getActiveOntology().getOntologyID().getOntologyIRI() + "#parallelTask"));

		OWLObjectProperty preferenceAlternativeTask_objectproperty = getOWLModelManager().getOWLDataFactory()
				.getOWLObjectProperty(
						IRI.create(getOWLModelManager().getActiveOntology().getOntologyID().getOntologyIRI()
								+ "#preferenceAlternativeTask"));

		OWLObjectProperty nextTask_objectproperty = getOWLModelManager().getOWLDataFactory().getOWLObjectProperty(
				IRI.create(getOWLModelManager().getActiveOntology().getOntologyID().getOntologyIRI() + "#nextTask"));

		try {
			OWLNamedIndividual parallelTask_individual = individual
					.getObjectPropertyValues(parallelTask_objectproperty, getOWLModelManager().getActiveOntology())
					.iterator().next().asOWLNamedIndividual();

			res = 1;

		} catch (Exception e) {
		}

		try {
			OWLNamedIndividual preferenceAlternativeTask_individual = individual
					.getObjectPropertyValues(preferenceAlternativeTask_objectproperty,
							getOWLModelManager().getActiveOntology())
					.iterator().next().asOWLNamedIndividual();

			res = 2;

		} catch (Exception e) {
		}

		try {
			OWLNamedIndividual alternativeTask_individual = individual
					.getObjectPropertyValues(alternativeTask_objectproperty, getOWLModelManager().getActiveOntology())
					.iterator().next().asOWLNamedIndividual();

			res = 3;

		} catch (Exception e) {
		}

		try {
			OWLNamedIndividual nextTask_individual = individual
					.getObjectPropertyValues(nextTask_objectproperty, getOWLModelManager().getActiveOntology())
					.iterator().next().asOWLNamedIndividual();

			res = 4;

		} catch (Exception e) {
		}
		return res;
	}

}
