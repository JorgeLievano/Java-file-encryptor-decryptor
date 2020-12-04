package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class to manage the actions
 *
 */
public class ActionController implements ActionListener {

	public final static String FILE = "FILE";
	public final static String ENCRYPT = "ENCRYPT";
	public final static String DECRYPT = "DECRYPT";

	private Coordinator coordinator;

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action) {
		case FILE:
			coordinator.chooseFile();

			break;
		case ENCRYPT:
			coordinator.encrypt();
			break;
		case DECRYPT:
			coordinator.decrypt();
			break;
		}
	}

	/**
	 * set relation with coordinator
	 *
	 * @param coordinator
	 */
	public void setCoordinator(Coordinator coordinator) {
		this.coordinator = coordinator;
	}

}
