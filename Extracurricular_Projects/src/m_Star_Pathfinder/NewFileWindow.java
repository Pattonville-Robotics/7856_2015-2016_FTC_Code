package m_Star_Pathfinder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class NewFileWindow
{
	private JFrame				frame;
	private GridPreviewPanel	previewPanel;
	private final GridDraw		superGridDraw;

	public NewFileWindow(final GridDraw superGridDraw)
	{
		this.superGridDraw = superGridDraw;
		this.initialize();
	}

	public JFrame getFrame()
	{
		return this.frame;
	}

	private void initialize()
	{
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, 450, 275);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JSplitPane splitPane = new JSplitPane();
		this.frame.getContentPane().add(splitPane, BorderLayout.CENTER);

		this.previewPanel = new GridPreviewPanel(this.superGridDraw.getGrid().getGrid()[0].length, this.superGridDraw.getGrid().getGrid().length,
				this.superGridDraw.getGrid().getSquareWidth(), this.superGridDraw.getGrid().getSquareHeight());
		splitPane.setRightComponent(this.previewPanel);

		final JPanel settingsPanel = new JPanel();
		settingsPanel.setMinimumSize(new Dimension(100, 100));
		splitPane.setLeftComponent(settingsPanel);
		settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

		final JLabel lblNewLabel = new JLabel("Columns");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		settingsPanel.add(lblNewLabel);

		final JSpinner widthSpinner = new JSpinner();
		widthSpinner.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(final ChangeEvent e)
			{
				NewFileWindow.this.previewPanel.setNumRows((Integer) widthSpinner.getValue());
				NewFileWindow.this.frame.repaint();
			}
		});
		widthSpinner.setModel(new SpinnerNumberModel(new Integer(this.superGridDraw.getGrid().getGrid()[0].length), new Integer(4), null, new Integer(1)));
		widthSpinner.setMaximumSize(new Dimension(76, 28));
		settingsPanel.add(widthSpinner);

		final JLabel lblNewLabel_1 = new JLabel("Rows");
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		settingsPanel.add(lblNewLabel_1);

		final JSpinner heightSpinner = new JSpinner();
		heightSpinner.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(final ChangeEvent e)
			{
				NewFileWindow.this.previewPanel.setNumColumns((Integer) heightSpinner.getValue());
				NewFileWindow.this.frame.repaint();
			}
		});
		heightSpinner.setModel(new SpinnerNumberModel(new Integer(this.superGridDraw.getGrid().getGrid().length), new Integer(4), null, new Integer(1)));
		heightSpinner.setMaximumSize(new Dimension(76, 28));
		heightSpinner.setToolTipText("Width");
		settingsPanel.add(heightSpinner);

		final JLabel lblNewLabel_2 = new JLabel("Square Width");
		lblNewLabel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		settingsPanel.add(lblNewLabel_2);

		final JSpinner squareWidthSpinner = new JSpinner();
		squareWidthSpinner.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(final ChangeEvent e)
			{
				NewFileWindow.this.previewPanel.setSquareWidth((Integer) squareWidthSpinner.getValue());
				NewFileWindow.this.frame.repaint();
			}
		});
		squareWidthSpinner.setModel(new SpinnerNumberModel(new Integer(this.superGridDraw.getGrid().getSquareWidth()), null, null, new Integer(1)));
		squareWidthSpinner.setMaximumSize(new Dimension(76, 28));
		settingsPanel.add(squareWidthSpinner);

		final JLabel lblNewLabel_3 = new JLabel("Square Height");
		lblNewLabel_3.setAlignmentX(Component.CENTER_ALIGNMENT);
		settingsPanel.add(lblNewLabel_3);

		final JSpinner squareHeightSpinner = new JSpinner();
		squareHeightSpinner.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(final ChangeEvent e)
			{
				NewFileWindow.this.previewPanel.setSquareHeight((Integer) squareHeightSpinner.getValue());
				NewFileWindow.this.frame.repaint();
			}
		});
		squareHeightSpinner.setModel(new SpinnerNumberModel(new Integer(this.superGridDraw.getGrid().getSquareHeight()), null, null, new Integer(1)));
		squareHeightSpinner.setMaximumSize(new Dimension(76, 28));
		settingsPanel.add(squareHeightSpinner);

		final JSeparator separator = new JSeparator();
		settingsPanel.add(separator);

		final JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				final Grid grid = new Grid((Integer) widthSpinner.getValue(), (Integer) heightSpinner.getValue(), (Integer) squareWidthSpinner.getValue(),
						(Integer) squareHeightSpinner.getValue());
				grid.setSquareContents(new Point(0, 0), SquareType.START);
				grid.setSquareContents(new Point(3, 3), SquareType.FINISH);
				PathfindAI.computeDistance(grid, grid.getStartPoint());
				PathfindAI.optimizePaths(grid);
				PathfindAI.identifyNodes(grid);
				PathfindAI.connectNodes(grid);
				PathfindAI.identifyNodes(grid);
				PathfindAI.connectAllNodes(grid);
				NewFileWindow.this.superGridDraw.replaceGrid(grid);
				NewFileWindow.this.frame.dispose();
			}
		});
		btnCreate.setAlignmentX(Component.CENTER_ALIGNMENT);
		settingsPanel.add(btnCreate);

		final JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				NewFileWindow.this.frame.dispose();
			}
		});
		btnCancel.setAlignmentX(Component.CENTER_ALIGNMENT);
		settingsPanel.add(btnCancel);
	}
}
