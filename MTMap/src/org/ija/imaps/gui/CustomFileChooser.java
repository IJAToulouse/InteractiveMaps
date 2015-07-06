package org.ija.imaps.gui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class CustomFileChooser extends JFileChooser {

	private static final long serialVersionUID = 1L;

	public CustomFileChooser(final String extension, String titre) {
		super();

		this.setMultiSelectionEnabled(false);
		this.setDialogTitle(titre);
		this.addChoosableFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return "Fichiers svg (*.svg)";
			}

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				} else {
					return f.getName().toLowerCase().endsWith(".svg");
				}
			}
		});
		this.setAcceptAllFileFilterUsed(false);
	}

}
