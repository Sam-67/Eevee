import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import Controller.ProjetPlanningController;
import Domain.DataBase;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static JFrame f = new JFrame("Mini ERP Eevee");
	public static ProjetPlanningController pc = ProjetPlanningController.getProjetPlanningControllerInstance();
	
	public MainFrame() {
		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};

		addWindowListener(l);
	}

	public static void main(String[] args) {
		DataBase.getDataBaseInstance().init();
		
		f.setSize(700, 500);
		JPanel pannel = new JPanel();

		JTabbedPane onglets = new JTabbedPane(SwingConstants.TOP);

		JPanel planning_panel = new JPanel();
		planning_panel.setPreferredSize(new Dimension(700, 500));

		JLabel planning_title = new JLabel("Vérifier le planning");
		planning_panel.add(planning_title);
		onglets.addTab("Planning", planning_panel);

		JPanel project_panel = new JPanel();
		JLabel project_title = new JLabel("Ajouter ou supprimer des projets");
		project_panel.add(project_title);

		onglets.addTab("Projet", project_panel);

		JPanel efficience_panel = new JPanel();
		JLabel efficience_title = new JLabel("Modifier l'efficience d'un projet");
		efficience_panel.add(efficience_title);
		onglets.addTab("Efficience", efficience_panel);

		construct_planning(planning_panel);
		construct_efficience(efficience_panel);
		construct_project(project_panel);

		onglets.setOpaque(true);
		pannel.add(onglets);
		f.getContentPane().add(pannel);
		f.setVisible(true);
	}

	public static void construct_planning(JPanel project_panel) {
		JButton btn_planning = new JButton("Pour tous les projets");
		project_panel.add(btn_planning);
		
		String resultat = pc.getAllProjectsFeasibility(DataBase.getDataBaseInstance().getEntreprise()) + "\n";
		JLabel label = new JLabel(resultat);
		project_panel.add(label, BorderLayout.CENTER);
		label.setSize(50, 100);
		label.setVisible(false);
		
		btn_planning.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				label.setVisible(true);
			}
		});
	}

	public static void construct_project(JPanel project_panel) {

	}

	public static void construct_efficience(JPanel project_panel) {
		JButton bouton = new JButton("Mon bouton");
		project_panel.add(bouton);
	}

}