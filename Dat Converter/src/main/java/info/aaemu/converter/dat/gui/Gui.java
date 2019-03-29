package info.aaemu.converter.dat.gui;

import javax.swing.JFrame;

import com.alee.laf.WebLookAndFeel;

import info.aaemu.converter.dat.entity.GetData;

import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Gui {

	private GetData getData;

	private JFrame frame;
	private JTextField doodadTF;
	private JTextField npcTF;
	private JButton doodadOpenBtn;
	private JButton npcOpenBtn;
	private JFileChooser fileChooser;

	private final String FILTERS = "dat";
	private final String DESCRIPTION = "Only map dat files";

	public Gui() {
		initialize();
		getData = new GetData();
	}

	private void initialize() {
		WebLookAndFeel.install();
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 115);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		doodadTF = new JTextField();
		doodadTF.setBounds(10, 11, 300, 23);
		frame.getContentPane().add(doodadTF);
		doodadTF.setColumns(10);

		doodadOpenBtn = new JButton("Convert doodad");
		doodadOpenBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg) {
				try {
					selectDoodadFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		doodadOpenBtn.setBounds(320, 10, 114, 25);
		frame.getContentPane().add(doodadOpenBtn);

		npcTF = new JTextField();
		npcTF.setEnabled(false);
		npcTF.setBounds(10, 46, 300, 23);
		frame.getContentPane().add(npcTF);
		npcTF.setColumns(10);

		npcOpenBtn = new JButton("Convert npc");
		npcOpenBtn.setEnabled(false);
		npcOpenBtn.setBounds(320, 45, 114, 25);
		frame.getContentPane().add(npcOpenBtn);
		frame.setVisible(true);

	}

	private void selectDoodadFile() throws IOException {
		String userDirLocation = System.getProperty("user.dir");
		File userDir = new File(userDirLocation);
		fileChooser = new JFileChooser(userDir);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return DESCRIPTION;
			}

			@Override
			public boolean accept(File f) {
				if (f != null) {
					if (f.isDirectory())
						return true;
					if (FILTERS == null)
						return (FILTERS.length() == 0);
					return f.getName().endsWith(FILTERS);
				}
				return false;
			}
		});

		if (fileChooser.showOpenDialog(doodadOpenBtn) == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			Path datFile = Paths.get(selectedFile.getAbsolutePath()).toAbsolutePath();
			doodadTF.setText(selectedFile.getAbsolutePath());
			getData.convert(datFile);
		}
	}

}