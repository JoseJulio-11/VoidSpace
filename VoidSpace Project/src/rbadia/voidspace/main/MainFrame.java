package rbadia.voidspace.main;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * The game's main frame. Contains all the game's labels, file menu, and game screen.
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private GameScreen gameScreen = null;
	
	private JLabel asteroidsDestroyedLabel;
	private JLabel asteroidsDestroyedValueLabel;
	
	private JLabel shipsDestroyedLabel;
	private JLabel shipsDestroyedValueLabel;

	private JLabel shipsLabel;
	private JLabel shipsValueLabel;
	
	private JLabel pointsLabel;
	private JLabel pointsValueLabel;
	
	private JLabel levelLabel;
	private JLabel levelValueLabel;
	
	/**
	 * This is the default constructor
	 */
	public MainFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(550, 500);
		this.setContentPane(getJContentPane());
		this.setTitle("Caw Space");
//		this.setResizable(false);
		
		Dimension dim = this.getToolkit().getScreenSize();
		Rectangle bounds = this.getBounds();
		this.setLocation(
			(dim.width - bounds.width) / 2,
			(dim.height - bounds.height) / 2);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints10.gridy = 1;
			gridBagConstraints10.anchor = GridBagConstraints.WEST;
			gridBagConstraints10.weightx = 1.0D;
			gridBagConstraints10.gridx = 3;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints9.gridy = 1;
			gridBagConstraints9.anchor = GridBagConstraints.EAST;
			gridBagConstraints9.weightx = 1.0D;
			gridBagConstraints9.gridx = 2;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints8.gridy = 3;
			gridBagConstraints8.anchor = GridBagConstraints.WEST;
			gridBagConstraints8.weightx = 1.0D;
			gridBagConstraints8.gridx = 1;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints7.gridy = 3;
			gridBagConstraints7.anchor = GridBagConstraints.EAST;
			gridBagConstraints7.weightx = 1.0D;
			gridBagConstraints7.gridx = 0;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints6.gridy = 2;
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.weightx = 1.0D;
			gridBagConstraints6.gridx = 1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.anchor = GridBagConstraints.EAST;
			gridBagConstraints5.weightx = 1.0D;
			gridBagConstraints5.gridx = 0;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints4.gridy = 2;
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.weightx = 1.0D;
			gridBagConstraints4.gridx = 3;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints3.gridy = 2;
			gridBagConstraints3.anchor = GridBagConstraints.EAST;
			gridBagConstraints3.weightx = 1.0D;
			gridBagConstraints3.gridx = 2;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.weightx = 1.0D;
			gridBagConstraints2.gridx = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.anchor = GridBagConstraints.EAST;
			gridBagConstraints1.weightx = 1.0D;
			gridBagConstraints1.gridx = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.fill = GridBagConstraints.NONE;
			gridBagConstraints.gridwidth = 4;
			shipsLabel = new JLabel("Ships Left: ");
			shipsValueLabel = new JLabel("3");
			asteroidsDestroyedLabel = new JLabel("Asteroids Destroyed: ");
			asteroidsDestroyedValueLabel = new JLabel("0");
			shipsDestroyedLabel = new JLabel("Enemy Ships Destroyed: ");
			shipsDestroyedValueLabel = new JLabel("0");
			pointsLabel = new JLabel("Points: ");
			pointsValueLabel = new JLabel("0");
			levelLabel = new JLabel("Level: ");
			levelValueLabel = new JLabel("0");
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(getGameScreen(), gridBagConstraints);
			jContentPane.add(shipsLabel, gridBagConstraints1);
			jContentPane.add(shipsValueLabel, gridBagConstraints2);
			jContentPane.add(asteroidsDestroyedLabel, gridBagConstraints3);
			jContentPane.add(asteroidsDestroyedValueLabel, gridBagConstraints4);
			jContentPane.add(pointsLabel, gridBagConstraints5);
			jContentPane.add(pointsValueLabel, gridBagConstraints6);
			jContentPane.add(levelLabel, gridBagConstraints7);
			jContentPane.add(levelValueLabel, gridBagConstraints8);
			jContentPane.add(shipsDestroyedLabel, gridBagConstraints9);
			jContentPane.add(shipsDestroyedValueLabel, gridBagConstraints10);

		}
		return jContentPane;
	}

	/**
	 * This method initializes gameScreen	
	 * 	
	 * @return GameScreen
	 */
	public GameScreen getGameScreen() {
		if (gameScreen == null) {
			gameScreen = new GameScreen();
			gameScreen.setShipsValueLabel(shipsValueLabel);
			gameScreen.setAsteroidsDestroyedValueLabel(asteroidsDestroyedValueLabel);
			gameScreen.setShipsDestroyedValueLabel(shipsDestroyedValueLabel);
			gameScreen.setPointsValueLabel(pointsValueLabel);
			gameScreen.setLevelValueLabel(levelValueLabel);

		}
		return gameScreen;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
